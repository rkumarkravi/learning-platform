package com.rk.olms.dtos.requests;

import lombok.Data;

@Data
public class EnrollmentReqDto {
    long courseId;
    long enrollId;
    String paymentStatus;
    String paymentTransId;
}
