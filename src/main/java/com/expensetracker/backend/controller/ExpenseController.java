package com.expensetracker.backend.controller;
import jakarta.validation.Valid;
import com.expensetracker.backend.model.Expense;
import com.expensetracker.backend.service.ExpenseService;
import org.springframework.web.bind.annotation.*;
import com.expensetracker.backend.dto.ExpenseRequest;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
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