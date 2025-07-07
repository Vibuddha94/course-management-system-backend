package lk.acpt.course_management_system.repository;

import lk.acpt.course_management_system.entity.CourseMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMaterialRepo extends JpaRepository<CourseMaterial, Integer> {
    List<CourseMaterial> findAllByCourse_Id(Integer courseId);

    void deleteAllByCourse_Id(Integer id);
}
