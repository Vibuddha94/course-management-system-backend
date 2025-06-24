package lk.acpt.course_management_system.repo;

import lk.acpt.course_management_system.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course,Integer> {
}
