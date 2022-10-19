package com.case4.model.entity.extra;

import com.case4.model.entity.blog.Blog;
import com.case4.model.entity.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String content;
    private  String createAt;
    @ManyToOne
    private UserInfo userInfo;
    @ManyToOne
    private Blog blog;
    @ManyToOne
    private Comment commentParent ;
}
