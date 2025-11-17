package com.khanalsharad.dailyshoppingcart.controller;

import com.khanalsharad.dailyshoppingcart.dto.UserDto;
import com.khanalsharad.dailyshoppingcart.dto.UserUpdateDto;
import com.khanalsharad.dailyshoppingcart.exception.ResourceNotFoundException;
import com.khanalsharad.dailyshoppingcart.model.User;
import com.khanalsharad.dailyshoppingcart.response.ApiResponse;
import com.khanalsharad.dailyshoppingcart.response.UserResponse;
import com.khanalsharad.dailyshoppingcart.service.user.UserService;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final DefaultSslBundleRegistry sslBundleRegistry;

    public UserController(UserService userService, DefaultSslBundleRegistry sslBundleRegistry) {
        this.userService = userService;
        this.sslBundleRegistry = sslBundleRegistry;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveUser(@RequestBody UserDto request) {
        try{
           UserResponse user = userService.saveUser(request);
           return ResponseEntity.ok(new ApiResponse("Successfully saved user!!", user));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            UserResponse user = userService.getUserById(userId);
            return ResponseEntity.ok(new ApiResponse("Successfully retrieved user!!", user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllUsers() {
        try{
            List<UserResponse> users = userService.getAllUsers();
            return ResponseEntity.ok(new ApiResponse("Successfully retrieved users", users));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long userId, @RequestBody UserUpdateDto updateDto) {
        try {
            UserResponse updatedUser = userService.updateUser(userId,updateDto);
            return ResponseEntity.ok(new ApiResponse("Successfully updated user!!", updatedUser));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok(new ApiResponse("Successfully deleted user!!", userId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
