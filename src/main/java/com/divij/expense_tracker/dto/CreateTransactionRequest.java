package com.divij.expense_tracker.dto;

import com.divij.expense_tracker.entity.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateTransactionRequest {

    @NotNull
    private TransactionType type;

    @NotBlank
    private String category;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private LocalDate txnDate;

    private String note;

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getTxnDate() { return txnDate; }
    public void setTxnDate(LocalDate txnDate) { this.txnDate = txnDate; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
