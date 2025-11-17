package com.khanalsharad.dailyshoppingcart.service.user;

import com.khanalsharad.dailyshoppingcart.dto.UserDto;
import com.khanalsharad.dailyshoppingcart.dto.UserUpdateDto;
import com.khanalsharad.dailyshoppingcart.model.User;
import com.khanalsharad.dailyshoppingcart.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse saveUser(UserDto user);

    UserResponse updateUser(Long userId, UserUpdateDto user);

    List<UserResponse> getAllUsers();

    List<UserResponse> getAllActiveUser();

    UserResponse getUserById(Long id);

    void deleteUserById(Long id);


}
