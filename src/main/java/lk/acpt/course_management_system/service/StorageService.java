package lk.acpt.course_management_system.service;

import lk.acpt.course_management_system.dto.StorageDto;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public interface StorageService {

    void init(Path rootLocation);

    String[] store(MultipartFile file, StorageDto storageDto);

    Stream<Path> loadAll(StorageDto storageDto);

    Path load(String filename, Path rootLocation);

    Resource loadAsResource(String filename, String location);

    boolean delete(String filePath);

    void deleteAll(StorageDto storageDto);

}
