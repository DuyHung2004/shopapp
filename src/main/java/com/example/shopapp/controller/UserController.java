package com.example.shopapp.controller;

import com.example.shopapp.dto.reponse.ApiResponse;
import com.example.shopapp.dto.reponse.UserResponse;
import com.example.shopapp.dto.request.UserCreationRequest;
import com.example.shopapp.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/users")
@Slf4j
public class UserController {
    UserService userService;
    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<UserResponse> apiResponse= new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }
}
