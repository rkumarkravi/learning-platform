package com.rk.olms.daos.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "lms_payment_entity")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id", nullable = false)
    private Long id;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "payment_date_time", nullable = false)
    private LocalDateTime paymentDateTime;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status", nullable = false, length = 100)
    private String paymentStatus;

    @Column(name = "transaction_id", nullable = false, length = 50)
    private String transactionId;

}