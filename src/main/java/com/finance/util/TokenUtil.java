package com.finance.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.stream.Collectors.joining;

public class TokenUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(TokenUtil.class);

  private static final String KEY_ID = "finance_private_key";

  public static String generateToken(final String userName,
                                     final String financeId) {
    final JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject(userName)
        .issuer("https://finance.com/")
        .issueTime(Date.from(ZonedDateTime.now().toInstant()))
        .expirationTime(getExpirationTime())
        .claim("finance", financeId)
        .build();

    final String privateKeyString = readFileAndPreserveNewLines("certificates/RSA2048-privateKey.key");
    final PrivateKey key = toPrivateKey(privateKeyString);
    return sign(KEY_ID, key, claimsSet);
  }

  public static Date getExpirationTime() {
    return Date.from(ZonedDateTime.now().plusDays(1).toInstant());
  }

  private static String sign(final String keyId, final PrivateKey privateKey, final JWTClaimsSet claimsSet) {
    final JWSSigner signer = new RSASSASigner(privateKey);
    final JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(keyId).build();
    try {
      final SignedJWT signedJwt = new SignedJWT(jwsHeader, claimsSet);
      signedJwt.sign(signer);
      return signedJwt.serialize();
    } catch (JOSEException e) {
      throw new RuntimeException(e);
    }
  }

  private static String readFileAndPreserveNewLines(final String certificateFileName) {
    return new BufferedReader(
        new InputStreamReader(TokenUtil.class.getClassLoader().getResourceAsStream(certificateFileName))
    ).lines().collect(joining(System.getProperty("line.separator")));
  }

  private static PrivateKey toPrivateKey(final String privateKeyString) {
    try {
      final byte[] certificateBytes = Base64.getMimeDecoder().decode(privateKeyString
          .replace("-----BEGIN PRIVATE KEY-----\n", "")
          .replace("-----END PRIVATE KEY-----", ""));

      final PKCS8EncodedKeySpec spec =
          new PKCS8EncodedKeySpec(certificateBytes);
      final KeyFactory kf = KeyFactory.getInstance("RSA");
      return kf.generatePrivate(spec);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
  }

  public static String oneWayHashing(final String originalString) {
    try {
      final MessageDigest digest = MessageDigest.getInstance("SHA-256");
      final byte[] encodedHash = digest.digest(
          originalString.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(encodedHash);
    } catch (Exception exception) {
      throw new RuntimeException("Error while hashing " + exception.getMessage());
    }
  }


  public static Optional<PublicKey> toPublicKey(final String keyId, final String fileName) {
    final String certificateString = readFileAndPreserveNewLines(fileName);
    final String certificateCore = certificateString
        .replace("-----BEGIN CERTIFICATE-----", "")
        .replace("-----END CERTIFICATE-----", "");

    try {
      final byte[] certificateBytes = Base64.getMimeDecoder().decode(certificateCore);
      final CertificateFactory factory = CertificateFactory.getInstance("X.509");
      final X509Certificate x509Certificate =
          (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(certificateBytes));
      if (x509Certificate == null) {
        LOGGER.warn("Auth public key with id '{}' cannot be stored/updated! Could not generate certificate out of '{}'",
            keyId, certificateString);
        return Optional.empty();
      }
      final PublicKey publicKey = x509Certificate.getPublicKey();
      if (publicKey == null) {
        LOGGER.warn("Auth public key with id '{}' cannot be stored/updated! Public key not found in certificate: '{}'",
            keyId, certificateString);
        return Optional.empty();
      }
      return Optional.of(publicKey);
    } catch (IllegalArgumentException e) {
      LOGGER.warn("Auth public key with id '{}' cannot be stored/updated! Unable to base64 decode the certificate string: {}.",
          keyId, certificateCore, e);
    } catch (CertificateException e) {
      LOGGER.warn("Auth public key with id '{}' cannot be stored/updated! Unable to process the string to RSA certificate.",
          keyId, e);
    }
    return Optional.empty();
  }
}
