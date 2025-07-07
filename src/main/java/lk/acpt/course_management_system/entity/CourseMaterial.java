package lk.acpt.course_management_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course_material") // Optional: specify table name
public class CourseMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String originalName;
    private String savedName;
    private String url;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id") // Foreign key column
    private Course course;
}
