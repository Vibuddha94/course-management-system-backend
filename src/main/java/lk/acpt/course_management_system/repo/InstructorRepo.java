package lk.acpt.course_management_system.repo;

import lk.acpt.course_management_system.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepo extends JpaRepository<Instructor,Integer> {
}
