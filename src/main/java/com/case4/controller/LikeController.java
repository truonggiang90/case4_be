package com.case4.controller;

import com.case4.model.entity.blog.Blog;
import com.case4.model.entity.extra.Like;
import com.case4.model.entity.user.UserInfo;
import com.case4.service.blog.IBlogService;
import com.case4.service.blogStautus.IBlogStatusService;
import com.case4.service.like.ILikeService;
import com.case4.service.userInfo.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IBlogService blogService;
    @Autowired
    private IBlogStatusService blogStatusService;
    @Autowired
    private ILikeService likeService;




    @GetMapping("/{idUserInfo}/{idBlog}")
    public ResponseEntity<?> saveLike(@PathVariable Long idUserInfo, @PathVariable Long idBlog) {
        Optional<UserInfo> userInfo=userInfoService.findById(idUserInfo);
        Optional<Blog> blog=blogService.findById(idBlog);
        if(!userInfo.isPresent()||!blog.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(likeService.findAllByBlogAndAndUserInfo(blog.get(),userInfo.get()).isPresent()){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        Like like=new Like();
        like.setBlog(blog.get());
        like.setUserInfo(userInfo.get());
        likeService.save(like);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/unlike/{idUserInfo}/{idBlog}")
    public ResponseEntity<?> deleteLike(@PathVariable Long idUserInfo, @PathVariable Long idBlog) {
        Optional<UserInfo> userInfo=userInfoService.findById(idUserInfo);
        Optional<Blog> blog=blogService.findById(idBlog);
        if(!userInfo.isPresent()||!blog.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Like> like=likeService.findAllByBlogAndAndUserInfo(blog.get(),userInfo.get());
        if(!like.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        likeService.removeById(like.get().getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
