package sample.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * https://java.ihoney.pe.kr/498
 */
@Slf4j
public class PasswordEncoderTest {

  private PasswordEncoder passwordEncoder;

  @Before
  public void setUp() throws Exception {
    passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Test
  public void encode() {
    String password = "password";

    System.out.println("BCrypt 암호화: "+passwordEncoder.encode(password));
    System.out.println("BCrypt 비교: "+passwordEncoder.matches(password, passwordEncoder.encode(password)));
  }
}
