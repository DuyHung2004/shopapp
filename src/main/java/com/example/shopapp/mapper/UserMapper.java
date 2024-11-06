package com.example.shopapp.mapper;

import com.example.shopapp.dto.reponse.UserResponse;
import com.example.shopapp.dto.request.UserCreationRequest;
import com.example.shopapp.dto.request.UserUpdateRequest;
import com.example.shopapp.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper  {
    User toUser(UserCreationRequest request);
    //@Mapping(target = "roles", ignore = true)
   // void updateUser(@MappingTarget User user, UserUpdateRequest request);
    //    @Mapping(source = "",target = "")
    @Mapping(target = "role_id",source = "role_id")
    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
