package com.divij.expense_tracker.service;

import com.divij.expense_tracker.dto.CreateTransactionRequest;
import com.divij.expense_tracker.dto.MonthlySummaryResponse;
import com.divij.expense_tracker.dto.TransactionResponse;
import com.divij.expense_tracker.entity.Transaction;
import com.divij.expense_tracker.entity.TransactionType;
import com.divij.expense_tracker.exception.NotFoundException;
import com.divij.expense_tracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository repo;

    public TransactionService(TransactionRepository repo) {
        this.repo = repo;
    }

    public TransactionResponse create(CreateTransactionRequest req) {
        Transaction t = new Transaction(
                req.getType(),
                req.getCategory().trim(),
                req.getAmount(),
                req.getTxnDate(),
                req.getNote()
        );

        Transaction saved = repo.save(t);
        return toResponse(saved);
    }

    public List<TransactionResponse> list(String category, LocalDate from, LocalDate to) {
        String cleanedCategory = (category == null || category.isBlank()) ? null : category.trim();
        List<Transaction> results = repo.search(cleanedCategory, from, to);
        return results.stream().map(this::toResponse).toList();
    }

    public void delete(Long id) {
        Transaction t = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found: " + id));
        repo.delete(t);
    }

    public MonthlySummaryResponse monthlySummary(String month) {
        if (month == null || month.isBlank()) {
            throw new IllegalArgumentException("month is required in format YYYY-MM");
        }

        YearMonth ym;
        try {
            ym = YearMonth.parse(month);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid month format. Use YYYY-MM. Example: 2026-02");
        }

        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        List<Transaction> txns = repo.search(null, start, end);

        BigDecimal income = BigDecimal.ZERO;
        BigDecimal expense = BigDecimal.ZERO;

        for (Transaction t : txns) {
            if (t.getType() == TransactionType.INCOME) {
                income = income.add(t.getAmount());
            } else {
                expense = expense.add(t.getAmount());
            }
        }

        BigDecimal balance = income.subtract(expense);
        return new MonthlySummaryResponse(income, expense, balance);
    }

    private TransactionResponse toResponse(Transaction t) {
        return new TransactionResponse(
                t.getId(),
                t.getType(),
                t.getCategory(),
                t.getAmount(),
                t.getTxnDate(),
                t.getNote()
        );
    }
}
