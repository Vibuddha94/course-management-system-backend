package lk.acpt.course_management_system.service;

import lk.acpt.course_management_system.dto.CourseModuleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseModuleService {
    List<CourseModuleDto> getAllCourseModules();

    CourseModuleDto getCourseModuleById(Integer id);

    CourseModuleDto saveCourseModule(Integer id, CourseModuleDto courseModuleDto);

    CourseModuleDto updateCourseModule(Integer id, CourseModuleDto courseModuleDto);

    Boolean deleteCourseModule(Integer id);
}
