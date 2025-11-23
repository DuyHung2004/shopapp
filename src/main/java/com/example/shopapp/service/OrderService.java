package com.example.shopapp.service;

import com.example.shopapp.dto.reponse.OrderReponse;
import com.example.shopapp.dto.request.OrderRequest;
import com.example.shopapp.dto.request.OrderUpdateRequest;
import com.example.shopapp.dto.request.VnpayRefund;
import com.example.shopapp.dto.request.VnpayRequest;
import com.example.shopapp.enums.Status;
import com.example.shopapp.exception.AppException;
import com.example.shopapp.exception.ErrorCode;
import com.example.shopapp.mapper.OrderMapper;
import com.example.shopapp.models.Order;
import com.example.shopapp.models.User;
import com.example.shopapp.models.Vnpay;
import com.example.shopapp.repository.OrderRepository;
import com.example.shopapp.repository.UserRepository;
import com.example.shopapp.repository.VnpayRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderService {
    VnPayService vnPayService;
    UserRepository userRepository;
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    VnpayRepository vnpayRepository;
    public OrderReponse createOrder(OrderRequest request){
        User user= userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Order order = new Order();
        order= orderMapper.toOrder(request);
        order.setUser_id(user);
        if (request.getPayment_method().equalsIgnoreCase("VNPAY")){
            order.setStatus(Status.shipped.toString());
        } else{
            order.setStatus(Status.pending.toString());
        }
        order.setShipping_date(LocalDate.now().plusDays(4));
        order.setTracking_number(generateRandomString(10));
        order.setOrder_date(LocalDate.now());
        order.setActive(true);
        Order savedOrder = orderRepository.save(order);
        OrderReponse orderReponse= orderMapper.toOrderReponse(savedOrder);
        return orderReponse;
    }
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
    public OrderReponse getOrder(int id){
        return orderMapper.toOrderReponse(orderRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED)));
    }
    public List<OrderReponse> getAllOrder(int userid){
        return orderRepository.findAll().stream().filter(order -> order.getUser_id().getId()==userid)
                .map(order -> orderMapper.toOrderReponse(order))
                .collect(Collectors.toList());
    }
    public OrderReponse updateOrder(int id, OrderUpdateRequest request){
        Order order= orderRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.UNAUTHENTICATED));
        orderMapper.updateOrder(order,request);
        return orderMapper.toOrderReponse(orderRepository.save(order));
    }
    public OrderReponse deleteOrder(HttpServletRequest req,int id) throws IOException {
        var context = SecurityContextHolder.getContext();
        String name= context.getAuthentication().getName();
        User user= userRepository.findByPhonenumber(name).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        Order order= orderRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        if(order.getUser_id().getId()!=user.getId()){
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        if (order != null&&order.getStatus().equals(Status.pending.toString())){
            order.setActive(false);
            order.setStatus(Status.cancelled.toString());
            orderRepository.save(order);
            Vnpay vnpay= vnpayRepository.findByOrder(order).orElseThrow(() -> new RuntimeException("Payment not found"));;;
            VnpayRefund vnpayRefund= VnpayRefund.builder()
                    .trantype("02")
                    .order_id(vnpay.getVnpTxnRef())
                    .amount(vnpay.getVnpAmount())
                    .trans_date(vnpay.getVnpPayDate())
                    .user(user.getRole_id().getId()==1 ? "user" : "admin")
                    .build();
            vnPayService.Refund(req,vnpayRefund);
        }
        return orderMapper.toOrderReponse(order);
    }
    public  List<OrderReponse> getAllOrders(){
        List<Order> orders= orderRepository.findAll();
        return orders.stream().map(order -> orderMapper.toOrderReponse(order)).collect(Collectors.toList());
    }
}
