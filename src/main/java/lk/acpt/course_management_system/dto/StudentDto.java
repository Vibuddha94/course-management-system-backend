package lk.acpt.course_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private  Integer id;
    private String address;
    private Integer age;
}
