package ru.clevertec.cleverbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.cleverbank.model.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
