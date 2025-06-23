package lk.acpt.course_management_system.controller;

import lk.acpt.course_management_system.dto.UserDto;
import lk.acpt.course_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ArrayList<UserDto> getAllUsers(){
        ArrayList<UserDto> users = (ArrayList<UserDto>) userService.getAllUsers();
        return users;
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Integer id){
        UserDto user = userService.getUserById(id);
        return user;
    }

    @PostMapping
    public UserDto saveUser(@RequestBody  UserDto userDto) {
        UserDto savedUser = userService.saveUser(userDto);
        return savedUser;
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return updatedUser;
    }

    @DeleteMapping("/{id}")
    public Boolean deleteUser(@PathVariable Integer id){
        return userService.deleteUser(id);
    }
}
