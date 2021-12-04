package com.finance.controllers;

import java.util.Optional;
import java.util.Set;

import com.finance.dto.FinanceDetailsResponseDto;
import com.finance.entities.Customer;
import com.finance.entities.FinanceEntity;
import com.finance.exception.NotFoundException;
import com.finance.services.CustomerService;
import com.finance.services.FinanceService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FinanceDataController {

  private final FinanceService financeService;
  private final CustomerService customerService;

  public FinanceDataController(FinanceService financeService, CustomerService customerService) {
    this.financeService = financeService;
    this.customerService = customerService;
  }

  @RequestMapping(
      path = "{finance_id}/details",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<FinanceDetailsResponseDto> getFinanceDetails(@PathVariable("finance_id") final String financeId) {
    final Optional<FinanceEntity> finance = financeService.getFinance(financeId);
    if (!finance.isPresent()) {
      throw new NotFoundException("Finance does not exists.");
    }

    Set<Customer> customers = customerService.getCustomers(financeId);

    FinanceDetailsResponseDto financeDetailsResponse = getFinanceDetailsResponse(customers);
    return ResponseEntity.ok(financeDetailsResponse);
  }

  private FinanceDetailsResponseDto getFinanceDetailsResponse(Set<Customer> customers) {
    long remaining = 0;
    long monthly = 0;
    for (Customer customer : customers) {
      remaining += customer.getRemainingAmount();
      monthly += customer.getMonthlyPayment();
    }

    return new FinanceDetailsResponseDto(remaining, monthly);
  }
}
