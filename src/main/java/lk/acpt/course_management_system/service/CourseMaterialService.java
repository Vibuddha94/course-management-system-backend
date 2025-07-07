package lk.acpt.course_management_system.service;

import lk.acpt.course_management_system.dto.CourseMaterialDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseMaterialService {
    List<CourseMaterialDto> getAllCourseMaterials();

    CourseMaterialDto getCourseMaterialById(Integer id);

    CourseMaterialDto saveCourseMaterial(Integer courseId, CourseMaterialDto courseMaterialDto);

    Boolean deleteCourseMaterial(Integer id);
}
