package com.finance.services;

import java.util.Optional;

import com.finance.entities.FinanceEntity;
import com.finance.repositories.FinanceRepository;
import org.springframework.stereotype.Service;

@Service
public class FinanceService {
  private final FinanceRepository financeRepository;

  public FinanceService(final FinanceRepository financeRepository) {
    this.financeRepository = financeRepository;
  }

  public Optional<FinanceEntity> getFinance(String financeId) {
    return financeRepository.getFinanceEntity(financeId);
  }
}
