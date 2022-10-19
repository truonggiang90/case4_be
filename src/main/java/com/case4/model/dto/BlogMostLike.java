package com.case4.model.dto;

public interface BlogMostLike {
    Long getBlogId();
    String getUsername();
    String getCategory();
    String getTitle();
    String getCreateAt();
    int getCountLike();
}
