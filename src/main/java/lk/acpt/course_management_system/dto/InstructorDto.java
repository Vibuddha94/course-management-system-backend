package lk.acpt.course_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDto{
    private Integer instructorId;
    private String qualification;
}
