package com.example.shopapp.dto.reponse;

import com.example.shopapp.models.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailReponse {
    int order_id;
    Product product_id;
    float price;
    int number_of_products;
    float total_money;
    String color;
}
