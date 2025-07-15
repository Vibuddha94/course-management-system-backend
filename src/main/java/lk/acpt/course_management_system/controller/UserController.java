package lk.acpt.course_management_system.controller;

import lk.acpt.course_management_system.dto.UserDto;
import lk.acpt.course_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<UserDto>> getAllUsers() {
        ArrayList<UserDto> users = (ArrayList<UserDto>) userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        UserDto user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getAll-by-role/{role}")
    public ResponseEntity<List<UserDto>> getAllByRole(@PathVariable String role) {
        List<UserDto> usersByRole = userService.getAllByRole(role);
        if (usersByRole == null || usersByRole.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usersByRole);
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        UserDto savedUser = userService.saveUser(userDto);
        if (savedUser == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Integer id) {
        Boolean deleted = userService.deleteUser(id);
        if (deleted == null || !deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(true);
    }
}
