package com.example.shopapp.controller;

import com.example.shopapp.dto.reponse.ApiResponse;
import com.example.shopapp.dto.reponse.OrderReponse;
import com.example.shopapp.dto.request.OrderRequest;
import com.example.shopapp.dto.request.OrderUpdateRequest;
import com.example.shopapp.service.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("api/v1/orders")
public class OrderController {
    OrderService orderService;
    @PostMapping
    ApiResponse<OrderReponse> createOrder(@RequestBody @Valid OrderRequest request){
        ApiResponse<OrderReponse> apiResponse= new ApiResponse<>();
        apiResponse.setResult(orderService.createOrder(request));
        return apiResponse;
    }
    @GetMapping("/{id}")
    public ApiResponse<OrderReponse> getOrder(@Valid @PathVariable("id") int id){
        ApiResponse<OrderReponse> apiResponse= new ApiResponse<>();
        apiResponse.setResult(orderService.getOrder(id));
        return apiResponse;
    }
    @GetMapping("/user/{userid}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("userid") int userid){
        ApiResponse<List<OrderReponse>> listApiResponse =new ApiResponse<>();
        listApiResponse.setResult(orderService.getAllOrder(userid));
        return ResponseEntity.ok(listApiResponse);
    }
    @PutMapping("/{id}")
    public ApiResponse<OrderReponse> updateOrder(@PathVariable("id") int id,@ModelAttribute OrderUpdateRequest request){
        ApiResponse<OrderReponse> orderReponseApiResponse= new ApiResponse<>();
        orderReponseApiResponse.setResult(orderService.updateOrder(id,request));
        return orderReponseApiResponse;
    }
    @DeleteMapping("/{id}")
    public ApiResponse<OrderReponse> deleteOrder(@Valid @PathVariable("id") int id){
        ApiResponse<OrderReponse> apiResponse= new ApiResponse<>();
        apiResponse.setResult(orderService.deleteOrder(id));
        return apiResponse;
    }
    @GetMapping("/getallorder")
    public ApiResponse<List<OrderReponse>> getAllOrders(){
        ApiResponse<List<OrderReponse>> apiResponse= new ApiResponse<>();
        apiResponse.setResult(orderService.getAllOrders());
        return apiResponse;
    }

}
