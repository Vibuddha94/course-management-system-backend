package lk.acpt.course_management_system.repo;

import lk.acpt.course_management_system.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface InstructorRepo extends JpaRepository<Instructor,Integer> {
    Optional<Instructor> findByUserId(Integer userId);

    @Transactional
    @Modifying
    void deleteByUserId(Integer userId);
}
