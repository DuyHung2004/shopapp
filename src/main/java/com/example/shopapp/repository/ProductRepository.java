package com.example.shopapp.repository;

import com.example.shopapp.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findById(int id);
    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR :categoryId = 0 OR p.categoryId.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%)")

    Page<Product> searchProducts(
            @Param("categoryId") int categoryId,
            @Param("keyword") String keyword,
            Pageable pageable);
    @Query(value = """
        WITH RankedProducts AS (
            SELECT
                p.id AS product_id,
                p.name AS product_name,
                p.thumbnail,
                p.category_id,
                ROW_NUMBER() OVER(PARTITION BY p.category_id ORDER BY p.id) AS rn
            FROM products p
        )
        SELECT 
            *
        FROM RankedProducts rp
        WHERE rp.rn <= 5
        """, nativeQuery = true )
    List<Object[]> findTop5ProductsByCategory();

}

