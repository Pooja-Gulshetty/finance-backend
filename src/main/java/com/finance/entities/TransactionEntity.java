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
@Table(name = "transaction")
public class TransactionEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private long id;

  @Column(name = "finance_id_ref")
  private String financeIdRef;

  @Column(name = "customer_id_ref")
  private long customerIdRef;

  @Column(name = "returned_amount")
  private long returnedAmount;

  @Column(name = "returned_date")
  private ZonedDateTime returnedDate;

  @Column(name = "returned_to")
  private String returnedTo;

  public TransactionEntity() {
    // hibernate
  }

  public TransactionEntity(final String financeIdRef,
                           final long customerIdRef,
                           final long returnedAmount,
                           final ZonedDateTime returnedDate,
                           final String returnedTo) {
    this.financeIdRef = financeIdRef;
    this.customerIdRef = customerIdRef;
    this.returnedAmount = returnedAmount;
    this.returnedDate = returnedDate;
    this.returnedTo = returnedTo;
  }

  public long getId() {
    return id;
  }

  public String getFinanceIdRef() {
    return financeIdRef;
  }

  public long getCustomerIdRef() {
    return customerIdRef;
  }

  public long getReturnedAmount() {
    return returnedAmount;
  }

  public ZonedDateTime getReturnedDate() {
    return returnedDate;
  }

  public String getReturnedTo() {
    return returnedTo;
  }
}
