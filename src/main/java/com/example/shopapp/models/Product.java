package com.example.shopapp.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "products")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(nullable = false, length = 350)
    String name;

    private float price;

    @Column( length = 350)
    String thumbnail;
    String description;
    @Column(name = "create_at")
    LocalDateTime createat;
    LocalDateTime update_at;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category categoryId;
    @PrePersist
    protected void onCreate(){
        createat= LocalDateTime.now();
        update_at=LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        update_at=LocalDateTime.now();
    }
}
