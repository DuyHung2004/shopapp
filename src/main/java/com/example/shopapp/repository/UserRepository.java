package com.example.shopapp.repository;

import com.example.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByPhonenumber(String phonenumber);
    Optional<User> findByPhonenumber(String phonenumber);
    Optional<User> findById(Integer id);
}
