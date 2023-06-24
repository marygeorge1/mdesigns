package com.sparta.mdesigns.utils;

import com.sparta.mdesigns.dtos.UserDTO;
import com.sparta.mdesigns.entities.User;

public class DTOConverter {

    public static UserDTO toUserDTO(User user){

        UserDTO uDTO=new UserDTO();
        uDTO.setName(user.getName());
        uDTO.setEmail(user.getEmail());

        return uDTO;
    }
}
