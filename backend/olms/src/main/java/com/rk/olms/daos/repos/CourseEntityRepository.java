package com.rk.olms.daos.repos;

import com.rk.olms.daos.models.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseEntityRepository extends JpaRepository<CourseEntity, String> {
}