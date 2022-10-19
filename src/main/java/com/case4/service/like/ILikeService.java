package com.case4.service.like;

import com.case4.model.dto.LikeCount;
import com.case4.model.dto.LikesBlog;
import com.case4.model.entity.blog.Blog;
import com.case4.model.entity.extra.Like;
import com.case4.model.entity.user.UserInfo;
import com.case4.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ILikeService extends IGeneralService<Like> {
    Optional<Like> findAllByBlogAndAndUserInfo(Blog blog, UserInfo userInfo);

    List<Like> findAllByBlogId(Long blogId);
    List< LikesBlog> findCountLikeByBlogId(Long blogId);
    void deleteLikeByBlogId(Long blogId);

    List<LikeCount> findCount();

}
