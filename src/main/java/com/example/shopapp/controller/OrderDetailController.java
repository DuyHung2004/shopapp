package com.example.shopapp.controller;

import com.example.shopapp.dto.reponse.ApiResponse;
import com.example.shopapp.dto.reponse.OrderDetailReponse;
import com.example.shopapp.dto.request.OrderDetailRequest;
import com.example.shopapp.service.OrderDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("api/v1/ordersdetails")
public class OrderDetailController {
    OrderDetailService orderDetailService;
    @PostMapping
    ApiResponse<OrderDetailReponse> createOrderDetail(@RequestBody OrderDetailRequest request){
        ApiResponse<OrderDetailReponse> apiResponse= new ApiResponse<>();
        apiResponse.setResult(orderDetailService.createOrderDetail(request));
        return apiResponse;
    }
    @GetMapping("/{id}")
    ApiResponse<List<OrderDetailReponse>> getOrderDetail(@PathVariable("id") int id){
        ApiResponse<List<OrderDetailReponse>> apiResponse= new ApiResponse<>();
        apiResponse.setResult(orderDetailService.getOrderDetail(id));
        return apiResponse;
    }
}
