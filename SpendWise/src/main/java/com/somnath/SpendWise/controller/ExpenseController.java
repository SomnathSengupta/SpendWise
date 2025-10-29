package com.somnath.SpendWise.controller;

import com.somnath.SpendWise.dto.ExpenseRequestDto;
import com.somnath.SpendWise.dto.ExpenseResponseDto;
import com.somnath.SpendWise.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    /**
     *  Add a new expense for a specific user
     */
    @PostMapping("/{userId}/add")
    public ResponseEntity<ExpenseResponseDto> addExpense(
            @PathVariable Long userId,
            @RequestBody ExpenseRequestDto expenseRequestDto) {

        ExpenseResponseDto createdExpense = expenseService.addExpense(userId, expenseRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }

    /**
     * Get all expenses of a user
     */
    @GetMapping("/{userId}/all")
    public ResponseEntity<List<ExpenseResponseDto>> getAllExpensesByUser(@PathVariable Long userId) {
        List<ExpenseResponseDto> expenses = expenseService.getAllExpensesByUserId(userId);
        return ResponseEntity.ok(expenses);
    }

    /**
     * Get remaining in-hand balance for a user
     */
    @GetMapping("/{userId}/balance")
    public ResponseEntity<BigDecimal> getRemainingBalance(@PathVariable Long userId) {
        BigDecimal remainingBalance = expenseService.getRemainingBalance(userId);
        return ResponseEntity.ok(remainingBalance);
    }

    /**
     *  Get expenses by a specific month and year
     */
    @GetMapping("/{userId}/month")
    public ResponseEntity<List<ExpenseResponseDto>> getExpensesByMonth(
            @PathVariable Long userId,
            @RequestParam int month,
            @RequestParam int year) {

        return ResponseEntity.ok(expenseService.getExpensesByMonth(userId, month, year));
    }

    /**
     * Get only current month's expenses (for dashboard/report)
     */
    @GetMapping("/{userId}/current-month")
    public ResponseEntity<List<ExpenseResponseDto>> getCurrentMonthExpenses(@PathVariable Long userId) {
        return ResponseEntity.ok(expenseService.getCurrentMonthExpenses(userId));
    }
}
