package com.divij.expense_tracker.repository;

import com.divij.expense_tracker.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
        SELECT t FROM Transaction t
        WHERE (:category IS NULL OR t.category = :category)
          AND (:fromDate IS NULL OR t.txnDate >= :fromDate)
          AND (:toDate IS NULL OR t.txnDate <= :toDate)
        ORDER BY t.txnDate DESC, t.id DESC
    """)
    List<Transaction> search(
            @Param("category") String category,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
}
