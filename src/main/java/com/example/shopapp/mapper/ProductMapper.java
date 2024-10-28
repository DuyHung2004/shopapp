package com.example.shopapp.mapper;

import com.example.shopapp.dto.reponse.ProductReponse;
import com.example.shopapp.dto.request.ProductRequest;
import com.example.shopapp.models.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest productRequest);
    ProductReponse toProductReponse(Product product);
}
