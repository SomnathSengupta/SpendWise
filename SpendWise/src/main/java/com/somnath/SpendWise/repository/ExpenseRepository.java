package com.somnath.SpendWise.repository;

import com.somnath.SpendWise.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    /**
     * Fetch all expenses for a specific user.
     */
    List<Expense> findByUserId(Long userId);

    /**
     * Fetch all expenses for a specific user within a particular month and year.
     * This will be used for monthly and current-month reports.
     */
    @Query("""
           SELECT e FROM Expense e
           WHERE e.user.id = :userId
             AND FUNCTION('MONTH', e.date) = :month
             AND FUNCTION('YEAR', e.date) = :year
           ORDER BY e.date DESC
           """)
    List<Expense> findByUserIdAndMonthAndYear(
            @Param("userId") Long userId,
            @Param("month") int month,
            @Param("year") int year
    );
}
