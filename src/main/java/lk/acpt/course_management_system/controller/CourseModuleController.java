package lk.acpt.course_management_system.controller;

import lk.acpt.course_management_system.dto.CourseModuleDto;
import lk.acpt.course_management_system.service.CourseModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course-modules")
public class CourseModuleController {

    private final CourseModuleService courseModuleService;

    @Autowired
    public CourseModuleController(CourseModuleService courseModuleService) {
        this.courseModuleService = courseModuleService;
    }

    @GetMapping
    public List<CourseModuleDto> getCourseModules() {
        return courseModuleService.getAllCourseModules();
    }

    @GetMapping("/{id}")
    public CourseModuleDto getCourseModuleById(@PathVariable Integer id) {
        return courseModuleService.getCourseModuleById(id);
    }

    @PostMapping("/{id}")
    public CourseModuleDto createCourseModule(@PathVariable Integer id, @RequestBody CourseModuleDto courseModuleDto) {
        return courseModuleService.saveCourseModule(id, courseModuleDto);
    }

    @PutMapping("/{id}")
    public CourseModuleDto updateCourseModule(@PathVariable Integer id, @RequestBody CourseModuleDto courseModuleDto) {
        return courseModuleService.updateCourseModule(id, courseModuleDto);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteCourseModule(@PathVariable Integer id) {
        return courseModuleService.deleteCourseModule(id);
    }
}
