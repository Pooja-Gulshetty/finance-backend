package com.finance.dto;

public class FinanceDetailsResponseDto {
  private final long totalRemaining;
  private final long totalMonthly;

  public FinanceDetailsResponseDto(long totalRemaining, long totalMonthly) {
    this.totalRemaining = totalRemaining;
    this.totalMonthly = totalMonthly;
  }

  public long getTotalRemaining() {
    return totalRemaining;
  }

  public long getTotalMonthly() {
    return totalMonthly;
  }
}
