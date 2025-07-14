package lk.acpt.course_management_system.controller;

import lk.acpt.course_management_system.dto.UserDto;
import lk.acpt.course_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
    public ArrayList<UserDto> getAllUsers() {
        return (ArrayList<UserDto>) userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserDto saveUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        System.out.println(userDto.getPassword());
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }
}
