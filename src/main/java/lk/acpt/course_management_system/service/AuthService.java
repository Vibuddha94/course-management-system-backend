package lk.acpt.course_management_system.service;

import lk.acpt.course_management_system.dto.AuthResponse;
import lk.acpt.course_management_system.dto.LoginRequest;

public interface AuthService {
    AuthResponse authenticate(LoginRequest loginRequest);
}
