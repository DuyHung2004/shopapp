package com.example.shopapp.service;

import com.example.shopapp.dto.reponse.OrderReponse;
import com.example.shopapp.dto.request.OrderRequest;
import com.example.shopapp.enums.Status;
import com.example.shopapp.exception.AppException;
import com.example.shopapp.exception.ErrorCode;
import com.example.shopapp.mapper.OrderMapper;
import com.example.shopapp.models.Order;
import com.example.shopapp.models.User;
import com.example.shopapp.repository.OrderRepository;
import com.example.shopapp.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    UserRepository userRepository;
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    public OrderReponse createOrder(OrderRequest request){
        User user= userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Order order = new Order();
        order= orderMapper.toOrder(request);
        order.setUser_id(user);
        order.setStatus(Status.pending.toString());
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
    public OrderReponse updateOrder(int id, OrderRequest orderRequest){
        Order order= orderRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.UNAUTHENTICATED));
        orderMapper.updateOrder(order,orderRequest);
        return orderMapper.toOrderReponse(orderRepository.save(order));
    }
    public OrderReponse deleteOrder(int id){
        Order order= orderRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        if (order != null){
            order.setActive(false);
            orderRepository.save(order);
        }
        return orderMapper.toOrderReponse(order);
    }
}
