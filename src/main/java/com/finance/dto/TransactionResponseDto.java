package com.finance.dto;

import java.time.ZonedDateTime;

public class TransactionResponseDto {
  private final long id;
  private final long returnedAmount;
  private final String returnedTo;
  private final ZonedDateTime returnedDate;

  public TransactionResponseDto(final long id,
                                final long returnedAmount,
                                final String returnedTo,
                                final ZonedDateTime returnedDate) {
    this.id = id;
    this.returnedAmount = returnedAmount;
    this.returnedTo = returnedTo;
    this.returnedDate = returnedDate;
  }

  public long getId() {
    return id;
  }

  public long getReturnedAmount() {
    return returnedAmount;
  }

  public String getReturnedTo() {
    return returnedTo;
  }

  public ZonedDateTime getReturnedDate() {
    return returnedDate;
  }
}
