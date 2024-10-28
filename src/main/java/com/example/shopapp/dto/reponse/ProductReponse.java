package com.example.shopapp.dto.reponse;

import com.example.shopapp.models.Category;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductReponse {
    int id;
    String name;
    float price;
    String thumbnail;
    String description;
    Category categoryId;
}
