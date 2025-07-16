package lk.acpt.course_management_system.service.impl;

import lk.acpt.course_management_system.dto.AuthResponse;
import lk.acpt.course_management_system.dto.LoginRequest;
import lk.acpt.course_management_system.dto.UserDto;
import lk.acpt.course_management_system.entity.User;
import lk.acpt.course_management_system.repository.UserRepo;
import lk.acpt.course_management_system.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public AuthServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public AuthResponse authenticate(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepo.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Compare plain text passwords since we're not using encryption
            if (loginRequest.getPassword().equals(user.getPassword())) {
                UserDto userDto = modelMapper.map(user, UserDto.class);
                // Hardcoded token for now
                String token = "sample-jwt-token-123456";
                return new AuthResponse(userDto.getId(), userDto.getName(), userDto.getRole(), token);
            }
        }
        throw new RuntimeException("Invalid email or password");
    }
}
