package lk.acpt.course_management_system.service.impl;

import lk.acpt.course_management_system.dto.StorageDto;
import lk.acpt.course_management_system.exception.StorageException;
import lk.acpt.course_management_system.exception.StorageFileNotFoundException;
import lk.acpt.course_management_system.service.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageServiceImpl implements StorageService {


    @Override
    public void init(Path rootLocation) {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public String[] store(MultipartFile file, StorageDto storageDto) {
        if (storageDto.getLocation().trim().isEmpty()) {
            throw new StorageException("File upload location can not be Empty.");
        }
        Path rootLocation = Paths.get(storageDto.getLocation());
        init(rootLocation);
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            // Get the original filename and extension
            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);

            // Generate a unique filename using timestamp and random string
            String timestamp = String.valueOf(System.currentTimeMillis());
            String randomString = java.util.UUID.randomUUID().toString().substring(0, 8);
            String newFilename = "file_" + timestamp + "_" + randomString + fileExtension;

            // Create the destination path with the new filename
            Path destinationFile = rootLocation.resolve(Paths.get(newFilename))
                    .normalize().toAbsolutePath();

            // Security check to prevent directory traversal attacks
            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory.");
            }

            // Save the file with the new name
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }

            // Return the new filename and the directory path
            return new String[]{newFilename, destinationFile.getParent().toString()};
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Path load(String filename, Path rootLocation) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename, String location) {
        if (location.trim().isEmpty()) {
            throw new StorageException("File upload location can not be Empty.");
        }
        Path rootLocation = Paths.get(location);
        try {
            Path file = load(filename, rootLocation);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: ");
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: ", e);
        }
    }

    @Override
    public Boolean deleteAll(StorageDto storageDto) {
        try {
            Path rootLocation = Paths.get(storageDto.getLocation());
            return FileSystemUtils.deleteRecursively(rootLocation.toFile());
        } catch (IllegalStateException e) {
            throw new StorageException("Failed to delete all files in " + storageDto.getLocation(), e);
        }
    }

    @Override
    public Boolean delete(String filePath, String fileName) {
        try {
            Path file = Paths.get(filePath, fileName);
            if (Files.isRegularFile(file)) {
                return Files.deleteIfExists(file);
            } else {
                return false;
            }
        } catch (IOException e) {
            throw new StorageException("Failed to delete file " + filePath + "/" + fileName, e);
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}