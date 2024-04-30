package com.nighthawk.spring_portfolio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;


/*
* To enable HTTP Security in Spring
*/
@Configuration
@EnableWebSecurity  // Beans to enable basic Web security
public class SecurityConfig {

    @Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	private PersonDetailsService personDetailsService;

    // @Bean  // Sets up password encoding style
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(personDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
    // Provide security configuration
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http
				// disable csrf
				.csrf(csrf -> csrf
					.disable()
				)
				// list the requests/endpoints need to be authenticated
				.authorizeHttpRequests(auth -> auth
					.requestMatchers(HttpMethod.GET,"/login").permitAll()
					.requestMatchers(HttpMethod.POST,"/authenticate").permitAll()
					.requestMatchers(HttpMethod.POST,"/authenticateForm").permitAll()
				    .requestMatchers(HttpMethod.POST, "/api/person/**").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/person/**").authenticated()
					.requestMatchers(HttpMethod.PUT, "/api/person/**").authenticated()
					.requestMatchers(HttpMethod.DELETE, "/api/person/**").hasAuthority("ROLE_ADMIN")
					.requestMatchers("/mvc/person/search/**").authenticated()
					.requestMatchers("/mvc/person/create/**").authenticated()
					.requestMatchers("/mvc/person/read/**").authenticated()
					.requestMatchers("/mvc/person/update/**").authenticated()
					.requestMatchers( "/mvc/person/delete/**").hasAuthority("ROLE_ADMIN")
					.requestMatchers("/**").permitAll()
				)
				// support cors
				.cors(Customizer.withDefaults())
				.headers(headers -> headers
					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "true"))
					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-ExposedHeaders", "*", "Authorization"))
					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "Content-Type", "Authorization", "x-csrf-token"))
					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-MaxAge", "600"))
					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "POST", "GET", "OPTIONS", "HEAD"))
					//.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "https://nighthawkcoders.github.io", "http://localhost:4100", "http://127.0.0.1:4100"))
				)
				.formLogin(form -> form 
					.loginPage("/login")
					.defaultSuccessUrl("/mvc/person/read")
				)
				.logout(logout -> logout
					.deleteCookies("jwt")
					.logoutSuccessUrl("/")
				)
				// make sure we use stateless session; 
				// session won't be used to store user's state.
				.exceptionHandling(exceptions -> exceptions
					.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				)
				.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				)
				// Add a filter to validate the tokens with every request
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
			return http.build();
	}
}
