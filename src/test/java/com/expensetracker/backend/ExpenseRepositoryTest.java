
package com.expensetracker.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.expensetracker.backend.model.Expense;
import com.expensetracker.backend.model.ExpenseCategory;
import com.expensetracker.backend.repository.ExpenseRepository;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class ExpenseRepositoryTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Test
    void shouldSaveAndFindExpense() {
        Expense expense = new Expense();
        expense.setTitle("Test lunch");
        expense.setAmount(new BigDecimal("250.00"));
        expense.setCategory(ExpenseCategory.FOOD);
        expense.setDate(LocalDate.of(2026, 10, 14));

        Expense savedExpense = expenseRepository.save(expense);

        assertThat(savedExpense.getId()).isNotNull();

        Expense foundExpense = expenseRepository
                .findById(savedExpense.getId())
                .orElseThrow();

        assertThat(foundExpense.getTitle()).isEqualTo("Test lunch");
        assertThat(foundExpense.getAmount())
                .isEqualByComparingTo("250.00");
        assertThat(foundExpense.getCategory())
                .isEqualTo(ExpenseCategory.FOOD);
    }
}
