package ru.clevertec.cleverbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.clevertec.cleverbank.model.entity.Account;

import java.math.BigDecimal;

public interface AccountRepository extends JpaRepository<Account, String> {

    @Query(value = "select sum(amount) from accounts a " +
            "left join transactions t on :id = t.consumer_id " +
            "where a.id = :id",
            nativeQuery = true)
    BigDecimal findReplenishmentSumById(@Param("id") String id);

    @Query(value = "select sum(amount) from accounts a " +
            "left join transactions t on :id = t.supplier_id " +
            "where a.id = :id",
            nativeQuery = true)
    BigDecimal findWithdrawalSumById(@Param("id") String id);
}
