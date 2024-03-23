package com.rk.olms.daos.repos;

import com.rk.olms.daos.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity u where u.username = ?1 or u.email = ?1")
    Optional<UserEntity> findByUsernameOrEmail(String username);

    @Query("select u from UserEntity u where u.email = ?1 and u.password = ?2")
    Optional<UserEntity> findByEmailAndPassword(String email, String password);



}