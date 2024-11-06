package com.example.shopapp.mapper;

import com.example.shopapp.dto.reponse.OrderReponse;
import com.example.shopapp.dto.request.OrderRequest;
import com.example.shopapp.dto.request.OrderUpdateRequest;
import com.example.shopapp.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "user_id",ignore = true)
    @Mapping(target = "order_date", ignore = true)
    Order toOrder(OrderRequest orderRequest);

    @Mapping(target = "user_id",ignore = true)
    @Mapping(target = "order_date", ignore = true)
    void updateOrder(@MappingTarget Order order , OrderUpdateRequest request);
    @Mapping(target = "user_id",source = "user_id.id")
    OrderReponse toOrderReponse(Order order);
}
