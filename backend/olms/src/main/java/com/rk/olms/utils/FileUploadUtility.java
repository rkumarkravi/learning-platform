package com.rk.olms.utils;

import com.rk.olms.dtos.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUploadUtility {

    public static ResponseDto<String> uploadToDir(String uploadDirectory, MultipartFile file) {
        File fileDir = new File(uploadDirectory);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        ResponseDto<String> responseDto = new ResponseDto<>();
        try {
            // Generate a unique filename
            String filename = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
            // Save the file to the upload directory
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + File.separator + filename);
            Files.write(path, bytes);

            // Generate URL for the uploaded file
            String fileUrl = "/media/get/" + filename; // Adjust as needed

            // Return the URL in the response
            responseDto.setRs("S");
            responseDto.setRd("Successful");
            responseDto.setPayload(new URI(fileUrl).toString());
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            responseDto.setRs("F");
            responseDto.setRd("Failed to upload file: " + e.getMessage());
        }

        return responseDto;
    }
}
