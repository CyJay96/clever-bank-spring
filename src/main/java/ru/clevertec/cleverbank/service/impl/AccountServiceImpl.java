package ru.clevertec.cleverbank.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.cleverbank.exception.EntityNotFoundException;
import ru.clevertec.cleverbank.mapper.AccountMapper;
import ru.clevertec.cleverbank.model.dto.request.AccountDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.AccountDtoResponse;
import ru.clevertec.cleverbank.model.dto.response.PageResponse;
import ru.clevertec.cleverbank.model.entity.Account;
import ru.clevertec.cleverbank.repository.AccountRepository;
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
    private final AccountMapper accountMapper;

    /**
     * Save a new Account entity
     *
     * @param accountDtoRequest Account DTO to save
     * @return saved Account DTO
     */
    @Override
    public AccountDtoResponse save(AccountDtoRequest accountDtoRequest) {
        Account account = accountMapper.toAccount(accountDtoRequest);
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
    public AccountDtoResponse findById(Long id) {
        return accountRepository.findById(id)
                .map(accountMapper::toAccountDtoResponse)
                .orElseThrow(() -> new EntityNotFoundException(Account.class, id));
    }

    /**
     * Update an existing Account entity info by ID
     *
     * @param id Account ID to update
     * @param accountDtoRequest Account DTO to update
     * @throws EntityNotFoundException if the Account entity with ID doesn't exist
     * @return updated Account DTO by ID
     */
    @Override
    public AccountDtoResponse update(Long id, AccountDtoRequest accountDtoRequest) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Account.class, id));
        accountMapper.updateAccount(accountDtoRequest, account);
        return accountMapper.toAccountDtoResponse(accountRepository.save(account));
    }

    /**
     * Delete an Account entity by ID
     *
     * @param id Account ID to delete
     * @throws EntityNotFoundException if the Account entity with ID doesn't exist
     */
    @Override
    public void deleteById(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new EntityNotFoundException(Account.class, id);
        }

        accountRepository.deleteById(id);
    }
}
