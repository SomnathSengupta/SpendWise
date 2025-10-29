package com.somnath.SpendWise.service.Impl;

import com.somnath.SpendWise.dto.ExpenseRequestDto;
import com.somnath.SpendWise.dto.ExpenseResponseDto;
import com.somnath.SpendWise.entity.Expense;
import com.somnath.SpendWise.entity.User;
import com.somnath.SpendWise.repository.ExpenseRepository;
import com.somnath.SpendWise.repository.UserRepository;
import com.somnath.SpendWise.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ExpenseResponseDto addExpense(Long userId, ExpenseRequestDto expenseRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

        Expense expense = modelMapper.map(expenseRequestDto, Expense.class);
        expense.setUser(user);

        // Ensure expense has timestamp
        if (expense.getDate() == null) {
            expense.setDate(LocalDateTime.now());
        }

        BigDecimal expenseAmount = expense.getAmount();
        BigDecimal currentBalance = user.getMonthlyInHand() != null ? user.getMonthlyInHand() : BigDecimal.ZERO;

        // Deduct expense from current month's in-hand balance
        user.setMonthlyInHand(currentBalance.subtract(expenseAmount));

        // Save updated user and new expense
        userRepository.save(user);
        Expense savedExpense = expenseRepository.save(expense);

        return modelMapper.map(savedExpense, ExpenseResponseDto.class);
    }

    @Override
    public List<ExpenseResponseDto> getAllExpensesByUserId(Long userId) {
        validateUser(userId);

        return expenseRepository.findByUserId(userId).stream()
                .map(expense -> modelMapper.map(expense, ExpenseResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getRemainingBalance(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

        return user.getMonthlyInHand() != null ? user.getMonthlyInHand() : BigDecimal.ZERO;
    }

    // Fetch expenses for a specific month and year (optimized to query DB directly)
    @Override
    public List<ExpenseResponseDto> getExpensesByMonth(Long userId, int month, int year) {
        validateUser(userId);

        List<Expense> expenses = expenseRepository.findByUserIdAndMonthAndYear(userId, month, year);

        return expenses.stream()
                .map(expense -> modelMapper.map(expense, ExpenseResponseDto.class))
                .collect(Collectors.toList());
    }

    // Fetch only current month's expenses
    @Override
    public List<ExpenseResponseDto> getCurrentMonthExpenses(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        return getExpensesByMonth(userId, now.getMonthValue(), now.getYear());
    }

    // Utility method to verify user existence
    private void validateUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("User not found with ID: " + userId);
        }
    }
}
