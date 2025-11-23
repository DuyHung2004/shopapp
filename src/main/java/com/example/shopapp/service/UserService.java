package com.example.shopapp.service;

import com.example.shopapp.dto.reponse.UserResponse;
import com.example.shopapp.dto.request.UserCreationRequest;
import com.example.shopapp.dto.request.UserUpdateRequest;
import com.example.shopapp.exception.AppException;
import com.example.shopapp.exception.ErrorCode;
import com.example.shopapp.mapper.UserMapper;
import com.example.shopapp.models.Role;
import com.example.shopapp.models.User;
import com.example.shopapp.repository.RoleRepository;
import com.example.shopapp.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    public UserResponse createUser(UserCreationRequest request){
        if (userRepository.existsByPhonenumber(request.getPhonenumber()))
            throw new AppException(ErrorCode.PHONE_EXISTED);
        User user=userMapper.toUser(request);
        user.setPass(passwordEncoder.encode(request.getPass()));
        user.setRole_id(Role.builder()
                        .id(1)
                        .name("user")
                .build());
        return userMapper.toUserResponse(userRepository.save(user));
    }
    @PreAuthorize("hasRole('admin')")
    public List<UserResponse> getAllUser(){
        List<User> users=userRepository.findAll();
        List<UserResponse> userResponses=users.stream().map(user -> userMapper.toUserResponse(user)).toList();
        return userResponses;
    }
    public UserResponse getUser(int id){
        User user = userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.UNAUTHENTICATED));
        return userMapper.toUserResponse(user);
    }
    public List<Role> getRole(){
        List<Role> roles= roleRepository.findAll();
        return roles;
    }
    public UserResponse updateUser(int id, UserUpdateRequest request){
        User user = userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.UNAUTHENTICATED));
        userMapper.updateUser(user,request);
        return userMapper.toUserResponse(userRepository.save(user));
    }
    public UserResponse activeUser(int id){
        User user=userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.UNAUTHENTICATED));
        if(user.getIs_active()==0){
            user.setIs_active(1);
        } else {
            user.setIs_active(0);
        }
        return userMapper.toUserResponse(userRepository.save(user));
    }
    public UserResponse getmyinfo(){
        var context = SecurityContextHolder.getContext();
        String name= context.getAuthentication().getName();
        User user= userRepository.findByPhonenumber(name).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }
}
