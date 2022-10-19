package com.case4.repository;

import com.case4.model.entity.extra.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ICommentRepo extends JpaRepository<Comment,Long> {

    List<Comment> findAllByBlog_Id(Long blogId);
}
