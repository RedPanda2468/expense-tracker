package com.expensetracker.backend.controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import com.expensetracker.backend.dto.CategorySummary;
import com.expensetracker.backend.dto.MonthlySummary;
import java.math.BigDecimal;

import com.expensetracker.backend.dto.CategorySummary;
import com.expensetracker.backend.dto.MonthlySummary;
import java.math.BigDecimal;


import java.time.LocalDate;
import jakarta.validation.Valid;
import com.expensetracker.backend.model.Expense;
import com.expensetracker.backend.model.ExpenseCategory;
import com.expensetracker.backend.service.ExpenseService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.web.bind.annotation.RequestParam;


import com.expensetracker.backend.dto.ExpenseRequest;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    @GetMapping("/page")
public Page<Expense> getExpensesPaginated(
        @PageableDefault(size = 5) Pageable pageable) {
    return expenseService.getExpensesPaginated(pageable);
}

@GetMapping("/summary/range")
public BigDecimal getTotalExpensesBetween(
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate startDate,

        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate endDate) {

    if (startDate.isAfter(endDate)) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "startDate must be on or before endDate"
        );
    }

    return expenseService.getTotalExpensesBetween(startDate, endDate);
}
@GetMapping("/summary")
public BigDecimal getTotalExpenses() {
    return expenseService.getTotalExpenses();
}

@GetMapping("/summary/by-category")
public List<CategorySummary> getCategorySummary() {
    return expenseService.getCategorySummary();
}

@GetMapping("/summary/monthly")
public List<MonthlySummary> getMonthlySummary() {
    return expenseService.getMonthlySummary();
}

    
    @GetMapping("/filter")
    public List<Expense> filterExpenses(
        @RequestParam(required = false) ExpenseCategory category,
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate) {

    if (startDate != null && endDate != null && category != null) {
        return expenseService.getExpensesByCategoryAndDateRange(
                category, startDate, endDate);
    }

    if (category != null) {
        return expenseService.getExpensesByCategory(category);
    }

    if (startDate != null && endDate != null) {
        return expenseService.getExpensesByDateRange(startDate, endDate);
    }

    return expenseService.getAllExpenses();
}


    @GetMapping
    public List<Expense> getExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }

    @PostMapping
public Expense createExpense(@Valid @RequestBody ExpenseRequest request) {

    Expense expense = new Expense();

    expense.setTitle(request.getTitle());
    expense.setAmount(request.getAmount());
    expense.setCategory(request.getCategory());
    expense.setDate(request.getDate());

    return expenseService.createExpense(expense);
}

   @PutMapping("/{id}")
public Expense updateExpense(
        @PathVariable Long id,
        @Valid @RequestBody ExpenseRequest request) {

    Expense expense = new Expense();

    expense.setTitle(request.getTitle());
    expense.setAmount(request.getAmount());
    expense.setCategory(request.getCategory());
    expense.setDate(request.getDate());

    return expenseService.updateExpense(id, expense);
}

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }
}