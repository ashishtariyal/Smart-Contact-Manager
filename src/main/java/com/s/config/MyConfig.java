package com.s.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class MyConfig {

	@Bean
	public UserDetailsService getuserDetailService() {

		return new UserDeatilsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationprovider() {

		DaoAuthenticationProvider dp = new DaoAuthenticationProvider();
		dp.setUserDetailsService(this.getuserDetailService());
		dp.setPasswordEncoder(passwordEncoder());

		return dp;

	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationprovider());
    }

	
	//configure method--->
	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
	    .requestMatchers("/admin/**").hasRole("ADMIN")
	    .requestMatchers("/user/**").hasRole("USER")
	    .requestMatchers("/**")
	    .permitAll() // Permit all requests starting with /
	    .and()
	    .csrf()
	    .disable()
	    .formLogin()
	    .loginPage("/signin")
	    .loginProcessingUrl("/dologin")
	    .defaultSuccessUrl("/user/index")
	    .failureUrl("/signin?error");

		
		return http.build();
		
	}
	
	
	
}
