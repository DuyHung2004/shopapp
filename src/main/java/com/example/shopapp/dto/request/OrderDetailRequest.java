package com.example.shopapp.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailRequest {
    int order_id;
    int product_id;
    int number_of_products;
    String color;
}
