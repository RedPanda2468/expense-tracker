package com.expensetracker.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.expensetracker.backend.model.ExpenseCategory;

import java.time.LocalDate;

import com.expensetracker.backend.model.ExpenseCategory;

public class ExpenseRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @Positive(message = "Amount must be greater than 0")
    private double amount;

    @NotBlank(message = "Category is required")
    private ExpenseCategory category;

    @NotNull(message = "Date is required")
    private LocalDate date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public ExpenseCategory getCategory() {
    return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
}
}