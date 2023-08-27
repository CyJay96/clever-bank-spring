package ru.clevertec.cleverbank.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.cleverbank.exception.EntityNotFoundException;
import ru.clevertec.cleverbank.mapper.BankMapper;
import ru.clevertec.cleverbank.model.dto.request.BankDtoRequest;
import ru.clevertec.cleverbank.model.dto.response.BankDtoResponse;
import ru.clevertec.cleverbank.model.dto.response.PageResponse;
import ru.clevertec.cleverbank.model.entity.Bank;
import ru.clevertec.cleverbank.model.enums.Status;
import ru.clevertec.cleverbank.repository.BankRepository;
import ru.clevertec.cleverbank.service.BankService;

import java.util.List;

/**
 * Bank Service to work with the Bank entity
 *
 * @author Konstantin Voytko
 */
@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final BankMapper bankMapper;

    /**
     * Save a new Bank entity
     *
     * @param bankDtoRequest Bank DTO to save
     * @return saved Bank DTO
     */
    @Override
    @Transactional
    public BankDtoResponse save(BankDtoRequest bankDtoRequest) {
        Bank bank = bankMapper.toBank(bankDtoRequest);
        bank.setStatus(Status.ACTIVE);
        Bank savedBank = bankRepository.save(bank);
        return bankMapper.toBankDtoResponse(savedBank);
    }

    /**
     * Find all Bank entities info
     *
     * @param pageable page number & page size values to find
     * @return all Bank DTOs
     */
    @Override
    @Transactional
    public PageResponse<BankDtoResponse> findAll(Pageable pageable) {
        Page<Bank> bankPage = bankRepository.findAll(pageable);

        List<BankDtoResponse> bankDtoResponses = bankPage.stream()
                .map(bankMapper::toBankDtoResponse)
                .toList();

        return PageResponse.<BankDtoResponse>builder()
                .content(bankDtoResponses)
                .number(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .numberOfElements(bankDtoResponses.size())
                .build();
    }

    /**
     * Find Bank entity info by ID. Uses the Redis-cache implementation
     *
     * @param id Bank ID to find
     * @throws EntityNotFoundException if the Bank entity with ID doesn't exist
     * @return found Bank DTO by ID
     */
    @Override
    @Transactional
    public BankDtoResponse findById(Long id) {
        return bankRepository.findById(id)
                .map(bankMapper::toBankDtoResponse)
                .orElseThrow(() -> new EntityNotFoundException(Bank.class, id));
    }

    /**
     * Update an existing Bank entity info by ID
     *
     * @param id Bank ID to update
     * @param bankDtoRequest Bank DTO to update
     * @throws EntityNotFoundException if the Bank entity with ID doesn't exist
     * @return updated Bank DTO by ID
     */
    @Override
    @Transactional
    public BankDtoResponse update(Long id, BankDtoRequest bankDtoRequest) {
        Bank bank = bankRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Bank.class, id));
        bankMapper.updateBank(bankDtoRequest, bank);
        return bankMapper.toBankDtoResponse(bankRepository.save(bank));
    }

    /**
     * Delete a Bank entity by ID
     *
     * @param id Bank ID to delete
     * @throws EntityNotFoundException if the Bank entity with ID doesn't exist
     * @return deleted Bank DTO by ID
     */
    @Override
    @Transactional
    public BankDtoResponse deleteById(Long id) {
        Bank bank = bankRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Bank.class, id));
        bank.setStatus(Status.DELETED);
        return bankMapper.toBankDtoResponse(bankRepository.save(bank));
    }
}
