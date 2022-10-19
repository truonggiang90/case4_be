package com.case4.service.comment;

import com.case4.model.entity.extra.Comment;
import com.case4.service.IGeneralService;

import java.util.List;

public interface ICommentService extends IGeneralService<Comment> {

    List<Comment> findAllByBlog_Id(Long blogId);
}
