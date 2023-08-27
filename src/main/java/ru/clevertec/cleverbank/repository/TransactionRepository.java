package ru.clevertec.cleverbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.clevertec.cleverbank.model.entity.Transaction;

import java.time.OffsetDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select * from transactions t " +
            "where t.create_date >= :date and " +
            "(t.supplier_id = :accountId or t.consumer_id = :accountId)",
            nativeQuery = true)
    List<Transaction> findAllByAccounts(@Param("accountId") String accountId, @Param("date") OffsetDateTime date);
}
