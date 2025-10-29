package com.somnath.SpendWise.service;

import com.somnath.SpendWise.dto.ExpenseRequestDto;
import com.somnath.SpendWise.dto.ExpenseResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseService {

    ExpenseResponseDto addExpense(Long userId, ExpenseRequestDto expenseRequestDto);

    List<ExpenseResponseDto> getAllExpensesByUserId(Long userId);

    BigDecimal getRemainingBalance(Long userId);

    // 🆕 Fetch expenses month-wise
    List<ExpenseResponseDto> getExpensesByMonth(Long userId, int month, int year);

    // 🆕 Fetch only current month's expenses (for dashboard/report)
    List<ExpenseResponseDto> getCurrentMonthExpenses(Long userId);
}
