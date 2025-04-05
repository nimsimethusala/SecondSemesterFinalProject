package lk.ijse.furnitureapp_back_end.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageUploadService {
//    public String saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
////        String uploadPath1 = null;
//        Path uploadPath = Paths.get(uploadDir);
//        System.out.println(Files.exists(uploadPath));
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//        try (InputStream inputStream = multipartFile.getInputStream()) {
//            Path filePath = uploadPath.resolve(fileName);
//            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

    /// /            uploadPath1 = String.valueOf(uploadPath);
//
//        } catch (IOException ioException) {
//            ioException.printStackTrace();
//        } finally {
//            return fileName;
//        }
//    }
    public String saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {

        Path uploadPath = Paths.get(uploadDir);
        System.out.println(uploadPath.toAbsolutePath());
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println(filePath);
            return fileName;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw ioException; // So that controller layer can catch and handle it
        }
    }


}

//Service
//public class ImageUploadService {
/// /    @Value("${archive.path}")
/// /    private String archivePath;
/// /
/// /    @Value("${server.url}")
/// /    private String serverUrl;
/// /
/// /    @Value("${server.file.prefix}")
/// /    private String filePrefix;
//
//    public String[] getResultsOfFileWrite(MultipartFile imageFile) throws Exception{
//        String urlPrefix = serverUrl + "/" + filePrefix + "/";
//        String fileName = new FileUtilizer().generateFileName(imageFile.getOriginalFilename());
//
//        if (!new FileUtilizer().writeToDisk(imageFile, Paths.get(archivePath), fileName)) {
//            throw new Exception("File Writing Error Occurred!");
//        } else {
//            return new String[]{(archivePath + "/" + fileName),(urlPrefix + fileName),(fileName)};
//        }
//    }
//}