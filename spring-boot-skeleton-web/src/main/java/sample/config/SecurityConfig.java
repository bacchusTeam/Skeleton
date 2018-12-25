package sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
          .antMatchers("/", "/home", "/webjars/**", "/script/**", "/css/**").permitAll().anyRequest().authenticated()
            .and()
          .formLogin()
            .loginPage("/login")
            .failureUrl("/login?error")
            .permitAll()
            .and()
          .logout()
            .permitAll();

    /**
     * csrf 사용 중지
     * https://developer.mozilla.org/ko/docs/Web/Security/Same-origin_policy#Cross-origin_%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC_%EC%A0%91%EA%B7%BC
     */
    http.csrf().disable();

    /**
     * clickjacking 동일 사이트 에서는 사용하기
     * https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/X-Frame-Options
     */
    http.headers().frameOptions().sameOrigin();
  }

  /**
   * spring security 다양한 예제들 나중에 참조
   * https://www.programcreek.com/java-api-examples/index.php?api=org.springframework.security.provisioning.JdbcUserDetailsManager
   */
  @Bean
  public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource, AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
    jdbcUserDetailsManager.setDataSource(dataSource);
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    authenticationManagerBuilder.userDetailsService(jdbcUserDetailsManager).passwordEncoder(encoder);

    /**
     * //root 계정이 없을경우
     *     if(!jdbcUserDetailsManager.userExists("root")) {
     *       List<GrantedAuthority> authorities = new ArrayList<>();
     *       //ROLE_ADMIN 권한 추가
     *       authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
     *       //root/qwe123 계정으로 테스트 계정 생성
     *       User userDetails = new User("root", encoder.encode("qwe123"), authorities);
     *       jdbcUserDetailsManager.createUser(userDetails);
     *     }
     */

    return jdbcUserDetailsManager;
  }

}