package com.rk.olms.services;

import com.rk.olms.configs.security.SecurityUserDetails;
import com.rk.olms.daos.models.CourseEntity;
import com.rk.olms.daos.models.PaymentEntity;
import com.rk.olms.daos.models.StudentEnrolledEntity;
import com.rk.olms.daos.repos.CourseRepository;
import com.rk.olms.daos.repos.PaymentRepository;
import com.rk.olms.daos.repos.StudentEnrolledRepository;
import com.rk.olms.dtos.ResponseDto;
import com.rk.olms.dtos.requests.EnrollmentReqDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class EnrollmentAndPaymentService {
    private final StudentEnrolledRepository studentEnrolledRepository;
    private final PaymentRepository paymentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentAndPaymentService(StudentEnrolledRepository studentEnrolledRepository, PaymentRepository paymentRepository, CourseRepository courseRepository) {
        this.studentEnrolledRepository = studentEnrolledRepository;
        this.paymentRepository = paymentRepository;
        this.courseRepository = courseRepository;
    }

    public Object enrollByCourseId(EnrollmentReqDto enrollmentReqDto) {
        SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ResponseDto<Long> responseDto = new ResponseDto<>();
        Optional<CourseEntity> course = courseRepository.findById(enrollmentReqDto.getCourseId());

        if (course.isPresent()) {
//            PaymentEntity paymentEntity = new PaymentEntity();
//            paymentEntity.setPaymentMethod("RAZORPAY");
//            paymentEntity.setPaymentDateTime(LocalDateTime.now());
//            paymentEntity.setTransactionId(enrollmentReqDto.getPaymentTransId());
//            paymentEntity.setAmount(course.get().getAmount());
//            paymentEntity.setPaymentStatus(enrollmentReqDto.getPaymentStatus());
//
//            paymentEntity=paymentRepository.save(paymentEntity);

            StudentEnrolledEntity studentEnrolledEntity = new StudentEnrolledEntity();
            studentEnrolledEntity.setCourseEntity(course.get());
            studentEnrolledEntity.setEnrollmentDt(LocalDateTime.now());
            studentEnrolledEntity.setUserEntity(userDetails.getUserEntity());

            studentEnrolledEntity = studentEnrolledRepository.save(studentEnrolledEntity);
            responseDto.setRs("S");
            responseDto.setRd("Please pay to confirm your enrollment!");
            responseDto.setPayload(studentEnrolledEntity.getId());
        }

        return responseDto;
    }

    public Object confirmPaymentByEnrollId(EnrollmentReqDto enrollmentReqDto) {
        SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseDto<StudentEnrolledEntity> responseDto = new ResponseDto<>();

        if (studentEnrolledRepository.isAlreadyEnrolled(enrollmentReqDto.getEnrollId())) {
            responseDto.setRs("F");
            responseDto.setRd("Payment already received!");
            return responseDto;
        }
        Optional<StudentEnrolledEntity> studentEnrolledEntityExist = studentEnrolledRepository.findById(enrollmentReqDto.getEnrollId());

        if (studentEnrolledEntityExist.isPresent()) {

            if (!Objects.equals(userDetails.getUserEntity().getId(), studentEnrolledEntityExist.get().getUserEntity().getId())) {
                responseDto.setRs("F");
                responseDto.setRd("Invalid user!");
                return responseDto;
            }

            Optional<CourseEntity> course = Optional.ofNullable(studentEnrolledEntityExist.get().getCourseEntity());
            if (course.isPresent()) {
                PaymentEntity paymentEntity = new PaymentEntity();
                paymentEntity.setPaymentMethod("RAZORPAY");
                paymentEntity.setPaymentDateTime(LocalDateTime.now());
                paymentEntity.setTransactionId(enrollmentReqDto.getPaymentTransId());
                paymentEntity.setAmount(course.get().getAmount());
                paymentEntity.setPaymentStatus(enrollmentReqDto.getPaymentStatus());

                paymentEntity = paymentRepository.save(paymentEntity);

                StudentEnrolledEntity studentEnrolledEntity = studentEnrolledEntityExist.get();
                studentEnrolledEntity.setPaymentEntity(paymentEntity);
                studentEnrolledEntity.setEnrollmentDt(LocalDateTime.now());

                studentEnrolledEntity = studentEnrolledRepository.save(studentEnrolledEntity);
                responseDto.setRs("S");
                responseDto.setRd("Enrollment Successful!");
                responseDto.setPayload(studentEnrolledEntity);
            }
        } else {
            responseDto.setRs("F");
            responseDto.setRd("Invalid enrollment id.");
        }

        return responseDto;
    }
}
