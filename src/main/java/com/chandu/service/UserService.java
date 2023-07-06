package com.chandu.service;

import com.chandu.model.User;
import com.chandu.model.UserStatus;
import com.chandu.repository.UserRepository;
import com.chandu.request.CreateUserRequest;
import com.chandu.response.CreateUserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public CreateUserResponse addUser(CreateUserRequest createUserRequest) {


        User userFromDb = userRepository.findByEmailAddressJPQL(createUserRequest.getEmail());
        if(userFromDb == null){
            User user = createUserRequest.toUser();
            user.setUserStatus(UserStatus.ACTIVE);
            userFromDb = userRepository.save(user);
        }
        CreateUserResponse createUserResponse = CreateUserResponse.builder().userId(userFromDb.getId()).build();
        return createUserResponse;

    }
}
