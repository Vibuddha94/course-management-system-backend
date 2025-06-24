package lk.acpt.course_management_system.dto;

import lk.acpt.course_management_system.entity.Instructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private Integer id;
    private String name;
    private String description;
    private Instructor instructor;
}
