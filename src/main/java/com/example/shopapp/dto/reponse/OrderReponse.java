package com.example.shopapp.dto.reponse;

import com.example.shopapp.models.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderReponse {
    int id;
    int user_id;
    String fullname;
    String email;
    String phone_number;
    String address;
    String note;
    String status;
    float total_money;
    String shipping_method;
    String shipping_address;
    LocalDate shipping_date;
    String tracking_number;
    String payment_method;
}
