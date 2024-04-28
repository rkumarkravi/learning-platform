package com.rk.olms.controllers;

import com.rk.olms.dtos.requests.EnrollmentReqDto;
import com.rk.olms.services.EnrollmentAndPaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnrollmentAndPaymentController {
    private final EnrollmentAndPaymentService enrollmentAndPaymentService;

    public EnrollmentAndPaymentController(EnrollmentAndPaymentService enrollmentAndPaymentService) {
        this.enrollmentAndPaymentService = enrollmentAndPaymentService;
    }

    @PostMapping("/enroll")
    public Object enrollByCourseId(@RequestBody EnrollmentReqDto enrollmentReqDto) {
        return enrollmentAndPaymentService.enrollByCourseId(enrollmentReqDto);
    }

    @PostMapping("/payment")
    public Object successfulPayment(@RequestBody EnrollmentReqDto enrollmentReqDto) {
        return enrollmentAndPaymentService.confirmPaymentByEnrollId(enrollmentReqDto);
    }
}
