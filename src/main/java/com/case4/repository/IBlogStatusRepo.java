package com.case4.repository;

import com.case4.model.entity.blog.BlogStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogStatusRepo extends JpaRepository<BlogStatus,Long> {
}
