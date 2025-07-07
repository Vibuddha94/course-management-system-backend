package lk.acpt.course_management_system.dto;


import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
@Getter
public class StorageDto {
    private String location = "F:\\Projects\\Course Management System\\upload-dir\\";

    public StorageDto(String location) {
        this.location += location;
    }

    public void setLocation(String folder) {
        this.location += folder;
    }

}