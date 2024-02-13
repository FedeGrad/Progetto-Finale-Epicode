package it.progetto.energy.impl.configuration;

import it.progetto.energy.impl.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@Slf4j
public class WebSecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
	private final AuthEntryPointJwt unauthorizedHandler;

	@Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
	public PasswordEncoder passwordEncoder() {
		log.trace("passwordEncoder");
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

//	@Autowired
//	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//		log.trace("configure(AuthenticationManagerBuilder authenticationManagerBuilder)");
//		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
			throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsService)
				.passwordEncoder(bCryptPasswordEncoder)
				.and()
				.build();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
//				.authenticationManager()
				.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
						authorizationManagerRequestMatcherRegistry
								.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
								.requestMatchers("/admin/**").hasAnyRole("ADMIN")
								.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
								.requestMatchers("/login/**").permitAll()
								.anyRequest()
								.authenticated())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(httpSecuritySessionManagementConfigurer ->
						httpSecuritySessionManagementConfigurer
								.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

//	protected void configure(HttpSecurity http) throws Exception {
//		log.trace("configure(HttpSecurity http)");
//		http.cors()
//			.and()
//			.csrf()
//			.disable()
//			.exceptionHandling()
//			.authenticationEntryPoint(unauthorizedHandler)
//			.and()
//			.sessionManagement()
//			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			.and()
//			.authorizeRequests()
//			.antMatchers("/**")
//			.permitAll()
//			.anyRequest()
//			.authenticated();
//
//		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//	}

}
