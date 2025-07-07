package lk.acpt.course_management_system;

import lk.acpt.course_management_system.dto.StorageDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageDto.class)
public class CourseManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseManagementSystemApplication.class, args);
    }

}
