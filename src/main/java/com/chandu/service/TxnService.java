package com.chandu.service;

import com.chandu.model.*;
import com.chandu.repository.ExpenseTypeRepository;
import com.chandu.repository.TxnDetailsRepository;
import com.chandu.repository.UserRepository;
import com.chandu.request.CreateTxnRequest;
import com.chandu.response.AnalyticalResponse;
import com.chandu.response.CreateTxnResponse;
import com.chandu.response.TxnSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TxnService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    private TxnDetailsRepository txnDetailsRepository;

    public CreateTxnResponse addUserTxn(CreateTxnRequest createTxnRequest) {

        // user if not exist -> email
        // expense type  ->  if it will not be there
        // txn in txndetails

        User userFromDb = userRepository.findByEmail(createTxnRequest.getUserEmail());

        // custom exception
        if(userFromDb == null){
            User user = User.builder().
                    email(createTxnRequest.getUserEmail()).
                    userStatus(UserStatus.ACTIVE).
                    build();
            userFromDb = userRepository.save(user);
        }

        ExpenseTypes expenseTypesFromDb  = expenseTypeRepository.findByExpenseType(createTxnRequest.getExpenseType());

        if(expenseTypesFromDb == null){
            ExpenseTypes expenseTypes = ExpenseTypes.builder().
                    expenseType(createTxnRequest.getExpenseType()).
                    createdBy(createTxnRequest.getUserEmail()).build();
            expenseTypesFromDb = expenseTypeRepository.save(expenseTypes);
        }

        TxnDetails txnDetails = createTxnRequest.toTxnDetails(createTxnRequest);
        txnDetails.setUser(userFromDb);
        txnDetails.setExpenseTypes(expenseTypesFromDb);
        TxnDetails txnDetailsFromDb = txnDetailsRepository.save(txnDetails);

        return  CreateTxnResponse.builder().userId(userFromDb.getId()).expenseId(expenseTypesFromDb.getId()).build();

    }

    public List<TxnSearchResponse> fetchUserTxnDetails(TxnFilterType txnFilterType, TxnFilterOperators operators, String[] values) throws ParseException {
        List<TxnSearchResponse> list = new ArrayList<>();
        List<TxnDetails> txnDetailsList = new ArrayList<>();

        // first of all i will be getting expense ids basis on expense types;

        List<ExpenseTypes> expenseIds= expenseTypeRepository.findByExpenseTypeIn(values);
//        Integer arr[] = expenseIds
        switch (txnFilterType) {
            case EXPENSE_TYPE: // food , travel >= , <=
                txnDetailsList.addAll(txnDetailsRepository.findByExpenseTypesIn(values));
                break;
            case EXPENDITURE_AMOUNT:
                switch (operators){
                    case EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenditureAmount(Double.valueOf(values[0])));
                        break;
                    case LESS_THAN_EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenditureAmountLessThanEqual(Double.valueOf(values[0])));
                        break;
                }
                break;
            case EXPENSE_DATE:
                switch (operators){
                    case LESS_THAN_EQUALS:
                        txnDetailsList.addAll(txnDetailsRepository.findByExpenseDateLessThanEqual(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(values[0])));
                        break;
                }
                break;
        }
        list = convertToSearchResponse(txnDetailsList);
        return list;
    }

    private List<TxnSearchResponse> convertToSearchResponse(List<TxnDetails> txnDetailsList) {
        List<TxnSearchResponse> txnSearchResponses = new ArrayList<>();

        for(int i= 0; i< txnDetailsList.size();i++){
            TxnSearchResponse txnSearchResponse = TxnSearchResponse.builder().
                    user(txnDetailsList.get(i).getUser()).
                    expenditureAmount(txnDetailsList.get(i).getExpenditureAmount()).
                    expenseDate(txnDetailsList.get(i).getExpenseDate().toString()).
                    expenseType(txnDetailsList.get(i).getExpenseTypes().getExpenseType()).build();
            txnSearchResponses.add(txnSearchResponse);
        }
        return txnSearchResponses;
    }

    public AnalyticalResponse fetchCalculatedResponse(String email) {

        // form the date
        LocalDate todayDate = LocalDate.now();
        LocalDate sevenDayBackDate = LocalDate.now().minusDays(7);

        User user =userRepository.findByEmail(email);
        Double oneDayAmount = txnDetailsRepository.getAggregatedData(todayDate, user.getId());
        Double sevenDayAmount = txnDetailsRepository.getAggregatedData(sevenDayBackDate, user.getId());

        return AnalyticalResponse.builder().
                userEmail(email).
                oneDayAmount(oneDayAmount).sevenDayAmount(sevenDayAmount).build();


        // select sum(expenditure_cost)  from txnDetails where expenseDate >= date and userid = userid
    }
}