package com.finance.controllers;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.finance.dto.TransactionRequestDto;
import com.finance.dto.TransactionResponseDto;
import com.finance.entities.Customer;
import com.finance.entities.FinanceEntity;
import com.finance.entities.TransactionEntity;
import com.finance.exception.NotFoundException;
import com.finance.services.CustomerService;
import com.finance.services.FinanceService;
import com.finance.services.TransactionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

  private final FinanceService financeService;
  private final CustomerService customerService;
  private final TransactionService transactionService;

  @Inject
  public TransactionController(final FinanceService financeService,
                               final CustomerService customerService,
                               final TransactionService transactionService) {
    this.financeService = financeService;
    this.customerService = customerService;
    this.transactionService = transactionService;
  }

  @RequestMapping(
      path = "/{finance_id}/customers/{customer_name}/transactions",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<TransactionResponseDto> create(@PathVariable("finance_id") final String financeId,
                                                       @PathVariable("customer_name") final String customerName,
                                                       @RequestBody final TransactionRequestDto requestDto) {
    Optional<FinanceEntity> finance = financeService.getFinance(financeId);
    if (!finance.isPresent()) {
      throw new NotFoundException(String.format("Finance with id:[%s] does not exists", financeId));
    }

    Optional<Customer> customer = customerService.getCustomer(financeId, customerName);
    if (!customer.isPresent()) {
      throw new NotFoundException(String.format("Customer with name:[%s] does not exists", customerName));
    }

    TransactionEntity transactionEntity = transactionService.create(financeId, customer.get(), requestDto);
    TransactionResponseDto responseDto = mapToResponse(transactionEntity);
    return ResponseEntity.ok(responseDto);
  }

  @RequestMapping(
      path = "/{finance_id}/customers/{customer_name}/transactions",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Set<TransactionResponseDto>> getAll(@PathVariable("finance_id") final String financeId,
                                                            @PathVariable("customer_name") final String customerName) {
    Optional<FinanceEntity> finance = financeService.getFinance(financeId);
    if (!finance.isPresent()) {
      throw new NotFoundException(String.format("Finance with id:[%s] does not exists", financeId));
    }

    Optional<Customer> customer = customerService.getCustomer(financeId, customerName);
    if (!customer.isPresent()) {
      throw new NotFoundException(String.format("Customer with name:[%s] does not exists", customerName));
    }

    Set<TransactionEntity> transactionEntities = transactionService.getAll(financeId, customer.get().getId());
    Set<TransactionResponseDto> responseDto = transactionEntities.stream().map(this::mapToResponse).collect(Collectors.toSet());
    return ResponseEntity.ok(responseDto);
  }

  private TransactionResponseDto mapToResponse(final TransactionEntity transactionEntity) {
    return new TransactionResponseDto(
        transactionEntity.getId(),
        transactionEntity.getReturnedAmount(),
        transactionEntity.getReturnedTo(),
        transactionEntity.getReturnedDate()
    );
  }

}
