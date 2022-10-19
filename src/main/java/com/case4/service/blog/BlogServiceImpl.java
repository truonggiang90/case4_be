package com.case4.service.blog;

import com.case4.model.dto.BlogMostLike;
import com.case4.model.dto.BlogsOfUser;
import com.case4.model.entity.blog.Blog;
import com.case4.model.entity.user.UserInfo;
import com.case4.repository.IBlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BlogServiceImpl implements IBlogService {
    @Autowired
    IBlogRepo iBlogRepo;
    @Override
    public List<Blog> findAll() {
        return iBlogRepo.findAll();
    }

    @Override
    public Blog save(Blog blog) {
        return iBlogRepo.save(blog);
    }

    @Override
    public void removeById(Long id) {
    iBlogRepo.deleteById(id);
    }

    @Override
    public Optional<Blog> findById(Long id) {
        return iBlogRepo.findById(id);
    }

    @Override
    public List<Blog> findAllByCategory_Name(String categoryName) {
        return iBlogRepo.findAllByCategory_Name(categoryName);
    }

    @Override
    public List<BlogsOfUser> findBlogsOfUser() {
        return iBlogRepo.findBlogsOfUser();
    }

    @Override
    public List<BlogMostLike> findBlogsMostLike() {
        return iBlogRepo.findBlogsMostLike();
    }

    @Override
    public List<Blog> findAllByUserInfo(UserInfo userInfo) {
        return iBlogRepo.findAllByUserInfo(userInfo);
    }
}
