package com.example.demo;

import me.gosimple.nbvcxz.Nbvcxz;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, DelegatedAuthenticationEntryPoint entryPoint) throws Exception {
		return http
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/error").permitAll()
				.anyRequest().authenticated())
			.httpBasic(b -> b.authenticationEntryPoint(entryPoint))
			.exceptionHandling(e -> e.authenticationEntryPoint(entryPoint))
			.build();
	}

	@Bean
	@Profile("haveibeenpwned")
	public CompromisedPasswordChecker haveIBeenPwnedPasswordChecker() {
		return new HaveIBeenPwnedRestApiPasswordChecker();
	}

	@Bean
	@Profile("nbvcxz")
	public CompromisedPasswordChecker nbvcxzPasswordChecker() {
		return new NbvcxzPasswordChecker(new Nbvcxz());
	}

	@Bean
	@Profile("resource")
	public ResourcePasswordChecker resourcePasswordChecker() {
		return new ResourcePasswordChecker(new ClassPathResource("10-million-password-list-top-1000000.txt"));
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService users(PasswordEncoder passwordEncoder) {
		return new InMemoryUserDetailsManager(
			User.builder()
				.username("user")
				.password(passwordEncoder.encode("password"))
				.build(),
			User.builder()
				.username("user2")
				.password(passwordEncoder.encode("A-Password-That-Is-Not-Common-Or-Shared"))
				.build()
		);
	}
}
