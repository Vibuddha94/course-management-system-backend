package lk.acpt.course_management_system.service.impl;

import lk.acpt.course_management_system.dto.CourseDto;
import lk.acpt.course_management_system.entity.Course;
import lk.acpt.course_management_system.entity.Instructor;
import lk.acpt.course_management_system.repo.CourseRepo;
import lk.acpt.course_management_system.repo.InstructorRepo;
import lk.acpt.course_management_system.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepo courseRepo;
    private final InstructorRepo instructorRepo;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public CourseServiceImpl(CourseRepo courseRepo, InstructorRepo instructorRepo) {
        this.courseRepo = courseRepo;
        this.instructorRepo = instructorRepo;
    }

    @Override
    public List<CourseDto> getAllCourses() {
        return courseRepo.findAll().stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .toList();
    }

    @Override
    public CourseDto getCourseById(Integer id) {
        return courseRepo.findById(id).map(course -> modelMapper.map(course, CourseDto.class)).orElse(null);
    }

    @Override
    public CourseDto saveCourse(Integer id, CourseDto courseDto) {
        Instructor instructor = instructorRepo.findById(id).orElse(null);
        if (instructor == null) {
            return null; // or throw an exception
        }
        Course course = modelMapper.map(courseDto, Course.class);
        course.setInstructor(instructor);
        return modelMapper.map(courseRepo.save(course), CourseDto.class);
    }

    @Override
    public CourseDto updateCourse(Integer id, CourseDto courseDto) {
        Course existingCourse = courseRepo.findById(id).orElse(null);
        if (existingCourse != null) {
            existingCourse.setName(courseDto.getName());
            existingCourse.setDescription(courseDto.getDescription());
            return modelMapper.map(courseRepo.save(existingCourse), CourseDto.class);
        }
        return null;
    }

    @Override
    public Boolean deleteCourse(Integer id) {
    if (courseRepo.existsById(id)) {
        courseRepo.deleteById(id);
    }
        return !courseRepo.existsById(id);
    }
}
