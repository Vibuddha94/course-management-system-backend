package lk.acpt.course_management_system.service;

import lk.acpt.course_management_system.dto.StorageDto;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Service
public interface StorageService {

    void init(Path rootLocation);

    String[] store(MultipartFile file, StorageDto storageDto);

    Path load(String filename, Path rootLocation);

    Resource loadAsResource(String filename, String location);

    Boolean delete(String filePath, String fileName);

    Boolean deleteAll(StorageDto storageDto);

}
