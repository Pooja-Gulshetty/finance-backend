package com.finance.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequestDto {
  private final String userName;
  private final String password;

  @JsonCreator
  public LoginRequestDto(@JsonProperty("userName") final String userName,
                         @JsonProperty("password") final String password) {
    this.userName = userName;
    this.password = password;
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }
}
