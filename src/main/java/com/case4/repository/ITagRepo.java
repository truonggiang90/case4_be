package com.case4.repository;

import com.case4.model.entity.classify.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITagRepo extends JpaRepository<Tag,Long> {
}
