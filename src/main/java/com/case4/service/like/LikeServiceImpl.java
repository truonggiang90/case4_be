package com.case4.service.like;

import com.case4.model.dto.LikeCount;
import com.case4.model.dto.LikesBlog;
import com.case4.model.entity.blog.Blog;
import com.case4.model.entity.extra.Like;
import com.case4.model.entity.user.UserInfo;
import com.case4.repository.ILikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceImpl implements ILikeService{

    @Autowired
    private ILikeRepo likeRepo;

    @Override
    public List<Like> findAll() {
        return likeRepo.findAll();
    }

    @Override
    public Like save(Like like) {
        return likeRepo.save(like);
    }

    @Override
    public void removeById(Long id) {
        likeRepo.deleteById(id);
    }

    @Override
    public Optional<Like> findById(Long id) {
        return likeRepo.findById(id);
    }

    @Override
    public Optional<Like> findAllByBlogAndAndUserInfo(Blog blog, UserInfo userInfo) {
        return likeRepo.findAllByBlogAndAndUserInfo(blog,userInfo);
    }

    @Override
    public List<Like> findAllByBlogId(Long blogId) {
        return likeRepo.findAllByBlog_Id(blogId);
    }

    @Override
    public List< LikesBlog> findCountLikeByBlogId(Long blogId) {
        return likeRepo.findCountLikeByBlogId(blogId);
    }

    @Override
    public void deleteLikeByBlogId(Long blogId) {
        likeRepo.deleteLikeByBlogId(blogId);
    }

    @Override
    public List<LikeCount> findCount() {
        return likeRepo.findCount();
    }
}
