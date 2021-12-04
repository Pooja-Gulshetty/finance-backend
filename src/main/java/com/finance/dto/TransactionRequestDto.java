package com.finance.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionRequestDto {
  private final long returnedAmount;
  private final String returnedTo;

  @JsonCreator
  public TransactionRequestDto(@JsonProperty("returnedAmount") final long returnedAmount,
                               @JsonProperty("returnedTo") final String returnedTo) {
    this.returnedAmount = returnedAmount;
    this.returnedTo = returnedTo;
  }

  public long getReturnedAmount() {
    return returnedAmount;
  }

  public String getReturnedTo() {
    return returnedTo;
  }
}
