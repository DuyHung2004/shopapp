package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Table(name = "vnpay")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vnpay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    long vnpAmount;
    String vnpTransactionNo;
    String vnpTxnRef;
    String vnpPayDate;
    String status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @OneToOne
    @JoinColumn(name = "order_id")
    Order order;

}
