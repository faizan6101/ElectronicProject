package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;

import java.util.List;

public interface UserServiceI {

    //create user
 UserDto createUser(UserDto userDto);

    //update user
    UserDto updateuser(UserDto userDto, Integer userId);

    //delete user

    void deleteUser(Integer userId);

    //get all user
    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);



    //get user by Id
    UserDto getUser(Integer userId);

    //search user
List<UserDto>searchUser(String keyword);

//get user by email
    UserDto getUserByEmail(String email);


    

}
