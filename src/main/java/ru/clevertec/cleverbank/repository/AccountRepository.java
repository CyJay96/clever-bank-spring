package ru.clevertec.cleverbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.cleverbank.model.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
