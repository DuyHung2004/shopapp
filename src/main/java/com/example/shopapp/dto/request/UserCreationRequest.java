package com.example.shopapp.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String fullname;
    @Size(min = 10, max = 10, message = "SDT khong dung")
    String phonenumber;
    String address;
    String pass;
    LocalDate date_of_birth;

}
