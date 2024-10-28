package com.example.shopapp.mapper;

import com.example.shopapp.dto.reponse.OrderDetailReponse;
import com.example.shopapp.dto.request.OrderDetailRequest;
import com.example.shopapp.models.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    @Mapping(target = "order_id",ignore = true)
    @Mapping(target = "product_id", ignore = true)
    OrderDetail toOrderDetail(OrderDetailRequest orderDetailRequest);

    @Mapping(target = "order_id", source = "order_id.id")
    @Mapping(target = "product_id", source = "product_id")
    OrderDetailReponse toOrderDetailReponse(OrderDetail orderDetail);
}
