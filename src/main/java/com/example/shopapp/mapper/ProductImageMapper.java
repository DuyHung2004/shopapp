package com.example.shopapp.mapper;

import com.example.shopapp.dto.reponse.ProductImageResponse;
import com.example.shopapp.dto.request.ProductImageRequest;
import com.example.shopapp.models.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
    @Mapping(target ="productId",ignore = true )
    ProductImage toProductImage(ProductImageRequest productImageRequest);
    @Mapping(target = "productId",source = "productId.id")
    ProductImageResponse toProductImageReponse(ProductImage productImage);
}
