package com.example.shopapp.controller;

import com.example.shopapp.dto.reponse.ApiResponse;
import com.example.shopapp.dto.reponse.ProductImageResponse;
import com.example.shopapp.dto.reponse.ProductReponse;
import com.example.shopapp.dto.request.ProductImageRequest;
import com.example.shopapp.dto.request.ProductRequest;
import com.example.shopapp.exception.AppException;
import com.example.shopapp.exception.ErrorCode;
import com.example.shopapp.mapper.ProductMapper;
import com.example.shopapp.models.Product;
import com.example.shopapp.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("api/v1/products")
public class ProductController {
    ProductService productService;
    ProductMapper productMapper;
    @PostMapping
    ApiResponse<Map<String, Object>> createProduct(
                    @Valid @ModelAttribute ProductRequest request
            ) throws IOException {
        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>();
        Map<String, Object> result = productService.createProduct(request);
        apiResponse.setResult(result);
        return apiResponse;
    }
    @PostMapping("/image")
    ApiResponse<Map<String,Object>> addImage(
            @Valid @ModelAttribute ProductImageRequest request
            ) throws IOException {
        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>();
        Map<String, Object> result = productService.addImage(request);
        apiResponse.setResult(result);
        return apiResponse;
    }
    @GetMapping("")
    ResponseEntity<?> getProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0",name = "categoryId") int categoryId,
            @RequestParam(defaultValue ="0" ,name = "page") int page,
            @RequestParam(defaultValue ="9" ,name="limit") int limit
    ){
        PageRequest pageRequest= PageRequest.of(page, limit, Sort.by("id").descending());
        Page<ProductReponse> products=productService.getAllProducts(keyword,categoryId,pageRequest);
        int totalPages=products.getTotalPages();
        List<ProductReponse> productList= products.getContent();
        Map<String,Object> objectMap= new HashMap<>();
        objectMap.put("listproduct",productList);
        objectMap.put("totalPages",totalPages);

        return  ResponseEntity.ok(objectMap);
    }
    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName) throws MalformedURLException {
        Path imagePath =Paths.get("uploads/"+imageName);
        UrlResource resource= new UrlResource(imagePath.toUri());
        if(resource.exists()){
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/product/{productid}")
    public ApiResponse<List<ProductImageResponse>> getAllByProductId(@PathVariable int productid){
        ApiResponse<List<ProductImageResponse>> listApiResponse = new ApiResponse<>();
        listApiResponse.setResult(productService.getAllByProductId(productid));
        return listApiResponse;
    }
    @GetMapping("/topproduct")
    public ApiResponse<List<?>> gettopproduct(){
        ApiResponse<List<?>> listApiResponse= new ApiResponse<>();
        listApiResponse.setResult(productService.findTop5Products());
        return listApiResponse;
    }
}
