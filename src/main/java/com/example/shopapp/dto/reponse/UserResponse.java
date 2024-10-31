package com.example.shopapp.dto.reponse;

import com.example.shopapp.models.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String fullname;
    String phonenumber;
    String address;
    LocalDate date_of_birth;
    int is_active;
    int facebook_account_id;
    int google_account_id;
    Role role_id;
}
