package com.rk.olms.daos.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "lms_student_enrolled_entity")
public class StudentEnrolledEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "enrolled_user_id", nullable = false)
    @JsonIgnore
    private UserEntity userEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore
    private CourseEntity courseEntity;

    @Column(name = "enrollment_dt", nullable = false)
    private LocalDateTime enrollmentDt;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "payment_id")
    private PaymentEntity paymentEntity;

}