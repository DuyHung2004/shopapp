package com.example.shopapp.controller;

import com.example.shopapp.dto.reponse.ApiResponse;
import com.example.shopapp.dto.reponse.AuthenticationReponse;
import com.example.shopapp.dto.reponse.IntrospectReponse;
import com.example.shopapp.dto.request.IntrospectRequest;
import com.example.shopapp.dto.request.LoginRequest;
import com.example.shopapp.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping("/login")
    ApiResponse<AuthenticationReponse> login(@RequestBody LoginRequest request, HttpServletResponse response){
        var result= authenticationService.login(request);
        Cookie cookie = new Cookie("token", result.getToken());
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
        return ApiResponse.<AuthenticationReponse>builder()
                .result(result)
                .build();
    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectReponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result= authenticationService.introspectReponse(request);
        return ApiResponse.<IntrospectReponse>builder()
                .result(result)
                .build();
    }
    @PostMapping("/login_admin")
    ApiResponse<AuthenticationReponse> loginadmin(@RequestBody LoginRequest request, HttpServletResponse response){
        var result= authenticationService.loginadmin(request);
        Cookie cookie = new Cookie("token", result.getToken());
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
        return ApiResponse.<AuthenticationReponse>builder()
                .result(result)
                .build();
    }
}
