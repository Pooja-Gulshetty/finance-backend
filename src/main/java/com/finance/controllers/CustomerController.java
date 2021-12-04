package com.finance.controllers;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.finance.dto.CustomerRequestDto;
import com.finance.dto.CustomerResponseDto;
import com.finance.entities.Customer;
import com.finance.entities.FinanceEntity;
import com.finance.exception.BadRequestException;
import com.finance.exception.ConflictException;
import com.finance.exception.NotFoundException;
import com.finance.services.CustomerService;
import com.finance.services.FinanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
  private final FinanceService financeService;
  private final CustomerService customerService;

  @Inject
  public CustomerController(final FinanceService financeService, final CustomerService customerService) {
    this.financeService = financeService;
    this.customerService = customerService;
  }

  @Transactional
  @RequestMapping(
      path = "/{finance_id}/customers",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<CustomerResponseDto> createCustomer(@PathVariable("finance_id") final String financeId,
                                                            @RequestBody CustomerRequestDto requestDto) {

    LOGGER.info("Creating customer for finance:[{}] and customer name:[{}]", financeId, requestDto.getCustomerName());
    validateRequest(requestDto);

    Optional<FinanceEntity> finance = financeService.getFinance(financeId);

    if (!finance.isPresent()) {
      throw new NotFoundException(noFinanceFound(financeId));
    }

    Optional<Customer> customer = customerService.getCustomer(financeId, requestDto.getCustomerName());
    if (customer.isPresent()) {
      throw new ConflictException(String.format("Customer:[%s] already exists", requestDto.getCustomerName()));
    }

    final Customer savedCustomer = customerService.createCustomer(financeId, requestDto);
    final CustomerResponseDto responseDto = mapToResponse(savedCustomer);

    return ResponseEntity.ok(responseDto);
  }

  private String noFinanceFound(final String financeId) {
    return String.format("Finance with id:[%s] does not exists", financeId);
  }

  @Transactional
  @RequestMapping(
      path = "/{finance_id}/customers",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Set<CustomerResponseDto>> getAllCustomers(@PathVariable("finance_id") final String financeId) {
    final Optional<FinanceEntity> finance = financeService.getFinance(financeId);
    if (!finance.isPresent()) {
      throw new NotFoundException(noFinanceFound(financeId));
    }

    final Set<CustomerResponseDto> customers = customerService
        .getCustomers(financeId)
        .stream()
        .map(this::mapToResponse)
        .collect(Collectors.toSet());

    return ResponseEntity.ok(customers);
  }

  @Transactional
  @RequestMapping(
      path = "/{finance_id}/customers/{customer_name}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<CustomerResponseDto> getAllCustomer(@PathVariable("finance_id") final String financeId,
                                                            @PathVariable("customer_name") final String customerName) {
    final Optional<FinanceEntity> finance = financeService.getFinance(financeId);
    if (!finance.isPresent()) {
      throw new NotFoundException(noFinanceFound(financeId));
    }

    Optional<Customer> customer = customerService.getCustomer(financeId, customerName);
    if (!customer.isPresent()) {
      throw new NotFoundException(String.format("Customer:[%s] does not exists", customerName));
    }

    final CustomerResponseDto responseDto = mapToResponse(customer.get());
    return ResponseEntity.ok(responseDto);
  }

  private CustomerResponseDto mapToResponse(final Customer savedCustomer) {
    return new CustomerResponseDto(
        savedCustomer.getId(),
        savedCustomer.getCustomerName(),
        savedCustomer.getEmail(),
        savedCustomer.getPhoneNumber(),
        savedCustomer.getLendingDate(),
        savedCustomer.getEndDate(),
        savedCustomer.getMonthlyPaymentDate(),
        savedCustomer.getTotalAmount(),
        savedCustomer.getMonthlyPayment(),
        savedCustomer.getRemainingAmount(),
        savedCustomer.getInterestRate(),
        savedCustomer.getMonths(),
        savedCustomer.getFirstWitnesName(),
        savedCustomer.getFirstWitnesPhoneNumber(),
        savedCustomer.getSecondWitnesName(),
        savedCustomer.getSecondWitnesPhoneNumber()
    );
  }

  private void validateRequest(final CustomerRequestDto requestDto) {
    if (requestDto.getCustomerName() == null || requestDto.getCustomerName().isEmpty()) {
      badRequest("Invalid customer name");
    }

    if (requestDto.getPhoneNumber() == null || requestDto.getPhoneNumber().isEmpty()) {
      badRequest("Invalid phone number");
    }

    if (requestDto.getMonths() <= 0) {
      badRequest("Months should be valid");
    }

    if (requestDto.getMonthlyPaymentDate() <= 0) {
      badRequest("Monthly payment date should not be empty");
    }

    if (requestDto.getTotalAmount() <= 0) {
      badRequest("Total amount should be zero");
    }

    if (requestDto.getInterestRate() <= 0) {
      badRequest("Interest rate should be greater then 0");
    }
  }

  private void badRequest(final String message) {
    throw new BadRequestException(message);
  }

}
