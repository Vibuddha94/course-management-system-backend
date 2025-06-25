package lk.acpt.course_management_system.repo;

import lk.acpt.course_management_system.entity.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseModuleRepo extends JpaRepository<CourseModule, Integer> {
}
