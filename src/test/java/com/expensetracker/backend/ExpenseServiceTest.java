
package com.expensetracker.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.expensetracker.backend.model.Expense;
import com.expensetracker.backend.model.ExpenseCategory;
import com.expensetracker.backend.repository.ExpenseRepository;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    void shouldReturnAllExpenses() {
        Expense expense = new Expense();
        expense.setTitle("Lunch");
        expense.setAmount(new BigDecimal("250.00"));
        expense.setCategory(ExpenseCategory.FOOD);
        expense.setDate(LocalDate.of(2026, 10, 16));

        when(expenseRepository.findAll()).thenReturn(List.of(expense));

        List<Expense> result = expenseService.getAllExpenses();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Lunch");
        verify(expenseRepository).findAll();
    }

    @Test
    void shouldFilterExpensesByCategory() {
        Expense expense = new Expense();
        expense.setTitle("Bus");
        expense.setCategory(ExpenseCategory.TRANSPORT);

        when(expenseRepository.findByCategory(ExpenseCategory.TRANSPORT))
                .thenReturn(List.of(expense));

        List<Expense> result =
                expenseService.getExpensesByCategory(ExpenseCategory.TRANSPORT);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory())
                .isEqualTo(ExpenseCategory.TRANSPORT);
    }
}
