package com.example.shopapp.mapper;

import com.example.shopapp.dto.reponse.CategoryReponse;
import com.example.shopapp.dto.request.CategoryRequest;
import com.example.shopapp.models.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest categoryRequest);
    CategoryReponse toCategoryReponse(Category category);
}
