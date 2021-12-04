package com.finance.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

  @RequestMapping(
      method = RequestMethod.GET,
      path = "/health/alive"
  )
  public ResponseEntity<Void> isAlive() {
    return ResponseEntity.ok().build();
  }

}
