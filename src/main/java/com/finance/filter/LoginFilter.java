package com.finance.filter;

import java.io.IOException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.finance.util.TokenUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class LoginFilter extends OncePerRequestFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoginFilter.class);

  private Optional<PublicKey> publicKey = Optional.empty();

  @Override
  protected void doFilterInternal(final HttpServletRequest httpServletRequest,
                                  final HttpServletResponse httpServletResponse,
                                  final FilterChain filterChain) throws ServletException, IOException {
    final String requestURI = httpServletRequest.getRequestURI();
    if (requestURI.endsWith("/login")) {
      filterChain.doFilter(httpServletRequest, httpServletResponse);
      return;
    }

    final Optional<String> financeIdFromToken = verifyToken(httpServletRequest);
    final String financeIdFromPath = extractFinanceIdFromPath(requestURI);
    if (!financeIdFromToken.isPresent() || !financeIdFromToken.get().equals(financeIdFromPath)) {
      httpServletResponse.setStatus(403);
      if (financeIdFromToken.isPresent()) {
        LOGGER.info("Finance from the path is:[{}] and Finance from the token is:[{}]",
            financeIdFromPath, financeIdFromToken.get());
        httpServletResponse.getWriter().write("FinanceId in token is different then FinanceId in Path.");
      } else {
        LOGGER.info("Finance does not present in the token because invalid token.");
      }
      return;
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

  private Optional<String> verifyToken(final HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    if (token == null) {
      LOGGER.info("Token does not present in the header.");
      return Optional.empty();
    }

    try {
      final JWT jwt = JWTParser.parse(token);

      if (!publicKey.isPresent()) {
        publicKey = TokenUtil.toPublicKey("finance-private-key", "certificates/RSA2048-public.crt");
      }

      final JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey.get());
      if (!((SignedJWT) jwt).verify(verifier)) {
        LOGGER.info("Verification of the token failed.");
        return Optional.empty();
      }

      return Optional.ofNullable(jwt.getJWTClaimsSet().getStringClaim("finance"));
    } catch (ParseException | JOSEException e) {
      LOGGER.warn("Invalid token.", e);
      return Optional.empty();
    }


  }


  public static String extractFinanceIdFromPath(final String url) throws IllegalStateException {
    final String withoutFinance = url.substring("finance/".length() + 1);
    return withoutFinance.substring(0, withoutFinance.indexOf("/"));
  }
}
