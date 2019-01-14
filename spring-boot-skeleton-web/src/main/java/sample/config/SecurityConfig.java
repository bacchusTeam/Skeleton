package sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private DataSource dataSource;

  public SecurityConfig(@Qualifier("dataSource") DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf().disable()// csrf 사용 중지 (https://developer.mozilla.org/ko/docs/Web/Security/Same-origin_policy#Cross-origin_%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC_%EC%A0%91%EA%B7%BC)
        .headers().frameOptions().sameOrigin().and()//clickjacking 동일 사이트 에서는 사용하기(https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/X-Frame-Options)
        .authorizeRequests()
          .antMatchers("/admin/**").hasRole("ADMIN")
          .antMatchers("/user/**").hasRole("USER")
          .anyRequest().permitAll()
          .and()
        .formLogin()
//          .loginPage("/login")
          .failureUrl("/login?error")
          .permitAll()
          .and()
        .logout()
          .permitAll()
          .and()
        .sessionManagement()
          .maximumSessions(1) //동시 로그인 1명 만 가능
//            .maxSessionsPreventsLogin 하지 않으면 기존 로그인 세션을 만료
//            .maxSessionsPreventsLogin(false) //기존 로그인 세션을 만료 기본값
//            .maxSessionsPreventsLogin(true) //새로 로그인 하는것을 막음;
    ;
  }

  @Override
  public void configure(WebSecurity webSecurity) throws Exception {
    webSecurity.ignoring()
            .antMatchers("/webjars/**")
            .antMatchers("/resources/**")
            .antMatchers("/script/**")
            .antMatchers("/css/**")
            .antMatchers("/favicon.ico")
    ;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean () throws Exception{
    return super.authenticationManagerBean();
  }

  /**
   * spring security 다양한 예제들 나중에 참조
   * https://github.com/spring-projects/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-web-secure-jdbc/src/main/java/sample/web/secure/jdbc/SampleWebSecureJdbcApplication.java
   * https://www.programcreek.com/java-api-examples/index.php?api=org.springframework.security.provisioning.JdbcUserDetailsManager
   * https://www.javainuse.com/spring/boot_security_jdbc_authentication_program
   */
  @Bean
  public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception {
    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
    jdbcUserDetailsManager.setDataSource(dataSource);
    /* spring 의 기본 table 과 다를 경우 직접 sql 작성해서 사용 가능
    jdbcUserDetailsManager.setGroupAuthoritiesByUsernameQuery("");
    jdbcUserDetailsManager.setUsersByUsernameQuery("");
    jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("");
    jdbcUserDetailsManager.setCreateUserSql("");
    jdbcUserDetailsManager.setChangePasswordSql("");
    jdbcUserDetailsManager.setDeleteUserSql("");
    */

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