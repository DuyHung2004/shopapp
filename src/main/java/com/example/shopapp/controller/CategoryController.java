package com.example.shopapp.controller;

import com.example.shopapp.dto.reponse.ApiResponse;
import com.example.shopapp.dto.reponse.CategoryReponse;
import com.example.shopapp.dto.request.CategoryRequest;
import com.example.shopapp.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("api/v1/categories")
public class CategoryController {
    CategoryService categoryService;
    @PostMapping
    ApiResponse<CategoryReponse> createCategory(@RequestBody CategoryRequest request){
        ApiResponse<CategoryReponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(categoryService.createCategory(request));
        return apiResponse;
    }
    @GetMapping
    ApiResponse<List<CategoryReponse>> getAllCategory(){
        ApiResponse<List<CategoryReponse>> apiResponse= new ApiResponse<>();
        apiResponse.setResult(categoryService.getAllCategory());
        return apiResponse;
    }
    @DeleteMapping("/{categoryid}")
    String deleteCategory(@PathVariable int categoryid){
        categoryService.deleteCategory(categoryid);
        return "Xoa danh muc thanh cong";
    }
}
