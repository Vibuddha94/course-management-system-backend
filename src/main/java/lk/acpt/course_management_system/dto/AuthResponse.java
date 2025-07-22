package lk.acpt.course_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private int id;
    private String name;
    private String role;
    private String token;

    public AuthResponse(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
