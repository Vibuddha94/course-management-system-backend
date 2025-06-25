package lk.acpt.course_management_system.service.impl;

import lk.acpt.course_management_system.dto.CourseModuleDto;
import lk.acpt.course_management_system.entity.CourseModule;
import lk.acpt.course_management_system.repo.CourseModuleRepo;
import lk.acpt.course_management_system.repo.CourseRepo;
import lk.acpt.course_management_system.service.CourseModuleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseModuleServiceImpl implements CourseModuleService {

    private final CourseModuleRepo courseModuleRepo;
    private final CourseRepo courseRepo;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public CourseModuleServiceImpl(CourseModuleRepo courseModuleRepo, CourseRepo courseRepo) {
        this.courseModuleRepo = courseModuleRepo;
        this.courseRepo = courseRepo;
    }

    @Override
    public List<CourseModuleDto> getAllCourseModules() {
        return courseModuleRepo.findAll().stream()
                .map(courseModule -> modelMapper.map(courseModule, CourseModuleDto.class)).toList();
    }

    @Override
    public CourseModuleDto getCourseModuleById(Integer id) {
        return courseModuleRepo.findById(id)
                .map(courseModule -> modelMapper.map(courseModule, CourseModuleDto.class)).orElse(null);
    }

    @Override
    public CourseModuleDto saveCourseModule(Integer id, CourseModuleDto courseModuleDto) {
        if (!courseRepo.existsById(id)) {
            return null; // Course does not exist
        }
        CourseModule courseModule = modelMapper.map(courseModuleDto, CourseModule.class);
        courseModule.setCourse(courseRepo.findById(id).orElse(null));
        return modelMapper.map(courseModuleRepo.save(courseModule), CourseModuleDto.class);
    }

    @Override
    public CourseModuleDto updateCourseModule(Integer id, CourseModuleDto courseModuleDto) {
        CourseModule existingModule = courseModuleRepo.findById(id).orElse(null);
        if (existingModule != null) {
            existingModule.setName(courseModuleDto.getName());
            existingModule.setUrl(courseModuleDto.getUrl());
            return modelMapper.map(courseModuleRepo.save(existingModule), CourseModuleDto.class);
        }
        return null;
    }

    @Override
    public Boolean deleteCourseModule(Integer id) {
        if (courseModuleRepo.existsById(id)) {
            courseModuleRepo.deleteById(id);
        }
        return !courseModuleRepo.existsById(id);
    }
}
