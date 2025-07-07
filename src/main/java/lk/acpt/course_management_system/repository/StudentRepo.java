package lk.acpt.course_management_system.repository;

import lk.acpt.course_management_system.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
    Optional<Student> findByUserId(Integer userId);

    @Transactional
    @Modifying
    void deleteByUserId(Integer userId);
}
