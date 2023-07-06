package com.chandu.service;

import com.chandu.model.ExpenseTypes;
import com.chandu.model.User;
import com.chandu.model.UserStatus;
import com.chandu.repository.ExpenseTypeRepository;
import com.chandu.repository.UserRepository;
import com.chandu.request.CreateExpenseTypeRequest;
import com.chandu.response.CreateExpenseTypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseTypeService {
    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    private UserRepository userRepository;

    public CreateExpenseTypeResponse addExpenseType(CreateExpenseTypeRequest expenseTypeRequest) {
        ExpenseTypes expenseTypesFromDB  = expenseTypeRepository.findByExpenseType(expenseTypeRequest.getExpenseType());
        if(expenseTypesFromDB == null){
            ExpenseTypes expenseTypes = expenseTypeRequest.toExpenseTypes();
            expenseTypesFromDB = expenseTypeRepository.save(expenseTypes);
        }
        // handle ->  user is not present -> say -> then we will create an entry
        User userFromDB = userRepository.findByEmail(expenseTypesFromDB.getCreatedBy());
        if(userFromDB == null){
            User user = User.builder().
                    email(expenseTypeRequest.getUserEmail()).
                    userStatus(UserStatus.ACTIVE).
                    build();
            userFromDB = userRepository.save(user);
        }

        CreateExpenseTypeResponse createExpenseTypeResponse = CreateExpenseTypeResponse.builder().
                expenseId(expenseTypesFromDB.getId()).
                userId(userFromDB.getId()).
                build();

        return createExpenseTypeResponse;
    }
}
