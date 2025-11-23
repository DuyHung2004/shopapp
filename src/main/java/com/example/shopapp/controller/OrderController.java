package com.example.shopapp.controller;

import com.example.shopapp.dto.reponse.ApiResponse;
import com.example.shopapp.dto.reponse.OrderReponse;
import com.example.shopapp.dto.request.OrderRequest;
import com.example.shopapp.dto.request.OrderUpdateRequest;
import com.example.shopapp.dto.request.VnpayRequest;
import com.example.shopapp.models.Vnpay;
import com.example.shopapp.service.OrderService;
import com.example.shopapp.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("api/v1/orders")
public class OrderController {
    OrderService orderService;
    VnpayController vnpayController;
    VnPayService vnpayService;
    @PostMapping
    ResponseEntity<?> createOrder(HttpServletRequest req,@RequestBody @Valid OrderRequest request) throws Exception {
        ApiResponse<OrderReponse> apiResponse= new ApiResponse<>();
        OrderReponse orderRes = orderService.createOrder(request);
        if (request.getPayment_method().equalsIgnoreCase("COD")) {
            apiResponse.setResult(orderRes);
            return ResponseEntity.ok(apiResponse);
        } else if (request.getPayment_method().equalsIgnoreCase("VNPAY")){
            VnpayRequest vnpay= VnpayRequest.builder()
                    .amount((long) request.getTotal_money())
                    .orderId(orderRes.getId())
                    .language("vn")
                    .build();
            Map<String, Object> paymentData = vnpayService.createPayment(req, vnpay);
            return ResponseEntity.ok(paymentData);
        }
        return null;
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
    public ApiResponse<OrderReponse> deleteOrder(HttpServletRequest req,@Valid @PathVariable("id") int id) throws IOException {
        ApiResponse<OrderReponse> apiResponse= new ApiResponse<>();
        apiResponse.setResult(orderService.deleteOrder(req,id));
        return apiResponse;
    }
    @GetMapping("/getallorder")
    public ApiResponse<List<OrderReponse>> getAllOrders(){
        ApiResponse<List<OrderReponse>> apiResponse= new ApiResponse<>();
        apiResponse.setResult(orderService.getAllOrders());
        return apiResponse;
    }

}
