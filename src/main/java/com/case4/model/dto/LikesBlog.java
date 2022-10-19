package com.case4.model.dto;

import com.case4.model.entity.blog.Blog;
import com.case4.model.entity.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikesBlog {
    private Long likes;
    private Blog idBlog;
    private UserInfo idUser;

}
