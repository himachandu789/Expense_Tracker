package com.chandu.controller;


import com.chandu.response.CreateExpenseTypeResponse;
import com.chandu.request.CreateExpenseTypeRequest;
import com.chandu.response.GenricResponse;
import com.chandu.service.ExpenseTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenseTracker")
public class ExpenseTypeController {

    @Autowired
    private ExpenseTypeService expenseTypeService;

    @PostMapping("/addExpenseType")


    public GenricResponse<CreateExpenseTypeResponse> addExpenseType(@Valid @RequestBody CreateExpenseTypeRequest expenseTypeRequest){
        CreateExpenseTypeResponse createExpenseTypeResponse = expenseTypeService.addExpenseType(expenseTypeRequest);
        GenricResponse genericResponse = GenricResponse.builder().
                code(HttpStatus.OK.value()).
                message("user details has been saved").
                statusCode(0).data(createExpenseTypeResponse)
                .build();
        return genericResponse;
    }

}
