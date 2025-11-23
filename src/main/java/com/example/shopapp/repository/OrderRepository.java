package com.example.shopapp.repository;

import com.example.shopapp.models.Order;
import com.example.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    Optional<Order> findById(int id);
   // List<Order> findByUser(User user);
}
