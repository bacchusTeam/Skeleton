package sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()// csrf 사용 중지 (https://developer.mozilla.org/ko/docs/Web/Security/Same-origin_policy#Cross-origin_%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC_%EC%A0%91%EA%B7%BC)
        .headers().frameOptions().sameOrigin().and()//clickjacking 동일 사이트 에서는 사용하기(https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/X-Frame-Options)
        .authorizeRequests()
          .antMatchers("/", "/home", "/webjars/**", "/script/**", "/css/**").permitAll()
          .antMatchers("/admin/**").hasRole("ADMIN")
          .antMatchers("/user/**").hasRole("USER")
          .anyRequest().authenticated()
          .and()
        .formLogin()
          .loginPage("/login")
          .failureUrl("/login?error")
          .permitAll()
          .and()
        .logout()
          .permitAll();
  }

  /**
   * spring security 다양한 예제들 나중에 참조
   * https://www.programcreek.com/java-api-examples/index.php?api=org.springframework.security.provisioning.JdbcUserDetailsManager
   */
  @Bean
  public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) throws Exception {
    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager() {{
      setDataSource(dataSource);
      /*
      //spring 의 기본 table 과 다를 경우 직접 sql 작성해서 사용 가능
      setGroupAuthoritiesByUsernameQuery("");
      setUsersByUsernameQuery("");
      setAuthoritiesByUsernameQuery("");
      setCreateUserSql("");
      setChangePasswordSql("");
      setDeleteUserSql("");
      */
    }};

    /*
     //root 계정이 없을경우
     if(!jdbcUserDetailsManager.userExists("root")) {
       List<GrantedAuthority> authorities = new ArrayList<>();
       //ROLE_ADMIN 권한 추가
       authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
       //root:password 계정으로 테스트 계정 생성
       User userDetails = new User("root", encoder.encode("password"), authorities);
       jdbcUserDetailsManager.createUser(userDetails);
     }
     */
    return jdbcUserDetailsManager;
  }
}