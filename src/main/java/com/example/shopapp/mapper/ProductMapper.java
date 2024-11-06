package com.example.shopapp.mapper;

import com.example.shopapp.dto.reponse.ProductReponse;
import com.example.shopapp.dto.request.ProductRequest;
import com.example.shopapp.dto.request.ProductUpdateRequest;
import com.example.shopapp.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest productRequest);
    ProductReponse toProductReponse(Product product);
    void updateProduct(@MappingTarget Product product, ProductUpdateRequest request);
}
