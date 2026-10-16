package com.expensetracker.backend.service;
import com.expensetracker.backend.dto.CategorySummary;
import com.expensetracker.backend.exception.ExpenseNotFoundException;
import com.expensetracker.backend.dto.MonthlySummary;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.expensetracker.backend.model.Expense;
import com.expensetracker.backend.model.ExpenseCategory;
import com.expensetracker.backend.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {
    public Page<Expense> getExpensesPaginated(Pageable pageable) {
    return expenseRepository.findAll(pageable);
}
public BigDecimal getTotalExpensesBetween(
        LocalDate startDate, LocalDate endDate) {
    return expenseRepository.getTotalExpensesBetween(startDate, endDate);
}

public BigDecimal getTotalExpenses() {
    return expenseRepository.getTotalExpenses();
}

public List<CategorySummary> getCategorySummary() {
    List<Object[]> rows = expenseRepository.getExpensesByCategorySummary();
    List<CategorySummary> result = new ArrayList<>();

    for (Object[] row : rows) {
        result.add(new CategorySummary(
                (ExpenseCategory) row[0],
                (BigDecimal) row[1]
        ));
    }

    return result;
}

public List<MonthlySummary> getMonthlySummary() {
    List<Object[]> rows = expenseRepository.getMonthlyExpensesSummary();
    List<MonthlySummary> result = new ArrayList<>();

    for (Object[] row : rows) {
        result.add(new MonthlySummary(
                ((Number) row[0]).intValue(),
                ((Number) row[1]).intValue(),
                (BigDecimal) row[2]
        ));
    }

    return result;
}

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // Get all expenses
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    // Get one expense
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
        .orElseThrow(() -> new ExpenseNotFoundException(id));
    }

    // Create expense
    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    // Update expense
    public Expense updateExpense(Long id, Expense expense) {
        Expense existingExpense = expenseRepository.findById(id)
        .orElseThrow(() -> new ExpenseNotFoundException(id));

        existingExpense.setTitle(expense.getTitle());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setCategory(expense.getCategory());
        existingExpense.setDate(expense.getDate());

        return expenseRepository.save(existingExpense);
    }

    // Delete expense
    public void deleteExpense(Long id) {
    if (!expenseRepository.existsById(id)) {
        throw new ExpenseNotFoundException(id);
    }

    expenseRepository.deleteById(id);
}

    // Filter by category
    public List<Expense> getExpensesByCategory(ExpenseCategory category) {
        return expenseRepository.findByCategory(category);
    }

    // Filter by date range
    public List<Expense> getExpensesByDateRange(
            LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByDateBetween(startDate, endDate);
    }

    // Filter by category and date range
    public List<Expense> getExpensesByCategoryAndDateRange(
            ExpenseCategory category,
            LocalDate startDate,
            LocalDate endDate) {
        return expenseRepository.findByCategoryAndDateBetween(
                category, startDate, endDate);
    }
}
