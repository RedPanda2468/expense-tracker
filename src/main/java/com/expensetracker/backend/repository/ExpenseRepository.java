package com.expensetracker.backend.repository;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.expensetracker.backend.model.Expense;
import com.expensetracker.backend.model.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

        @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e")
BigDecimal getTotalExpenses();

@Query("""
    SELECT COALESCE(SUM(e.amount), 0)
    FROM Expense e
    WHERE e.date BETWEEN :startDate AND :endDate
    """)
BigDecimal getTotalExpensesBetween(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
);

@Query("""
    SELECT e.category, SUM(e.amount)
    FROM Expense e
    GROUP BY e.category
    """)
List<Object[]> getExpensesByCategorySummary();

@Query("""
    SELECT YEAR(e.date), MONTH(e.date), SUM(e.amount)
    FROM Expense e
    GROUP BY YEAR(e.date), MONTH(e.date)
    ORDER BY YEAR(e.date), MONTH(e.date)
    """)
List<Object[]> getMonthlyExpensesSummary();

    List<Expense> findByCategory(ExpenseCategory category);

    List<Expense> findByDateBetween(
            LocalDate startDate, LocalDate endDate);

    List<Expense> findByCategoryAndDateBetween(
            ExpenseCategory category,
            LocalDate startDate,
            LocalDate endDate);
}
