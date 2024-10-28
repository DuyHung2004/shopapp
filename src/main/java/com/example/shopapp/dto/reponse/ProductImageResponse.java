package com.example.shopapp.dto.reponse;

import com.example.shopapp.models.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductImageResponse {
    int id;
    int productId;
    String image_url;
}
