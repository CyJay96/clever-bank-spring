package ru.clevertec.cleverbank.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.cleverbank.repository.TransactionRepository;
import ru.clevertec.cleverbank.service.TransactionService;

/**
 * Transaction Service to work with the Transaction entity
 *
 * @author Konstantin Voytko
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
}
