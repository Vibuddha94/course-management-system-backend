package lk.acpt.course_management_system.controller;

import lk.acpt.course_management_system.dto.CourseMaterialDto;
import lk.acpt.course_management_system.dto.StorageDto;
import lk.acpt.course_management_system.exception.StorageFileNotFoundException;
import lk.acpt.course_management_system.service.CourseMaterialService;
import lk.acpt.course_management_system.service.CourseService;
import lk.acpt.course_management_system.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/course-modules")
public class CourseMaterialController {

    private final CourseMaterialService courseMaterialService;
    private final CourseService courseService;
    private final StorageService storageService;

    @Autowired
    public CourseMaterialController(CourseMaterialService courseMaterialService, CourseService courseService, StorageService storageService) {
        this.storageService = storageService;
        this.courseService = courseService;
        this.courseMaterialService = courseMaterialService;
    }

    @GetMapping
    public List<CourseMaterialDto> getCourseModules() {
        return courseMaterialService.getAllCourseMaterials();
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getCourseModuleById(@PathVariable Integer id) {
        CourseMaterialDto courseMaterial = courseMaterialService.getCourseMaterialById(id);
        if (courseMaterial == null) {
            return null;
        }

        String location = courseMaterial.getUrl();
        String filename = courseMaterial.getSavedName();

        try {
            // Load the file as a resource
            Resource fileResource = storageService.loadAsResource(filename, location);
            // If the resource is null or does not exist, return a 404
            if (fileResource == null || !fileResource.exists()) {
                return ResponseEntity.notFound().build();
            }
            // Read the file as an InputStream
            InputStream inputStream = fileResource.getInputStream();

            // Read the InputStream as a byte array
            byte[] fileBytes = inputStream.readAllBytes();

            // Set the HTTP headers for the response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", courseMaterial.getOriginalName());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            // Return the file as a byte array
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileBytes);

        } catch (StorageFileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IOException e) {
            // If something goes wrong, return a 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{courseId}")
    public ResponseEntity<List<CourseMaterialDto>> saveCourseMaterials(@PathVariable Integer courseId, @RequestParam("files") MultipartFile[] files) {
        try {
            List<CourseMaterialDto> courseMaterials = new ArrayList<>();
            for (MultipartFile file : files) {
                CourseMaterialDto courseMaterialDto = new CourseMaterialDto();
                courseMaterialDto.setOriginalName(file.getOriginalFilename());

                if (courseService.getCourseById(courseId) == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }

                String[] result = storageService.store(file, new StorageDto(courseService.getCourseById(courseId)
                        .getName()));
                courseMaterialDto.setSavedName(result[0]);
                courseMaterialDto.setUrl(result[1]);

                CourseMaterialDto savedCourseMaterial = courseMaterialService.saveCourseMaterial(courseId, courseMaterialDto);
                if (savedCourseMaterial == null) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }

                // Clear sensitive data before sending response
                savedCourseMaterial.setSavedName("");
                savedCourseMaterial.setUrl("");

                courseMaterials.add(savedCourseMaterial);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(courseMaterials);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourseModule(@PathVariable Integer id) {
        CourseMaterialDto courseMaterial = courseMaterialService.getCourseMaterialById(id);
        if (courseMaterial == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }
        String filePath = courseMaterial.getUrl();
        String fileName = courseMaterial.getSavedName();

        if (filePath == null || filePath.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("File path is required");
        }

        boolean deleted = storageService.delete(filePath, fileName);
        if (deleted) {
            Boolean deletedResult = courseMaterialService.deleteCourseMaterial(id);
            if (!deletedResult) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            return ResponseEntity.ok().body("File deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found or could not be deleted");
        }
    }
}
