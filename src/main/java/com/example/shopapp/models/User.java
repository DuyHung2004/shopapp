package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "users")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(length = 100)
    String fullname;
    @Column(name ="phonenumber" ,length = 10, nullable = false)
    String phonenumber;
    @Column(length = 100)
    String address;
    String pass;
    LocalDateTime created_at;
    LocalDateTime updated_at;
    @PrePersist
    protected void onCreate(){
        created_at= LocalDateTime.now();
        updated_at=LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        updated_at=LocalDateTime.now();
    }
    int is_active;
    LocalDate date_of_birth;
    int facebook_account_id;
    int google_account_id;
    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role_id;
}
