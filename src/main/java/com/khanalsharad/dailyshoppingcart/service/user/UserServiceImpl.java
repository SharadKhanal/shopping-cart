package com.khanalsharad.dailyshoppingcart.service.user;

import com.khanalsharad.dailyshoppingcart.dto.UserDto;
import com.khanalsharad.dailyshoppingcart.dto.UserUpdateDto;
import com.khanalsharad.dailyshoppingcart.enums.Status;
import com.khanalsharad.dailyshoppingcart.exception.ResourceNotFoundException;
import com.khanalsharad.dailyshoppingcart.model.User;
import com.khanalsharad.dailyshoppingcart.repo.UserRepository;
import com.khanalsharad.dailyshoppingcart.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse saveUser(UserDto request) {
        logger.info("Saving user {{}}: " + request);
        return Optional.of(request).filter(userDto ->
                !userRepository.existsByEmail(request.getEmail()))
                .map(req->{
                    User user = new User();
                    user.setUsername(request.getUsername());
                    user.setFullName(request.getFullName());
                    user.setContactNumber(request.getContactNumber());
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setStatus(Status.ACTIVE);
                    UserResponse savedUser = getUserResponse(userRepository.save(user));

                    return savedUser;
                }).orElseThrow(()->new ResourceNotFoundException("User already exist with email::{{}} " + request.getEmail()));
    }

    private UserResponse getUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setContactNumber(user.getContactNumber());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setStatus(user.getStatus());
        return  response;
    }

    @Override
    public UserResponse updateUser(Long userId, UserUpdateDto updateUserDto) {
        return userRepository.findById(userId).map(existingUser->{
         existingUser.setUsername(updateUserDto.getUsername());
         existingUser.setFullName(updateUserDto.getFullName());
         existingUser.setContactNumber(updateUserDto.getContactNumber());
         return getUserResponse(userRepository.save(existingUser));
        }).orElseThrow(()-> new ResourceNotFoundException("User not found with id:::{{}}" +  userId));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::getUserResponse ).collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getAllActiveUser() {
        return List.of();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return getUserResponse(userRepository.
                findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found!!")));
    }

    @Override
    public void deleteUserById(Long id) {
        logger.info("deleting user of id {{}} : " + id);
      userRepository.findById(id).ifPresentOrElse(userRepository:: delete,()->{
         throw new ResourceNotFoundException("User not found!");
     });
    }
}
