package com.expensetracker.backend.dto;

import java.math.BigDecimal;

public class MonthlySummary {

    private int year;
    private int month;
    private BigDecimal total;

    public MonthlySummary(int year, int month, BigDecimal total) {
        this.year = year;
        this.month = month;
        this.total = total;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public BigDecimal getTotal() {
        return total;
    }
}