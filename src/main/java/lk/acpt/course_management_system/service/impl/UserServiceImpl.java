package lk.acpt.course_management_system.service.impl;

import lk.acpt.course_management_system.dto.UserDto;
import lk.acpt.course_management_system.entity.Instructor;
import lk.acpt.course_management_system.entity.User;
import lk.acpt.course_management_system.repo.InstructorRepo;
import lk.acpt.course_management_system.repo.UserRepo;
import lk.acpt.course_management_system.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private InstructorRepo instructorRepo;

    @Override
    public UserDto saveUser(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        // Map UserDto to User entity
        User user = modelMapper.map(userDto, User.class);
        if (userDto.getInstructor() != null) {
            // Map InstructorDto to Instructor entity
            Instructor instructor = modelMapper.map(userDto.getInstructor(), Instructor.class);
            // Set bidirectional relationship
            instructor.setUser(user);
            user.setInstructor(instructor);
            User savedUser = userRepo.save(user);
            return modelMapper.map(savedUser, UserDto.class);
        }
        return  null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        ModelMapper modelMapper = new ModelMapper();
        ArrayList<UserDto> users = new ArrayList<>();
        for(User user: userRepo.findAll()){
            users.add(modelMapper.map(user,UserDto.class));
        }
        return users;
    }

    @Override
    public UserDto getUserById(Integer id) {
        ModelMapper modelMapper = new ModelMapper();
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            return modelMapper.map(user,UserDto.class);
        }
        return null;
    }

    @Override
    public UserDto updateUser(Integer id, UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        User existingUser = userRepo.findById(id).orElse(null);
        if (existingUser != null) {
            // Update user fields
            existingUser.setName(userDto.getName());
            existingUser.setEmail(userDto.getEmail());
            existingUser.setPassword(userDto.getPassword());
            existingUser.setRole(userDto.getRole());
            existingUser.setContactNumber(userDto.getContactNumber());

            // If instructor details are provided, update or create the instructor
            if (userDto.getInstructor() != null) {
                Instructor existingInstructor = instructorRepo.findByUserId(id).orElse(null);
                if (existingInstructor != null) {
                    existingInstructor.setQualification(userDto.getInstructor().getQualification());
                    instructorRepo.save(existingInstructor);
                } else {
                    return null;
                }
            }

            User updatedUser = userRepo.save(existingUser);
            return modelMapper.map(updatedUser, UserDto.class);
        }
        return null;
    }

    @Override
    public Boolean deleteUser(Integer id) {
    if (userRepo.existsById(id)) {
        instructorRepo.deleteByUserId(id);
        if (instructorRepo.findByUserId(id).isEmpty()) {
            userRepo.deleteById(id);
        }
        return !userRepo.existsById(id);
    }
        return false;
    }
}
