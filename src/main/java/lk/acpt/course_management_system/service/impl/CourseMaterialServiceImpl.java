package lk.acpt.course_management_system.service.impl;

import lk.acpt.course_management_system.dto.CourseMaterialDto;
import lk.acpt.course_management_system.entity.CourseMaterial;
import lk.acpt.course_management_system.repository.CourseMaterialRepo;
import lk.acpt.course_management_system.repository.CourseRepo;
import lk.acpt.course_management_system.service.CourseMaterialService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseMaterialServiceImpl implements CourseMaterialService {

    private final CourseMaterialRepo courseMaterialRepo;
    private final CourseRepo courseRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseMaterialServiceImpl(CourseMaterialRepo courseMaterialRepo, CourseRepo courseRepo) {
        this.courseMaterialRepo = courseMaterialRepo;
        this.courseRepo = courseRepo;
        this.modelMapper = new ModelMapper();

        // Configure ModelMapper to skip mapping the course field
        this.modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);

        // Explicitly tell ModelMapper to ignore the course field during mapping
//        this.modelMapper.typeMap(CourseMaterialDto.class, CourseMaterial.class)
//                .addMappings(mapper -> mapper.skip(CourseMaterial::setCourse));
    }

    @Override
    public List<CourseMaterialDto> getAllByCourseId(Integer courseId) {
        return courseMaterialRepo.findAllByCourse_Id(courseId).stream()
                .map(courseMaterial -> {
                    CourseMaterialDto courseMaterialDto = modelMapper.map(courseMaterial, CourseMaterialDto.class);
                    courseMaterialDto.setSavedName("");
                    courseMaterialDto.setUrl("");
                    return courseMaterialDto;
                }).toList();
    }

    @Override
    public CourseMaterialDto getCourseMaterialById(Integer id) {
        return courseMaterialRepo.findById(id)
                .map(courseMaterial -> modelMapper.map(courseMaterial, CourseMaterialDto.class)).orElse(null);
    }

    @Override
    public CourseMaterialDto saveCourseMaterial(Integer courseId, CourseMaterialDto courseMaterialDto) {
        if (!courseRepo.existsById(courseId)) {
            return null; // Course does not exist
        }
        CourseMaterial courseMaterial = modelMapper.map(courseMaterialDto, CourseMaterial.class);
        courseMaterial.setCourse(courseRepo.findById(courseId).orElse(null));
        CourseMaterial savedCourseMaterial = courseMaterialRepo.save(courseMaterial);
        System.out.println(savedCourseMaterial.getCourse().getName());
        return modelMapper.map(savedCourseMaterial, CourseMaterialDto.class);
    }

    @Override
    public Boolean deleteCourseMaterial(Integer id) {
        if (courseMaterialRepo.existsById(id)) {
            courseMaterialRepo.deleteById(id);
        }
        return !courseMaterialRepo.existsById(id);
    }

    @Override
    @Transactional
    public void deleteAllCourseMaterials(Integer courseId) {
        courseMaterialRepo.deleteAllByCourse_Id(courseId);
    }
}
