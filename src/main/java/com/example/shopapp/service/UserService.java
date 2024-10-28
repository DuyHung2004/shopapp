package com.example.shopapp.service;

import com.example.shopapp.dto.reponse.UserResponse;
import com.example.shopapp.dto.request.UserCreationRequest;
import com.example.shopapp.exception.AppException;
import com.example.shopapp.exception.ErrorCode;
import com.example.shopapp.mapper.UserMapper;
import com.example.shopapp.models.Role;
import com.example.shopapp.models.User;
import com.example.shopapp.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
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
}
