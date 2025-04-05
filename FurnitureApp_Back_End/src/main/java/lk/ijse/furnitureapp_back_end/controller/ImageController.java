package lk.ijse.furnitureapp_back_end.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
public class ImageController {

    // Hardcoded image directory path
    private static final String IMAGE_DIRECTORY = "C:\\Users\\Lenovo\\Documents\\Project\\Second Semester\\SecondSemesterFinal\\uploads";

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            System.out.println("Requested filename: " + filename);

            // Build the full path to the image
            Path filePath = Paths.get(IMAGE_DIRECTORY).resolve(filename).normalize();
            System.out.println("Resolved path: " + filePath);

            // Check if file exists
            if (!Files.exists(filePath)) {
                System.out.println("File does not exist.");
                return ResponseEntity.notFound().build();
            }

            // Load as resource
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                System.out.println("Resource not accessible.");
                return ResponseEntity.notFound().build();
            }

            // Detect content type (e.g., image/jpeg, image/png)
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
