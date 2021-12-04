package com.finance.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private long id;

  @Column(name = "finance_id_ref")
  private String financeId;

  @Column(name = "name")
  private String customerName;

  @Column(name = "email")
  private String email;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "expiry_date")
  private ZonedDateTime endDate;

  @Column(name = "lending_date")
  private ZonedDateTime lendingDate;

  @Column(name = "monthly_payment_day")
  private long monthlyPaymentDate;

  @Column(name = "total_amount")
  private long totalAmount;

  @Column(name = "remaining_amount")
  private long remainingAmount;

  @Column(name = "interest_rate")
  private float interestRate;

  @Column(name = "number_of_months")
  private int months;

  @Column(name = "per_month_payment")
  private long monthlyPayment;

  @Column(name = "first_witness_name")
  private String firstWitnesName;

  @Column(name = "first_witness_number")
  private String firstWitnesPhoneNumber;

  @Column(name = "second_witness_name")
  private String secondWitnesName;

  @Column(name = "second_witness_number")
  private String secondWitnesPhoneNumber;

  protected Customer() {
    // hibernate
  }

  public Customer(final String financeId,
                  final String customerName,
                  final String email,
                  final String phoneNumber,
                  final ZonedDateTime endDate,
                  final ZonedDateTime lendingDate,
                  final long monthlyPaymentDate,
                  final long totalAmount,
                  final long remainingAmount,
                  final float interestRate,
                  final int months,
                  final long monthlyPayment,
                  final String firstWitnesName,
                  final String firstWitnesPhoneNumber,
                  final String secondWitnesName,
                  final String secondWitnesPhoneNumber) {
    this.financeId = financeId;
    this.customerName = customerName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.endDate = endDate;
    this.lendingDate = lendingDate;
    this.monthlyPaymentDate = monthlyPaymentDate;
    this.totalAmount = totalAmount;
    this.remainingAmount = remainingAmount;
    this.interestRate = interestRate;
    this.months = months;
    this.monthlyPayment = monthlyPayment;
    this.firstWitnesName = firstWitnesName;
    this.firstWitnesPhoneNumber = firstWitnesPhoneNumber;
    this.secondWitnesName = secondWitnesName;
    this.secondWitnesPhoneNumber = secondWitnesPhoneNumber;
  }

  public long getId() {
    return id;
  }

  public String getFinanceId() {
    return financeId;
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

  public ZonedDateTime getEndDate() {
    return endDate;
  }

  public ZonedDateTime getLendingDate() {
    return lendingDate;
  }

  public long getMonthlyPaymentDate() {
    return monthlyPaymentDate;
  }

  public long getTotalAmount() {
    return totalAmount;
  }

  public long getRemainingAmount() {
    return remainingAmount;
  }

  public void setRemainingAmount(final long remainingAmount) {
    this.remainingAmount = remainingAmount;
  }

  public float getInterestRate() {
    return interestRate;
  }

  public int getMonths() {
    return months;
  }

  public long getMonthlyPayment() {
    return monthlyPayment;
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
