package com.rk.olms.daos.repos;

import com.rk.olms.daos.models.CourseContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseContentRepository extends JpaRepository<CourseContentEntity, Long> {
    @Query("select c from CourseContentEntity c where c.course.courseId = ?1")
    List<CourseContentEntity> findByCourse_CourseId(Long courseId);

}