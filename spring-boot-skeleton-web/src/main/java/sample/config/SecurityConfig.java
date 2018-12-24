package sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
          .antMatchers("/webjars/**", "/script/**", "/css/**").permitAll().anyRequest().authenticated()
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

  @Bean
  public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
    jdbcUserDetailsManager.setDataSource(dataSource);
    return jdbcUserDetailsManager;
  }

}
