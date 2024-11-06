package com.example.shopapp.dto.request;

import com.example.shopapp.models.Role;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String fullname;
    String phonenumber;
    String address;
    LocalDate date_of_birth;
    Role role_id;
    int is_active;
}
