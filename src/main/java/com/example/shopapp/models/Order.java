package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "orders")
@Entity
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user_id;
    String fullname;
    String email;
    @Column(name = "phone_number")
    String phone_number;
    String address;
    String note;
    @Column(name = "order_date")
    LocalDate order_date;
    String status;
    float total_money;
    String shipping_method;
    String shipping_address;
    LocalDate shipping_date;
    String tracking_number;
    String payment_method;
    boolean active;
}
