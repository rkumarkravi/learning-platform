package com.rk.olms.services;

import com.rk.olms.configs.security.SecurityUserDetails;
import com.rk.olms.daos.models.CourseContentEntity;
import com.rk.olms.daos.models.CourseEntity;
import com.rk.olms.daos.repos.CourseContentRepository;
import com.rk.olms.daos.repos.CourseRepository;
import com.rk.olms.daos.repos.UserRepository;
import com.rk.olms.dtos.ResponseDto;
import com.rk.olms.dtos.requests.CourseContentReqDto;
import com.rk.olms.dtos.requests.CourseReqDto;
import com.rk.olms.dtos.requests.FileDeleteReqDto;
import com.rk.olms.dtos.requests.FileUploadReqDto;
import com.rk.olms.dtos.responses.CourseContentResDto;
import com.rk.olms.dtos.responses.CourseResDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseMgmtService {
    @Value("${file.upload.dir}")
    String fileUploadDir;

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseContentRepository courseContentRepository;
    private final FileUploadService fileUploadService;

    public CourseMgmtService(CourseRepository courseRepository, UserRepository userRepository, CourseContentRepository courseContentRepository, FileUploadService fileUploadService) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.courseContentRepository = courseContentRepository;
        this.fileUploadService = fileUploadService;
    }

    public ResponseDto<CourseResDto> createCourseMetaData(CourseReqDto courseReqDto) {
        SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseDto<CourseResDto> responseDto = new ResponseDto<>();
        CourseEntity courseEntity = new CourseEntity(courseReqDto);
        courseEntity.setAuthor(userDetails.getUserEntity().getFullName());
        courseEntity.setUserEntity(userDetails.getUserEntity());
        courseEntity = courseRepository.save(courseEntity);

        responseDto.setRs("S");
        responseDto.setRd("Course created successfully!");
        responseDto.setPayload(new CourseResDto(courseEntity));
        return responseDto;
    }

    public ResponseDto<CourseContentResDto> createCourseContent(CourseContentReqDto courseContentReqDto, long courseId) {
        ResponseDto<CourseContentResDto> responseDto = new ResponseDto<>();

        Optional<CourseEntity> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isPresent()) {
            FileUploadReqDto strategyReqDto = new FileUploadReqDto();
            strategyReqDto.setCourseId(courseId);
            strategyReqDto.setFile(courseContentReqDto.getFile());
            strategyReqDto.setFilePath(fileUploadDir);
            ResponseDto<String> uploadRes = fileUploadService.uploadFile(strategyReqDto);

            if ("S".equals(uploadRes.getRs())) {
                CourseContentEntity cce = CourseContentEntity
                        .builder()
                        .title(courseContentReqDto.getTitle())
                        .description(courseContentReqDto.getDescription())
                        .format(courseContentReqDto.getFormat())
                        .contentUrl(uploadRes.getPayload())
                        .title(courseContentReqDto.getTitle())
                        .course(courseOptional.get())
                        .build();

                cce = courseContentRepository.save(cce);

                responseDto.setPayload(CourseContentResDto.builder()
                        .contentId(cce.getId())
                        .courseId(cce.getCourse().getCourseId())
                        .title(cce.getTitle())
                        .description(cce.getDescription())
                        .url(cce.getContentUrl())
                        .format(cce.getFormat())
                        .build()
                );
                responseDto.setRd("Content uploaded successfully!");
                responseDto.setRs("S");
            } else {
                responseDto.setRd(uploadRes.getRd());
                responseDto.setRs(uploadRes.getRs());
            }
        }
        return responseDto;
    }

    public ResponseDto<CourseContentResDto> updateCourseContent(CourseContentReqDto courseContentReqDto, long courseId, long contentId) {
        ResponseDto<CourseContentResDto> responseDto = new ResponseDto<>();

        Optional<CourseContentEntity> courseContentOpt = courseContentRepository.findById(contentId);
        if (courseContentOpt.isPresent()) {
            CourseContentEntity cce = courseContentOpt.get();
            FileDeleteReqDto fileDeleteReqDto = new FileDeleteReqDto();
            fileDeleteReqDto.setCourseId(cce.getCourse().getCourseId());
            ResponseDto<String> fileDeleteRes=new ResponseDto<>();
            if(!courseContentReqDto.getFile().isEmpty()) {
                fileDeleteReqDto.setFilePath(fileUploadDir + "/" + cce.getContentUrl());
                fileDeleteRes = fileUploadService.deleteFile(fileDeleteReqDto);
            }else{
                fileDeleteRes.setRs("S");
            }

            if ("S".equals(fileDeleteRes.getRs())) {
                ResponseDto<String> uploadRes =new ResponseDto<>();
                if(!courseContentReqDto.getFile().isEmpty()) {
                    FileUploadReqDto strategyReqDto = new FileUploadReqDto();
                    strategyReqDto.setCourseId(cce.getCourse().getCourseId());
                    strategyReqDto.setFile(courseContentReqDto.getFile());
                    strategyReqDto.setFilePath(fileUploadDir);
                   uploadRes = fileUploadService.uploadFile(strategyReqDto);
                }else {
                    uploadRes.setRs("S");
                    uploadRes.setPayload(courseContentOpt.get().getContentUrl());
                }
                if ("S".equals(uploadRes.getRs())) {

                    cce.setContentUrl(uploadRes.getPayload());
                    cce.setFormat(courseContentReqDto.getFormat());
                    cce.setDescription(courseContentReqDto.getDescription());
                    cce.setTitle(courseContentReqDto.getTitle());
                    cce = courseContentRepository.save(cce);

                    responseDto.setPayload(CourseContentResDto.builder()
                            .contentId(cce.getId())
                            .courseId(cce.getCourse().getCourseId())
                            .title(cce.getTitle())
                            .description(cce.getDescription())
                            .url(cce.getContentUrl())
                            .format(cce.getFormat())
                            .build()
                    );

                    responseDto.setRd("Content updated successfully!");
                    responseDto.setRs("S");
                } else {
                    responseDto.setRd(uploadRes.getRd());
                    responseDto.setRs(uploadRes.getRs());
                }
            } else {
                responseDto.setRs(fileDeleteRes.getRs());
                responseDto.setRd(fileDeleteRes.getRd());
            }

        } else {
            responseDto.setRd("Content not available!");
        }

        return responseDto;
    }

    public ResponseDto<Void> deleteMedia(long contentId) {
        ResponseDto<Void> responseDto = new ResponseDto<>();
        Optional<CourseContentEntity> courseContentOpt = courseContentRepository.findById(contentId);
        if (courseContentOpt.isPresent()) {
            CourseContentEntity courseContent=courseContentOpt.get();
            courseContent.setContentUrl("NA");
            courseContent.setFormat("NA");

            courseContentRepository.save(courseContent);
            responseDto.setRs("S");
            responseDto.setRd("Media deleted successfully!");
        }else {
            responseDto.setRd("Invalid Content Id!");
        }
        return responseDto;
    }

        public ResponseDto<CourseContentResDto> deleteCourseContent(long courseId, long contentId) {
        ResponseDto<CourseContentResDto> responseDto = new ResponseDto<>();

        Optional<CourseContentEntity> courseContentOpt = courseContentRepository.findById(contentId);
        if (courseContentOpt.isPresent()) {
            CourseContentEntity cce = courseContentOpt.get();
            FileDeleteReqDto fileDeleteReqDto = new FileDeleteReqDto();
            fileDeleteReqDto.setCourseId(cce.getCourse().getCourseId());
            fileDeleteReqDto.setFilePath(fileUploadDir + "/" + cce.getContentUrl());
            ResponseDto<String> fileDeleteRes = fileUploadService.deleteFile(fileDeleteReqDto);

            if ("S".equals(fileDeleteRes.getRs())) {

                courseContentRepository.delete(cce);
                responseDto.setPayload(CourseContentResDto.builder()
                        .contentId(cce.getId())
                        .courseId(cce.getCourse().getCourseId())
                        .title(cce.getTitle())
                        .description(cce.getDescription())
                        .url(cce.getContentUrl())
                        .format(cce.getFormat())
                        .build()
                );
                responseDto.setRd("Content deleted successfully!");
                responseDto.setRs("S");
            } else {
                responseDto.setRs(fileDeleteRes.getRs());
                responseDto.setRd(fileDeleteRes.getRd());
            }

        } else {
            responseDto.setRd("Content not available!");
        }

        return responseDto;
    }

    public ResponseDto<List<CourseContentResDto>> getAllCourseContent(Long courseId) {
        ResponseDto<List<CourseContentResDto>> responseDto = new ResponseDto<>();

        Optional<CourseEntity> ceOpt = courseRepository.findById(courseId);

        if (ceOpt.isPresent()) {
            List<CourseContentEntity> courseContents = courseContentRepository.findByCourse_CourseId(courseId);

            List<CourseContentResDto> courseContentResDtos = courseContents.stream().map(cce -> CourseContentResDto.builder()
                    .contentId(cce.getId())
                    .courseId(cce.getCourse().getCourseId())
                    .title(cce.getTitle())
                    .description(cce.getDescription())
                    .url(cce.getContentUrl())
                    .format(cce.getFormat())
                    .build()).collect(Collectors.toList());

            responseDto.setPayload(courseContentResDtos);
            responseDto.setRd("Successful!");
            responseDto.setRs("S");
        } else {
            responseDto.setRd("Course not available!");
        }

        return responseDto;
    }
}
