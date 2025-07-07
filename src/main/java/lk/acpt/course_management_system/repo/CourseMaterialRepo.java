package lk.acpt.course_management_system.repo;

import lk.acpt.course_management_system.entity.CourseMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseMaterialRepo extends JpaRepository<CourseMaterial, Integer> {
}
