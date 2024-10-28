package com.example.shopapp.service;

import com.example.shopapp.dto.reponse.OrderDetailReponse;
import com.example.shopapp.dto.request.OrderDetailRequest;
import com.example.shopapp.exception.AppException;
import com.example.shopapp.exception.ErrorCode;
import com.example.shopapp.mapper.OrderDetailMapper;
import com.example.shopapp.models.Order;
import com.example.shopapp.models.OrderDetail;
import com.example.shopapp.models.Product;
import com.example.shopapp.repository.OrderDetailRepository;
import com.example.shopapp.repository.OrderRepository;
import com.example.shopapp.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderDetailService {
    OrderRepository orderRepository;
    ProductRepository productRepository;
    OrderDetailRepository orderDetailRepository;
    OrderDetailMapper orderDetailMapperl;
    public OrderDetailReponse createOrderDetail(OrderDetailRequest request){
        Order order= orderRepository.findById(request.getOrder_id()).orElseThrow(()->
                new AppException(ErrorCode.UNAUTHENTICATED));
        Product product= productRepository.findById(request.getProduct_id()).orElseThrow(
                ()-> new AppException(ErrorCode.UNAUTHENTICATED)
        );
        OrderDetail orderDetail= orderDetailMapperl.toOrderDetail(request);
        orderDetail.setOrder_id(order);
        orderDetail.setProduct_id(product);
        orderDetail.setPrice(product.getPrice());
        orderDetail.setTotal_money(product.getPrice()*request.getNumber_of_products());
        orderDetailRepository.save(orderDetail);
        return orderDetailMapperl.toOrderDetailReponse(orderDetail);
    }
    public List<OrderDetailReponse> getOrderDetail(int id){
        return orderDetailRepository.findAll().stream().filter(order -> order.getOrder_id().getId()==id)
                .map(orderDetail1 -> orderDetailMapperl.toOrderDetailReponse(orderDetail1) ).collect(Collectors.toList())
                        ;

    }
}
