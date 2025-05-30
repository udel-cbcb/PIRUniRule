package org.proteininformationresource.pirsitepredict.config;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.proteininformationresource.pirsitepredict.account.UserService;
import org.springframework.context.annotation.*;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserService userService() {
		return new UserService();
	}

	@Bean
	public TokenBasedRememberMeServices rememberMeServices() {
		return new TokenBasedRememberMeServices("remember-me-key",
				userService());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

	// @Bean
	// public DefaultMethodSecurityExpressionHandler
	// webSecurityExpressionHandler() {
	// return new DefaultMethodSecurityExpressionHandler();
	// }
	//

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.eraseCredentials(true).userDetailsService(userService())
				.passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.disable()
				.authorizeRequests()
				.antMatchers("/", "/**", "/favicon.ico", "/resources/**",
						"/signup").permitAll().anyRequest().authenticated()
				.and().formLogin().loginPage("/signin").permitAll()
				.failureUrl("/signin?error=1")
				.loginProcessingUrl("/authenticate").and().logout()
				.logoutUrl("/logout").permitAll()
				.logoutSuccessUrl("/signin?logout").and().rememberMe()
				.rememberMeServices(rememberMeServices())
				.key("remember-me-key");
	}
}