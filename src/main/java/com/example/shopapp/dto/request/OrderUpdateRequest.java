package com.example.shopapp.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderUpdateRequest {
    String fullname;
    String email;
    String phone_number;
    String address;
    String note;
    float total_money;
    String status;
}
