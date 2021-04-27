package ua.com.foxminded.taskE.GoogleDriveManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class GoogleDriveApplication {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("D:\\downloads\\");
        Files.createDirectories(path);

        SpringApplication.run(GoogleDriveApplication.class, args);
    }

}
