package lk.acpt.course_management_system.service;

import lk.acpt.course_management_system.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto saveUser(UserDto userDto);
    List<UserDto> getAllUsers();
    UserDto getUserById(Integer id);
    UserDto updateUser(Integer id, UserDto userDto);
    Boolean deleteUser(Integer id);
}
