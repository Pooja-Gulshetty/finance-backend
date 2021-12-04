package com.finance.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerRequestDto {
  private final String customerName;
  private final String email;
  private final String phoneNumber;
  private final long monthlyPaymentDate;
  private final long totalAmount;
  private final float interestRate;
  private final int months;
  private final String firstWitnesName;
  private final String firstWitnesPhoneNumber;
  private final String secondWitnesName;
  private final String secondWitnesPhoneNumber;

  @JsonCreator
  public CustomerRequestDto(@JsonProperty("customerName") final String customerName,
                            @JsonProperty("email") final String email,
                            @JsonProperty("phoneNumber") final String phoneNumber,
                            @JsonProperty("monthlyPaymentDate") final int monthlyPaymentDate,
                            @JsonProperty("totalAmount") final long totalAmount,
                            @JsonProperty("interestRate") final float interestRate,
                            @JsonProperty("months") final int months,
                            @JsonProperty("firstWitnesName") final String firstWitnesName,
                            @JsonProperty("firstWitnesPhoneNumber") final String firstWitnesPhoneNumber,
                            @JsonProperty("secondWitnesName") final String secondWitnesName,
                            @JsonProperty("secondWitnesPhoneNumber") final String secondWitnesPhoneNumber) {
    this.customerName = customerName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.monthlyPaymentDate = monthlyPaymentDate;
    this.totalAmount = totalAmount;
    this.interestRate = interestRate;
    this.months = months;
    this.firstWitnesName = firstWitnesName;
    this.firstWitnesPhoneNumber = firstWitnesPhoneNumber;
    this.secondWitnesName = secondWitnesName;
    this.secondWitnesPhoneNumber = secondWitnesPhoneNumber;
  }

  public String getCustomerName() {
    return customerName;
  }

  public String getEmail() {
    return email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public long getMonthlyPaymentDate() {
    return monthlyPaymentDate;
  }

  public long getTotalAmount() {
    return totalAmount;
  }

  public float getInterestRate() {
    return interestRate;
  }

  public int getMonths() {
    return months;
  }

  public String getFirstWitnesName() {
    return firstWitnesName;
  }

  public String getFirstWitnesPhoneNumber() {
    return firstWitnesPhoneNumber;
  }

  public String getSecondWitnesName() {
    return secondWitnesName;
  }

  public String getSecondWitnesPhoneNumber() {
    return secondWitnesPhoneNumber;
  }
}
