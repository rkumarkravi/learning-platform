package com.rk.olms.services;

import com.rk.olms.daos.models.CourseContentEntity;
import com.rk.olms.daos.repos.CourseContentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Optional;

@Service
@Slf4j
public class CourseMediaService {
    public static final String VIDEO_FILE_PATH = "C:\\Users\\errku\\Videos\\2023-11-16 12-40-06.mp4";
    @Value("${file.upload.dir}")
    String fileUploadDir;
    private final CourseContentRepository courseContentRepository;

    public CourseMediaService(CourseContentRepository courseContentRepository) {
        this.courseContentRepository = courseContentRepository;
    }

    public Object getContent(Long contentId, HttpServletRequest request) {
        Object response = null;
        Optional<CourseContentEntity> courseContentOpt = this.courseContentRepository.findById(contentId);
        if (courseContentOpt.isPresent()) {
            CourseContentEntity courseContent = courseContentOpt.get();
            String format = courseContent.getFormat();
            String contentPath = fileUploadDir.substring(0, fileUploadDir.length() - 1) + courseContent.getContentUrl();

            log.info("content Path is :{}", contentPath);

            if ("mp4".equals(format)) {
                response = getVideoContent(contentPath, request);
            } else if ("pdf".equals(format)) {
                response = getPdfContent(contentPath);
            } else if ("jpg".equals(format) || "jpeg".equals(format)) {
                response = getImageContent(contentPath);
            }
        }
        return response;
    }

    public Object getImageContent(String path) {
        return Mono.fromCallable(() -> {
            try {
                // Load the image file from the file system
                File imageFile = new File(path); // Update with your actual image file path
                FileInputStream fileInputStream = new FileInputStream(imageFile);

                // Create an InputStreamResource from the image file input stream
                InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);

                // Set the appropriate headers for image content
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // Adjust MediaType as per your image type
                headers.setContentLength(imageFile.length());

                // Return the ResponseEntity with the InputStreamResource and headers
                return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);
            } catch (IOException e) {
                // Handle IOException if file loading fails
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }

    public Object getPdfContent(String path) {
        return Mono.fromCallable(() -> {
            try {
                // Load the PDF file from the file system
                File pdfFile = new File(path); // Update with your actual PDF file path
                FileInputStream fileInputStream = new FileInputStream(pdfFile);

                // Create an InputStreamResource from the PDF file input stream
                InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);

                // Set the appropriate headers for PDF content
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentLength(pdfFile.length());

                // Return the ResponseEntity with the InputStreamResource and headers
                return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle IOException if file loading fails
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }

    public Object getVideoContent(String path, HttpServletRequest request) {
        String rangeHeader = request.getHeader("Range");
        File videoFile = new File(path);
        long contentLength = videoFile.length();
        long rangeStart = 0;
        long rangeEnd = contentLength - 1;

        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            String[] range = rangeHeader.substring(6).split("-");
            rangeStart = Long.parseLong(range[0]);
            rangeEnd = range.length == 1 ? contentLength - 1 : Long.parseLong(range[1]);
        }

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(videoFile, "r");
            randomAccessFile.seek(rangeStart);
            long partialContentLength = rangeEnd - rangeStart + 1;

            Resource videoResource = new InputStreamResource(new FileInputStream(randomAccessFile.getFD()));
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            responseHeaders.setContentLength(partialContentLength);
            responseHeaders.set("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + contentLength);
            responseHeaders.set("Content-Type", "video/mp4");

            return Mono.just(new ResponseEntity<>(videoResource, responseHeaders, HttpStatus.PARTIAL_CONTENT));
        } catch (IOException e) {
            e.printStackTrace();
            return Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

}
