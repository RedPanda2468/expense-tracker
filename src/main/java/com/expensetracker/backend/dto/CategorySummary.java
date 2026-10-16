package com.expensetracker.backend.dto;

import com.expensetracker.backend.model.ExpenseCategory;
import java.math.BigDecimal;

public class CategorySummary {

    private ExpenseCategory category;
    private BigDecimal total;

    public CategorySummary(ExpenseCategory category, BigDecimal total) {
        this.category = category;
        this.total = total;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public BigDecimal getTotal() {
        return total;
    }
}