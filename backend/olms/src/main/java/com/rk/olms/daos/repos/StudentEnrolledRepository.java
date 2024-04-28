package com.rk.olms.daos.repos;

import com.rk.olms.daos.models.StudentEnrolledEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentEnrolledRepository extends JpaRepository<StudentEnrolledEntity, Long> {
    @Query("select s from StudentEnrolledEntity s " +
            "where s.paymentEntity.transactionId is not null " +
            "order by s.enrollmentDt DESC")
    List<StudentEnrolledEntity> findByPaymentEntity_TransactionIdNotNullOrderByEnrollmentDtDesc();

    @Query("select s from StudentEnrolledEntity s where s.paymentEntity is not null and s.id = ?1")
    Optional<StudentEnrolledEntity> findByPaymentEntityNotNullAndId(Long id);

    @Query("select (count(s) > 0) from StudentEnrolledEntity s where s.id = ?1 and s.paymentEntity is not null")
    boolean isAlreadyEnrolled(Long id);


}