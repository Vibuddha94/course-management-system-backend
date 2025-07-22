package lk.acpt.course_management_system.controller;

import lk.acpt.course_management_system.dto.AuthResponse;
import lk.acpt.course_management_system.dto.LoginRequest;
import lk.acpt.course_management_system.dto.UserDto;
import lk.acpt.course_management_system.security.CustomUserDetails;
import lk.acpt.course_management_system.security.JwtService;
import lk.acpt.course_management_system.service.AuthService;
import lk.acpt.course_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authManager, JwtService jwtService, AuthService authService, UserService userService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        if (userService.getAllUsers().isEmpty()) {
            System.out.println("executed 1");
            UserDto adminUser = new UserDto();
            adminUser.setName("Admin Admin");
            adminUser.setEmail("admin@admin.com");
            adminUser.setPassword("admin123");
            adminUser.setRole("ROLE_ADMIN");
            UserDto savedUser = userService.saveUser(adminUser);
            if (savedUser == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails.getUsername());

        AuthResponse authResponse = authService.authenticate(request);
        authResponse.setToken(token);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        if (userDto.getRole().equals("ROLE_STUDENT")) {
            UserDto savedUser = userService.saveUser(userDto);
            if (savedUser == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
