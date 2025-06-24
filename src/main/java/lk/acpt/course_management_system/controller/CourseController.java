package lk.acpt.course_management_system.controller;

import lk.acpt.course_management_system.dto.CourseDto;
import lk.acpt.course_management_system.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseDto> getAllCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public CourseDto getCourseById(@PathVariable  Integer id){
        return courseService.getCourseById(id);
    }

    @PostMapping("{id}")
    public CourseDto saveCourse(@PathVariable Integer id,@RequestBody  CourseDto courseDto) {
        return courseService.saveCourse(id, courseDto);
    }

    @PutMapping("/{id}")
    public CourseDto updateCourse(@PathVariable Integer id, @RequestBody CourseDto courseDto) {
        return courseService.updateCourse(id, courseDto);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteCourse(@PathVariable Integer id){
        return courseService.deleteCourse(id);
    }
}
