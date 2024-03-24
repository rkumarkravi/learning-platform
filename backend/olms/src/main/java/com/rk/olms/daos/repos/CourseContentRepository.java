package com.rk.olms.daos.repos;

import com.rk.olms.daos.models.CourseContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseContentRepository extends JpaRepository<CourseContentEntity, Long> {
}