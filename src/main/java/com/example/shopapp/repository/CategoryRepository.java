package com.example.shopapp.repository;

import com.example.shopapp.models.Category;
import com.example.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    boolean existsByName(String name);
    Optional<Category> findById(int id);
}
