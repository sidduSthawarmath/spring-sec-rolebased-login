package com.siddu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*@Configuration
@EnableWebSecurity*/
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("emp").password("emp123").roles("EMP");
		auth.inMemoryAuthentication().withUser("admin").password("admin123").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/test/**").permitAll()
		.antMatchers("/emp/**").access("hasRole('EMP') or hasRole('ADMIN')")
		.antMatchers("/admin/**").access("hasRole('ADMIN')")
		.and().formLogin().loginPage("/login")
		.usernameParameter("ssoId").passwordParameter("password")
        .and().csrf()
		.and().exceptionHandling().accessDeniedPage("/Access_Denied");

	}
}