package com.scm.config;

import com.scm.services.impl.SecurityCustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  //spring security uses UserDetailService ie whenever we login it fetches the user and to fetch that uses it uses UserDetailService and this service has a method called loadUserByUsername to load the user
  // and then matches the password that we entered and the loaded user if password matches login is done

  @Autowired
  private SecurityCustomUserDetailService userDetailService;

  @Autowired
  private OAuthAuthenicationSuccessHandler handler;

  //configuration of authentication provider for spring security
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    //this daoAuthenticationProvider has all the methods to register our services

    //fetching userDetailService  object
    daoAuthenticationProvider.setUserDetailsService(userDetailService);
    //fetching  object of passwordEncoder
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }

  //very important configuration as this will configure and manage all the routes whihc user can see what page, what type of login form login oauth login etc
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    throws Exception {
    //configuration
    //urls configured which one stays public which one stays private
    //urls be given from specific to generalised just like in chaining of catch blocks in that the way exceptions are given
    httpSecurity.authorizeHttpRequests(authorize -> {
      // authorize.requestMatchers("/home", "/register", "/login").permitAll();     //making these page public
      authorize.requestMatchers("/user/**").authenticated();
      authorize.anyRequest().permitAll();
    });

    //form login default
    //if we wish to change anything regarding form login we will come here
    // httpSecurity.formLogin(Customizer.withDefaults());      // this is default setting by spring security with no customizations
    //customizing our own login page
    httpSecurity.formLogin(formLogin -> {
      formLogin
        .loginPage("/login") //login page is present at this url
        .loginProcessingUrl("/authenticate"); //submission will happen at this url ie all the processing(we can all the methods too)
      formLogin.successForwardUrl("/user/profile"); //after login the user will come to this page
      // formLogin.failureForwardUrl("/login?error=true");    //if error occured during login
      formLogin.usernameParameter("email"); //use email as username
      formLogin.passwordParameter("password");
      // we can also define success and failure handlers that will run the methods upon success or failure of authentication

    });

    httpSecurity.logout(logoutForm -> {
      logoutForm.logoutUrl("/logout");
    });

    //oauth configurations
    httpSecurity.oauth2Login(oauth -> {
      oauth.loginPage("/login");
      oauth.successHandler(handler);
    });

    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
