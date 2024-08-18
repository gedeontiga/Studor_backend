package com.studor.orientation_student.manager.services.suggestionCour;

// import java.io.File;
// import java.io.IOException;

// import org.springframework.core.io.AbstractFileResolvingResource;
// import org.springframework.core.io.ClassPathResource;

// import jakarta.annotation.Resource;

public class GetAbsoluePath {
        public String getAbsolutePathOfFile() {
            String currentDirectory = System.getProperty("user.dir");
            return currentDirectory;
    }
}
