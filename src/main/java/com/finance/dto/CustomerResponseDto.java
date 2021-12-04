package com.finance.dto;

import java.time.ZonedDateTime;

public class CustomerResponseDto {
  private final long id;
  private final String customerName;
  private final String email;
  private final String phoneNumber;
  private final ZonedDateTime lendingDate;
  private final ZonedDateTime endDate;
  private final long monthlyPaymentDate;
  private final long totalAmount;
  private final long perMonthPayment;
  private final long remainingAmount;
  private final float interestRate;
  private final int months;
  private final String firstWitnesName;
  private final String firstWitnesPhoneNumber;
  private final String secondWitnesName;
  private final String secondWitnesPhoneNumber;

  public CustomerResponseDto(final long id,
                             final String customerName,
                             final String email,
                             final String phoneNumber,
                             final ZonedDateTime lendingDate,
                             final ZonedDateTime endDate,
                             final long monthlyPaymentDate,
                             final long totalAmount,
                             final long perMonthPayment,
                             final long remainingAmount,
                             final float interestRate,
                             final int months,
                             final String firstWitnesName,
                             final String firstWitnesPhoneNumber,
                             final String secondWitnesName,
                             final String secondWitnesPhoneNumber) {
    this.id = id;
    this.customerName = customerName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.lendingDate = lendingDate;
    this.endDate = endDate;
    this.monthlyPaymentDate = monthlyPaymentDate;
    this.totalAmount = totalAmount;
    this.perMonthPayment = perMonthPayment;
    this.remainingAmount = remainingAmount;
    this.interestRate = interestRate;
    this.months = months;
    this.firstWitnesName = firstWitnesName;
    this.firstWitnesPhoneNumber = firstWitnesPhoneNumber;
    this.secondWitnesName = secondWitnesName;
    this.secondWitnesPhoneNumber = secondWitnesPhoneNumber;
  }

  public long getId() {
    return id;
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

  public ZonedDateTime getLendingDate() {
    return lendingDate;
  }

  public ZonedDateTime getEndDate() {
    return endDate;
  }

  public long getMonthlyPaymentDate() {
    return monthlyPaymentDate;
  }

  public long getTotalAmount() {
    return totalAmount;
  }

  public long getPerMonthPayment() {
    return perMonthPayment;
  }

  public long getRemainingAmount() {
    return remainingAmount;
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
