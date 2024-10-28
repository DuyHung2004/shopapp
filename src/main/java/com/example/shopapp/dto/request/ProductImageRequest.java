package com.example.shopapp.dto.request;

import com.example.shopapp.models.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductImageRequest {
    int productId;
    String image_url;
    List<MultipartFile> files;
}
