package com.example.shopapp.controller;

import com.example.shopapp.dto.reponse.ApiResponse;
import com.example.shopapp.dto.reponse.UserResponse;
import com.example.shopapp.dto.request.UserCreationRequest;
import com.example.shopapp.dto.request.UserUpdateRequest;
import com.example.shopapp.models.Role;
import com.example.shopapp.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers() {
        ApiResponse<List<UserResponse>> apiResponse= new ApiResponse<>();
        apiResponse.setResult(userService.getAllUser());
        return apiResponse;
    }
    @GetMapping("/{userid}")
    ApiResponse<UserResponse> getUser(@PathVariable int userid){
        ApiResponse<UserResponse> apiResponse= new ApiResponse<>();
        apiResponse.setResult(userService.getUser(userid));
        return apiResponse;
    }
    @GetMapping("/role")
    ApiResponse<List<Role>> getRole(){
        ApiResponse<List<Role>> apiResponse= new ApiResponse<>();
        apiResponse.setResult(userService.getRole());
        return apiResponse;
    }
    @PutMapping("/{userid}")
    ApiResponse<UserResponse> updateUser(@PathVariable int userid, UserUpdateRequest request){
        ApiResponse<UserResponse>  apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.updateUser(userid,request));
        return apiResponse;
    }
    @PutMapping("/update/{userid}")
    ApiResponse<UserResponse> activeUser(@PathVariable int userid){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.activeUser(userid));
        return apiResponse;
    }
    @GetMapping("/myinfo")
    ApiResponse<UserResponse> getMyinfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getmyinfo())
                .build();
    }
}
