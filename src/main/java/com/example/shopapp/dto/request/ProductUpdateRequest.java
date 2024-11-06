package com.example.shopapp.dto.request;

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
public class ProductUpdateRequest {
    String name;
    @Min(value = 0, message = " gia nhap lon hon 0")
    float price;
    String description;
    Category categoryId;
}
