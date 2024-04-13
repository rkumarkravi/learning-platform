package com.rk.olms.daos.repos;

import com.rk.olms.daos.models.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    @Query("select count(c) from CourseEntity c where c.userEntity.id = ?1")
    long getCourseCountById(@NonNull Long id);

    @Query("select c from CourseEntity c where c.userEntity.id = ?1 order by c.likes DESC")
    List<CourseEntity> findByUserEntity_IdOrderByLikesDesc(@NonNull Long id);


}