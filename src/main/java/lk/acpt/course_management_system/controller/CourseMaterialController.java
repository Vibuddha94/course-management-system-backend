package lk.acpt.course_management_system.controller;

import lk.acpt.course_management_system.dto.CourseMaterialDto;
import lk.acpt.course_management_system.dto.StorageDto;
import lk.acpt.course_management_system.service.CourseMaterialService;
import lk.acpt.course_management_system.service.CourseService;
import lk.acpt.course_management_system.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public CourseMaterialDto getCourseModuleById(@PathVariable Integer id) {
        return courseMaterialService.getCourseMaterialById(id);
    }

    @PostMapping("/{courseId}")
    public ResponseEntity<CourseMaterialDto> saveCourseMaterial(@PathVariable Integer courseId, @RequestParam("file") MultipartFile file) {
        try {
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

            return ResponseEntity.status(HttpStatus.CREATED).body(savedCourseMaterial);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public Boolean deleteCourseModule(@PathVariable Integer id) {
        return courseMaterialService.deleteCourseMaterial(id);
    }
}
