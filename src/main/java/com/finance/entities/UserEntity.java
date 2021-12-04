package com.finance.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "finance_users")
public class UserEntity {
  @Id
  @Column(name = "user_name")
  private String userName;

  @Column(name = "password")
  private String password;

  @Column(name = "finance_ref")
  private String financeRef;

  protected UserEntity() {

  }

  public UserEntity(final String userName, final String password, final String financeRef) {
    this.userName = userName;
    this.password = password;
    this.financeRef = financeRef;
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public String getFinanceRef() {
    return financeRef;
  }
}
