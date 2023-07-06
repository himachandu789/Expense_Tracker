package com.chandu.controller;


import com.chandu.model.TxnFilterOperators;
import com.chandu.model.TxnFilterType;
import com.chandu.request.CreateTxnRequest;
import com.chandu.response.*;
import com.chandu.service.TxnService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/expenseTracker")
public class TxnController {


    @Autowired
    private TxnService txnService;
    @PostMapping("/addUserTxn")
    public GenricResponse<CreateTxnResponse> addUserTxn(@Valid @RequestBody CreateTxnRequest createTxnRequest){
        CreateTxnResponse createTxnResponse = txnService.addUserTxn(createTxnRequest);
        GenricResponse genericResponse = GenricResponse.builder().
                code(HttpStatus.OK.value()).
                message("user details has been saved").
                statusCode(0).data(createTxnResponse)
                .build();
        return genericResponse;

    }

    @GetMapping("/fetchTxn")
    public GenricResponse<List<TxnSearchResponse>> fetchUserTxnDetails(@RequestParam("filter") TxnFilterType txnFilterType,
                                                                        @RequestParam("operator") TxnFilterOperators operators,
                                                                        @RequestParam("values") String value) throws ParseException {


        String[] values = value.split(",");
        List<TxnSearchResponse> listOfTxnSearchResp = txnService.fetchUserTxnDetails(txnFilterType,operators, values);
        GenricResponse genericResponse = GenricResponse.builder().
                code(HttpStatus.OK.value()).
                message("user details has been saved").
                statusCode(0).data(listOfTxnSearchResp)
                .build();
        return genericResponse;
    }

    // api -> one day expense, 7 day expense, 15 day, 30 days

    @GetMapping("/fetchCalculatedResults")
    public GenricResponse<AnalyticalResponse> fetchCalculatedResponse(@RequestParam("email") String email){
        AnalyticalResponse analyticalResponse = txnService.fetchCalculatedResponse(email);
        GenricResponse genericResponse = GenricResponse.builder().
                code(HttpStatus.OK.value()).
                message("user details has been saved").
                statusCode(0).data(analyticalResponse)
                .build();
        return genericResponse;

    }
}
