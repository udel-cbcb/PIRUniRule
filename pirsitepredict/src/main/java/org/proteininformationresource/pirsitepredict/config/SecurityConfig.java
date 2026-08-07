package org.proteininformationresource.pirsitepredict.config;

import org.proteininformationresource.pirsitepredict.account.UserService;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
class SecurityConfig {

	@Bean
	public UserService userService() {
		return new UserService();
	}

	@Bean
	public TokenBasedRememberMeServices rememberMeServices() {
		return new TokenBasedRememberMeServices("remember-me-key", userService());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authz -> authz
						.requestMatchers("/", "/favicon.ico", "/resources/**", "/signup", "/signin",
								"/news", "/contactus", "/documentation/**", "/download/**", "/prediction/**",
								"/generalError", "/error").permitAll()
						.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/signin").permitAll()
						.failureUrl("/signin?error=1")
						.loginProcessingUrl("/authenticate"))
				.logout(logout -> logout.logoutUrl("/logout").permitAll()
						.logoutSuccessUrl("/signin?logout"))
				.rememberMe(remember -> remember.rememberMeServices(rememberMeServices()));
		return http.build();
	}
}
