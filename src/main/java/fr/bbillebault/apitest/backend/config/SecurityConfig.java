package fr.bbillebault.apitest.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private TokenAuthenticationEntryPoint tokenAuthenticationEntryPoint;
	
	@Autowired
	private UserDetailsService tokenUserDetailsService;
	
	@Autowired
	private TokenRequestFilter tokenRequestFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) throws Exception {
		return httpSecurity
			.getSharedObject(AuthenticationManagerBuilder.class)
			.userDetailsService(this.tokenUserDetailsService)
			.passwordEncoder(this.passwordEncoder())
			.and()
			.build();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
			.csrf()
				.disable()
			.authorizeHttpRequests(
				(httpRequest) -> httpRequest
					// GET /hello => allow for everyone
					.requestMatchers(HttpMethod.GET, "/hello")
						.permitAll()
						
					// POST /register => allow for everyone
					.requestMatchers(HttpMethod.POST, "/register")
						.permitAll()
				
					// POST /token => allow for everyone
					.requestMatchers(HttpMethod.POST, "/token")
						.permitAll()
						
					// GET /boat => allow for users
					.requestMatchers(HttpMethod.GET, "/boat")
						.hasRole("USER")
					
					// GET /boat/{id} => allow for users
					.requestMatchers(HttpMethod.GET, "/boat/{id}")
						.hasRole("USER")
					
					// POST /boat => allow for admins
					.requestMatchers(HttpMethod.POST, "/boat")
						.hasRole("ADMIN")
						
					// PUT /boat/{id} => allow for admins
					.requestMatchers(HttpMethod.PUT, "/boat/{id}")
						.hasRole("ADMIN")
						
					// DELETE /boat/{id} => allow for admins
					.requestMatchers(HttpMethod.DELETE, "/boat/{id}")
						.hasRole("ADMIN")
						
					// Deny all unconfigured requests
					.anyRequest().denyAll()
			)
			.exceptionHandling()
				.authenticationEntryPoint(this.tokenAuthenticationEntryPoint)
				.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
			.addFilterBefore(this.tokenRequestFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}
}
