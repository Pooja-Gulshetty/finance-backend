package com.finance.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import com.finance.dto.CustomerRequestDto;
import com.finance.entities.Customer;
import com.finance.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository repository;

  @Inject
  public CustomerService(final CustomerRepository repository) {
    this.repository = repository;
  }

  public Optional<Customer> getCustomer(final String financeId,
                                        final String customerName) {

    return repository.getCustomer(financeId, customerName);
  }

  public Customer createCustomer(final String financeId, final CustomerRequestDto requestDto) {
    final Customer customer = mapToCustomer(financeId, requestDto);
    return repository.save(customer);
  }

  private Customer mapToCustomer(final String financeId, final CustomerRequestDto requestDto) {

    final long monthlyPayment = requestDto.getTotalAmount() / requestDto.getMonths();
    return new Customer(
        financeId,
        requestDto.getCustomerName(),
        requestDto.getEmail(),
        requestDto.getPhoneNumber(),
        ZonedDateTime.now(ZoneId.of("UTC")).plusMonths(requestDto.getMonths()),
        ZonedDateTime.now(ZoneId.of("UTC")),
        requestDto.getMonthlyPaymentDate(),
        requestDto.getTotalAmount(),
        requestDto.getTotalAmount(),
        requestDto.getInterestRate(),
        requestDto.getMonths(),
        monthlyPayment,
        requestDto.getFirstWitnesName(),
        requestDto.getFirstWitnesPhoneNumber(),
        requestDto.getSecondWitnesName(),
        requestDto.getSecondWitnesPhoneNumber()
    );
  }

  public Set<Customer> getCustomers(final String financeId) {
    return repository.getCustomers(financeId);
  }
}
