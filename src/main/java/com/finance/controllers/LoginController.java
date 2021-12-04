package com.finance.controllers;

import javax.inject.Inject;

import com.finance.dto.LoginRequestDto;
import com.finance.dto.LoginResponseDto;
import com.finance.services.LoginService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  private final LoginService loginService;

  @Inject
  public LoginController(final LoginService loginService) {
    this.loginService = loginService;
  }

  @RequestMapping(
      path = "/login",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
    final LoginResponseDto loginResponseDto = loginService.login(requestDto);
    return ResponseEntity.ok(loginResponseDto);
  }
}
