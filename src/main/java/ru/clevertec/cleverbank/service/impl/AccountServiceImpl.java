package ru.clevertec.cleverbank.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.cleverbank.exception.EntityNotFoundException;
import ru.clevertec.cleverbank.mapper.AccountMapper;
import ru.clevertec.cleverbank.model.dto.request.AccountDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.AccountDtoResponse;
import ru.clevertec.cleverbank.model.dto.response.PageResponse;
import ru.clevertec.cleverbank.model.entity.Account;
import ru.clevertec.cleverbank.model.entity.Bank;
import ru.clevertec.cleverbank.model.entity.User;
import ru.clevertec.cleverbank.model.enums.Status;
import ru.clevertec.cleverbank.repository.AccountRepository;
import ru.clevertec.cleverbank.repository.BankRepository;
import ru.clevertec.cleverbank.repository.UserRepository;
import ru.clevertec.cleverbank.service.AccountService;

import java.util.List;

/**
 * Account Service to work with the Account entity
 *
 * @author Konstantin Voytko
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final AccountMapper accountMapper;

    /**
     * Save a new Account entity
     *
     * @param accountDtoRequest Account DTO to save
     * @return saved Account DTO
     */
    @Override
    @Transactional
    public AccountDtoResponse save(AccountDtoRequest accountDtoRequest) {
        Account account = accountMapper.toAccount(accountDtoRequest);

        User user = userRepository.findById(accountDtoRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(User.class, accountDtoRequest.getUserId()));

        Bank bank = bankRepository.findById(accountDtoRequest.getBankId())
                .orElseThrow(() -> new EntityNotFoundException(Bank.class, accountDtoRequest.getBankId()));

        account.setUser(user);
        account.setBank(bank);
        account.setStatus(Status.ACTIVE);

        Account savedAccount = accountRepository.save(account);
        return accountMapper.toAccountDtoResponse(savedAccount);
    }

    /**
     * Find all Account entities info
     *
     * @param pageable page number & page size values to find
     * @return all Account DTOs
     */
    @Override
    @Transactional
    public PageResponse<AccountDtoResponse> findAll(Pageable pageable) {
        Page<Account> accountPage = accountRepository.findAll(pageable);

        List<AccountDtoResponse> accountDtoResponses = accountPage.stream()
                .map(accountMapper::toAccountDtoResponse)
                .toList();

        return PageResponse.<AccountDtoResponse>builder()
                .content(accountDtoResponses)
                .number(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .numberOfElements(accountDtoResponses.size())
                .build();
    }

    /**
     * Find Account entity info by ID. Uses the Redis-cache implementation
     *
     * @param id Account ID to find
     * @throws EntityNotFoundException if the Account entity with ID doesn't exist
     * @return found Account DTO by ID
     */
    @Override
    @Transactional
    public AccountDtoResponse findById(String id) {
        return accountRepository.findById(id)
                .map(accountMapper::toAccountDtoResponse)
                .orElseThrow(() -> new EntityNotFoundException(Account.class, id));
    }

    /**
     * Delete an Account entity by ID
     *
     * @param id Account ID to delete
     * @throws EntityNotFoundException if the Account entity with ID doesn't exist
     * @return deleted Account DTO by ID
     */
    @Override
    @Transactional
    public AccountDtoResponse deleteById(String id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Account.class, id));
        account.setStatus(Status.DELETED);
        return accountMapper.toAccountDtoResponse(accountRepository.save(account));
    }
}
