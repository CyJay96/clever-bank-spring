package ru.clevertec.cleverbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.cleverbank.model.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
