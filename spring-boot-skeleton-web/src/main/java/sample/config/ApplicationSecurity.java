package sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
          .antMatchers("/h2-console/**").permitAll().anyRequest().authenticated()
            .and()
          .formLogin()
            .loginPage("/login")
            .failureUrl("/login?error")
            .permitAll()
            .and()
          .logout()
            .permitAll();
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
