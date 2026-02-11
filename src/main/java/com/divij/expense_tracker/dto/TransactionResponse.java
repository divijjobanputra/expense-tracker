package com.divij.expense_tracker.dto;

import com.divij.expense_tracker.entity.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionResponse {

    private Long id;
    private TransactionType type;
    private String category;
    private BigDecimal amount;
    private LocalDate txnDate;
    private String note;

    public TransactionResponse(Long id, TransactionType type, String category, BigDecimal amount, LocalDate txnDate, String note) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.txnDate = txnDate;
        this.note = note;
    }

    public Long getId() { return id; }
    public TransactionType getType() { return type; }
    public String getCategory() { return category; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getTxnDate() { return txnDate; }
    public String getNote() { return note; }
}
