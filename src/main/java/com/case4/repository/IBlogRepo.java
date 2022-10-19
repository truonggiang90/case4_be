package com.case4.repository;

import com.case4.model.dto.BlogMostLike;
import com.case4.model.dto.BlogsOfUser;
import com.case4.model.dto.LikeCount;
import com.case4.model.entity.blog.Blog;
import com.case4.model.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBlogRepo extends JpaRepository<Blog,Long> {
    List<Blog> findAllByCategory_Name(String categoryName);
    List<Blog> findAllByUserInfo(UserInfo userInfo);
    @Query(value = "SELECT blogs.id as blogId ,count(likies.blog_id) as countL FROM case4.blogs inner join case4.likies on likies.blog_id = blogs.id group by likies.blog_id  limit 10;", nativeQuery = true)
    List<LikeCount> findCount();
@Query(value = " select t.user_id as userId,t.user_name as userName,t.count_blog as countBlog from  case4.top_ten_blog_user t",nativeQuery = true)
    List<BlogsOfUser> findBlogsOfUser();

@Query(value = "SELECT t.post_id as blogId,t.user_name as username,t.post_title as Title,t.category_title as category,t.post_create_at as createAt ,t.count_like as countLike FROM  case4.top_ten_like_of_blog t",nativeQuery = true)
    List<BlogMostLike> findBlogsMostLike();
}
