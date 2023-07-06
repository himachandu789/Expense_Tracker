package com.chandu.controller;


import com.chandu.request.CreateUserRequest;
import com.chandu.response.CreateUserResponse;
import com.chandu.response.GenricResponse;
import com.chandu.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenseTracker")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/addUser")

    public GenricResponse<CreateUserResponse> addUser(@Valid @RequestBody CreateUserRequest createUserRequest){

        CreateUserResponse createUserResponse= userService.addUser(createUserRequest);
        GenricResponse genricResponse = GenricResponse.builder().
                code(HttpStatus.OK.value()).
                message("user details has been saved").
                statusCode(0).data(createUserResponse)
                .build();

        return genricResponse;


    }
}
