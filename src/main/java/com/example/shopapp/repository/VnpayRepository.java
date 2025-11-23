package com.example.shopapp.repository;

import com.example.shopapp.models.Order;
import com.example.shopapp.models.User;
import com.example.shopapp.models.Vnpay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VnpayRepository extends JpaRepository<Vnpay,String>  {
    boolean existsByVnpTxnRefAndVnpPayDate(String vnpTxnRef, String vnpPayDate);
    Optional<Vnpay> findByVnpTxnRef(String vnpTxnRef);
    Optional<Vnpay> findByOrder(Order order);
}
