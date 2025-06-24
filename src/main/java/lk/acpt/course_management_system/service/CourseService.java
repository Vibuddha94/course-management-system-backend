package lk.acpt.course_management_system.service;

import lk.acpt.course_management_system.dto.CourseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {
    List<CourseDto> getAllCourses();

    CourseDto getCourseById(Integer id);

    CourseDto saveCourse(CourseDto courseDto);

    CourseDto updateCourse(Integer id, CourseDto courseDto);

    Boolean deleteCourse(Integer id);
}
