package com.expensetracker.backend.controller;

import com.expensetracker.backend.model.Expense;
import com.expensetracker.backend.repository.ExpenseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping
    public List<Expense> getExpenses() {
        return expenseRepository.findAll();
    }

    @GetMapping("/{id}")
public Expense getExpenseById(@PathVariable Long id) {
    return expenseRepository.findById(id)
            .orElseThrow();
    }

    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        return expenseRepository.save(expense);
    }

    @PutMapping("/{id}")
public Expense updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
    Expense existingExpense = expenseRepository.findById(id)
            .orElseThrow();

    existingExpense.setTitle(expense.getTitle());
    existingExpense.setAmount(expense.getAmount());
    existingExpense.setCategory(expense.getCategory());
    existingExpense.setDate(expense.getDate());

    return expenseRepository.save(existingExpense);
}

@DeleteMapping("/{id}")
public void deleteExpense(@PathVariable Long id) {
    expenseRepository.deleteById(id);
}

}