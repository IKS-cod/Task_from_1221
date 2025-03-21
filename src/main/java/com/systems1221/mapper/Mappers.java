package com.systems1221.mapper;

import com.systems1221.dto.DishDto;
import com.systems1221.dto.UserDto;
import com.systems1221.model.Dish;
import com.systems1221.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface Mappers {
    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

    DishDto toDishDto(Dish dish);

    Dish toDish(DishDto dishDto);

}
