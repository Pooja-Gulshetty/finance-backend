package com.finance.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "finance")
public class FinanceEntity implements Serializable {

  @Id
  @Column(name = "finance_id")
  private String financeId;

  @Column(name = "name")
  private String name;

  public FinanceEntity() {
    // hibernate
  }

  public FinanceEntity(final String financeId, final String name) {
    this.financeId = financeId;
    this.name = name;
  }

  public String getFinanceId() {
    return financeId;
  }

  public String getName() {
    return name;
  }
}
