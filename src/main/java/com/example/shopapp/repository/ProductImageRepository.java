package com.example.shopapp.repository;

import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage,Integer> {
    List<ProductImage> findAllByProductId(Product productId);
}
