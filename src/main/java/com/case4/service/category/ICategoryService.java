package com.case4.service.category;

import com.case4.model.dto.ShowCategory;
import com.case4.model.entity.classify.Category;
import com.case4.service.IGeneralService;

import java.util.Optional;

public interface ICategoryService extends IGeneralService<Category> {

    Iterable<ShowCategory> getAllCategoryByUserId(Long user_id);
    Optional<Category> findByName(String name);
}
