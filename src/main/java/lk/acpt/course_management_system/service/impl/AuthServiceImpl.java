package lk.acpt.course_management_system.service.impl;

import lk.acpt.course_management_system.dto.AuthResponse;
import lk.acpt.course_management_system.dto.LoginRequest;
import lk.acpt.course_management_system.entity.User;
import lk.acpt.course_management_system.repository.UserRepo;
import lk.acpt.course_management_system.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;

    @Autowired
    public AuthServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public AuthResponse authenticate(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepo.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new AuthResponse(user.getId(), user.getName(), user.getRole());
        }
        throw new RuntimeException("Invalid email or password");
    }
}
