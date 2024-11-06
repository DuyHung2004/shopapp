package com.example.shopapp.service;

import com.example.shopapp.dto.reponse.CategoryReponse;
import com.example.shopapp.dto.request.CategoryRequest;
import com.example.shopapp.exception.AppException;
import com.example.shopapp.exception.ErrorCode;
import com.example.shopapp.mapper.CategoryMapper;
import com.example.shopapp.models.Category;
import com.example.shopapp.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    @PreAuthorize("hasRole('admin')")
    public CategoryReponse createCategory(CategoryRequest request){
        if(categoryRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        Category category= categoryMapper.toCategory(request);
        return categoryMapper.toCategoryReponse(categoryRepository.save(category));
    }
    public List<CategoryReponse> getAllCategory(){
        List<CategoryReponse> categoryReponses= (List<CategoryReponse>) categoryRepository.findAll().stream().map((category -> categoryMapper.toCategoryReponse(category))).toList();
        return categoryReponses ;
    }
    public void deleteCategory(int id){
        categoryRepository.deleteById(id);
    }
}
