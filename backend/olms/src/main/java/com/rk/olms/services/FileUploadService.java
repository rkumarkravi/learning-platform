package com.rk.olms.services;

import com.rk.olms.dtos.ResponseDto;
import com.rk.olms.dtos.requests.FileDeleteReqDto;
import com.rk.olms.dtos.requests.FileUploadReqDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadService {
    @Value("${file.upload.strategy}")
    String fileUploadStrategy;

    public ResponseDto<String> uploadFile(FileUploadReqDto strategyReqDto) {
        FileUploader fileUploadAdapter = new FileUploadAdapter(fileUploadStrategy);
        return fileUploadAdapter.uploadFile(strategyReqDto);
    }

    public ResponseDto<String> deleteFile(FileDeleteReqDto fileDeleteReqDto) {
        FileUploader fileUploadAdapter = new FileUploadAdapter(fileUploadStrategy);
        return fileUploadAdapter.deleteFile(fileDeleteReqDto);
    }

    public static interface FileUploader {
        ResponseDto<String> uploadFile(FileUploadReqDto fileUploadReqDto);

        ResponseDto<String> deleteFile(FileDeleteReqDto fileDeleteReqDto);
    }

    public static class FileUploadAdapter implements FileUploader {
        private final FileUploader fileUploader;

        public FileUploadAdapter(String storageType) {
            if (storageType.equalsIgnoreCase("LOCAL")) {
                fileUploader = new LocalFileUploader();
            }
//            else if (storageType.equalsIgnoreCase("S3")) {
//                fileUploader = new S3FileUploader();
//            }
            else {
                throw new IllegalArgumentException("Invalid storage type");
            }
        }

        @Override
        public ResponseDto<String> uploadFile(FileUploadReqDto strategyReqDto) {
            return fileUploader.uploadFile(strategyReqDto);
        }

        @Override
        public ResponseDto<String> deleteFile(FileDeleteReqDto fileDeleteReqDto) {
            return fileUploader.deleteFile(fileDeleteReqDto);
        }

    }

    public static class LocalFileUploader implements FileUploader {
        @Override
        public ResponseDto<String> uploadFile(FileUploadReqDto srd) {
            srd.setFilePath(srd.getFilePath() + File.separator + srd.getCourseId());
            File fileDir = new File(srd.getFilePath());
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            ResponseDto<String> responseDto = new ResponseDto<>();
            try {
                // Generate a unique filename
                String filename = UUID.randomUUID() + srd.getFile().getOriginalFilename().substring(srd.getFile().getOriginalFilename().indexOf("."));
                // Save the file to the upload directory
                byte[] bytes = srd.getFile().getBytes();
                Path path = Paths.get(srd.getFilePath() + File.separator + filename);
                Files.write(path, bytes);

                // Generate URL for the uploaded file
                String fileUrl = "/" + srd.getCourseId() + "/" + filename; // Adjust as needed

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

        @Override
        public ResponseDto<String> deleteFile(FileDeleteReqDto fdr) {
            ResponseDto<String> responseDto = new ResponseDto<>();
            File fileDir = new File(fdr.getFilePath());
            if (!fileDir.exists()) {
                responseDto.setRd("File not found!");
                return responseDto;
            }

            fileDir.delete();

            if (fileDir.exists()) {
                responseDto.setRd("Some issue in deleting your content!");
                return responseDto;
            }

            responseDto.setRs("S");
            responseDto.setRd("Deleted Successfully!");

            return responseDto;
        }
    }
}
