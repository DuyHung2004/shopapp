package com.example.shopapp.service;

import com.example.shopapp.dto.reponse.ProductImageResponse;
import com.example.shopapp.dto.reponse.ProductReponse;
import com.example.shopapp.dto.request.ProductImageRequest;
import com.example.shopapp.dto.request.ProductRequest;
import com.example.shopapp.dto.request.ProductUpdateRequest;
import com.example.shopapp.exception.AppException;
import com.example.shopapp.exception.ErrorCode;
import com.example.shopapp.mapper.ProductImageMapper;
import com.example.shopapp.mapper.ProductMapper;
import com.example.shopapp.models.Category;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImage;
import com.example.shopapp.repository.CategoryRepository;
import com.example.shopapp.repository.ProductImageRepository;
import com.example.shopapp.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ProductMapper productMapper;
    ProductImageRepository productImageRepository;
    ProductImageMapper productImageMapper;
    @PreAuthorize("hasRole('admin')")
    public Map<String, Object> createProduct(ProductRequest request) throws IOException {
        Product product= productMapper.toProduct(request);
        categoryRepository.findById(product.getCategoryId().getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<MultipartFile> files = request.getFiles();
        productRepository.save(product);

        List<String> storedFilenames = new ArrayList<>();
        for (MultipartFile file : files) {

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/"))
                throw new AppException(ErrorCode.UNAUTHORIZED);

            String storedFilename = storeFile(file);
            storedFilenames.add(storedFilename);

            createProductImage(ProductImageRequest.builder()
                    .productId(product.getId())
                    .image_url(storedFilename)
                    .build());
        }

        ProductReponse productReponse = productMapper.toProductReponse(product);
        Map<String, Object> response = new HashMap<>();
        response.put("product", productReponse);
        response.put("filenames", storedFilenames);

        return response;
    }



    String storeFile(MultipartFile file) throws IOException {
        String name= StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFilename= UUID.randomUUID().toString()+"_"+name;
        Path upload= Paths.get("uploads");
        if(!Files.exists(upload)){
            Files.createDirectories(upload);
        }
        Path destination=Paths.get(upload.toString(),uniqueFilename);
        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
    public ProductReponse getProduct(int id){
        return productMapper.toProductReponse( productRepository.findById(id)
                .orElseThrow(()->new RuntimeException()));
    }
    public Page<ProductReponse> getAllProducts( String keyword, int categoryId , PageRequest pageRequest){
        Page<Product> productPage;
        productPage=productRepository.searchProducts(categoryId,keyword,pageRequest);
        Page<ProductReponse> productReponses = productPage.map(product -> productMapper.toProductReponse(product));
        return productReponses;
    }
    public ProductImageResponse createProductImage( ProductImageRequest productImageRequest){
        Product product= productRepository.findById(productImageRequest.getProductId())
                .orElseThrow(()-> new AppException(ErrorCode.UNAUTHENTICATED));
        ProductImage productImage= productImageMapper.toProductImage(productImageRequest);
        productImage.setProductId(product);
     return productImageMapper.toProductImageReponse(   productImageRepository.save(productImage));
    }
    public  Map<String, Object> addImage(ProductImageRequest request) throws IOException {
        List<MultipartFile> files = request.getFiles();
        List<String> storedFilenames = new ArrayList<>();
        Product product= productRepository.findById(request.getProductId())
                .orElseThrow(()-> new AppException(ErrorCode.UNAUTHENTICATED));
        for (MultipartFile file : files) {

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/"))
                throw new AppException(ErrorCode.UNAUTHORIZED);

            String storedFilename = storeFile(file);
            storedFilenames.add(storedFilename);

            createProductImage(ProductImageRequest.builder()
                    .productId(product.getId())
                    .image_url(storedFilename)
                    .build());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("productimage",request.getProductId());
        response.put("list_url",storedFilenames);
        return response;
    }
    public List<ProductImageResponse> getAllByProductId(int id){
        Product product= productRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.UNAUTHENTICATED));
        List<ProductImage> productImageList= productImageRepository.findAllByProductId(product);
        List<ProductImageResponse> productImageResponseList= productImageList.stream().map(productImage -> productImageMapper
                .toProductImageReponse(productImage)).toList();
        return productImageResponseList;
    }
    public List<Map<String, Object>> findTop5Products() {
        List<Object[]> results = productRepository.findTop5ProductsByCategory();
        List<Map<String, Object>> products = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> product = new HashMap<>();
            product.put("id", row[0]);
            product.put("name", row[1]);
            product.put("thumbnail", row[2]);
            product.put("price",row[4]);
            Category category= categoryRepository.findById((Integer) row[3]).orElseThrow(()->new AppException(ErrorCode.UNAUTHENTICATED));
            product.put("category",category);
            products.add(product);
        }
        return products;
    }
    public ProductReponse updateProduct(int id, ProductUpdateRequest request){
        Product product =productRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.UNAUTHENTICATED));
        productMapper.updateProduct(product,request);
        return productMapper.toProductReponse(productRepository.save(product));
    }
    public void deleteImage(int id){
        productImageRepository.deleteById(id);
    }
    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }
}
