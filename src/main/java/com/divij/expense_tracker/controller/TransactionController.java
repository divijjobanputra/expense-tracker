package com.divij.expense_tracker.controller;

import com.divij.expense_tracker.dto.CreateTransactionRequest;
import com.divij.expense_tracker.dto.MonthlySummaryResponse;
import com.divij.expense_tracker.dto.TransactionResponse;
import com.divij.expense_tracker.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("/transactions")
    public TransactionResponse create(@Valid @RequestBody CreateTransactionRequest req) {
        return service.create(req);
    }

    @GetMapping("/transactions")
    public List<TransactionResponse> list(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return service.list(category, from, to);
    }

    @DeleteMapping("/transactions/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/summary")
    public MonthlySummaryResponse summary(@RequestParam String month) {
        return service.monthlySummary(month);
    }
}
