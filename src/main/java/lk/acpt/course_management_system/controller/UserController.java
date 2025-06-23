package lk.acpt.course_management_system.controller;

import lk.acpt.course_management_system.dto.InstructorDto;
import lk.acpt.course_management_system.dto.UserDto;
import lk.acpt.course_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserDto saveUser(@RequestBody  UserDto userDto) {

        UserDto savedUser = userService.saveUser(userDto);
        if (savedUser != null) {
            return savedUser;
        } else {
            return null;
        }
    }
}
