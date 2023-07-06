package com.chandu.repository;

import com.chandu.model.ExpenseTypes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseTypes,Integer> {

    public ExpenseTypes findByExpenseType(String expenseType);


    public List<ExpenseTypes> findByExpenseTypeIn(String[] expenseType);
}
