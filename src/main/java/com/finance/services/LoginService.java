package com.finance.services;

import java.util.Optional;

import javax.inject.Inject;

import com.finance.dto.LoginRequestDto;
import com.finance.dto.LoginResponseDto;
import com.finance.entities.FinanceEntity;
import com.finance.entities.UserEntity;
import com.finance.exception.NotFoundException;
import com.finance.repositories.UserRepository;
import com.finance.util.TokenUtil;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final UserRepository userRepository;
  private final FinanceService financeService;

  @Inject
  public LoginService(final UserRepository userRepository,
                      final FinanceService financeService) {
    this.userRepository = userRepository;
    this.financeService = financeService;
  }

  public LoginResponseDto login(final LoginRequestDto requestDto) {
    final Optional<UserEntity> userEntity = this.userRepository.getUser(requestDto.getUserName());
    if (!userEntity.isPresent()) {
      throw new NotFoundException("User with name " + requestDto.getUserName() + " does not exists");
    }

    final UserEntity user = userEntity.get();

    final String hashedPassword = TokenUtil.oneWayHashing(requestDto.getPassword());
    if (!hashedPassword.equals(user.getPassword())) {
      throw new NotFoundException("Invalid password");
    }

    final FinanceEntity finance = this.financeService.getFinance(user.getFinanceRef()).get();
    final String token = TokenUtil.generateToken(requestDto.getUserName(), finance.getFinanceId());
    return new LoginResponseDto(
        token,
        user.getUserName(),
        TokenUtil.getExpirationTime().toInstant().toEpochMilli(),
        finance.getName(),
        finance.getFinanceId()
    );
  }
}
