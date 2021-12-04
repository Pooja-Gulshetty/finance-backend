package com.finance.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import javax.inject.Inject;

import com.finance.dto.TransactionRequestDto;
import com.finance.entities.Customer;
import com.finance.entities.TransactionEntity;
import com.finance.repositories.CustomerRepository;
import com.finance.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private final TransactionRepository repository;
  private final CustomerRepository customerRepository;

  @Inject
  public TransactionService(final TransactionRepository repository,
                            final CustomerRepository customerRepository) {
    this.repository = repository;
    this.customerRepository = customerRepository;
  }

  public TransactionEntity create(final String financeId, final Customer customer, final TransactionRequestDto requestDto) {
    final TransactionEntity transactionEntity = mapToTransaction(financeId, customer.getId(), requestDto);
    updateCustomer(customer, transactionEntity);
    return repository.save(transactionEntity);
  }

  private void updateCustomer(final Customer customer, final TransactionEntity transactionEntity) {
    final long remainingAmount = customer.getRemainingAmount() - transactionEntity.getReturnedAmount();
    customer.setRemainingAmount(remainingAmount);
    customerRepository.save(customer);
  }

  public Set<TransactionEntity> getAll(final String financeId, final long customerId) {
    return repository.getAllTransaction(financeId, customerId);
  }

  private TransactionEntity mapToTransaction(final String financeId, final long customerId, final TransactionRequestDto requestDto) {
    return new TransactionEntity(
        financeId,
        customerId,
        requestDto.getReturnedAmount(),
        ZonedDateTime.now(ZoneId.of("UTC")),
        requestDto.getReturnedTo()
    );
  }
}
