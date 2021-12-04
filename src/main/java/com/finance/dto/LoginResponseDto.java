package com.finance.dto;

public class LoginResponseDto {
  private final String token;
  private final String userName;
  private final long expirationTime;
  private final String financeName;
  private final String financeId;

  public LoginResponseDto(final String token,
                          final String userName,
                          final long expirationTime,
                          final String financeName,
                          final String financeId) {
    this.token = token;
    this.userName = userName;
    this.expirationTime = expirationTime;
    this.financeName = financeName;
    this.financeId = financeId;
  }

  public String getToken() {
    return token;
  }

  public String getUserName() {
    return userName;
  }

  public long getExpirationTime() {
    return expirationTime;
  }

  public String getFinanceName() {
    return financeName;
  }

  public String getFinanceId() {
    return financeId;
  }
}
