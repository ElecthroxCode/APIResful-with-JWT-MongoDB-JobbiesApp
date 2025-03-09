package com.jobbies.jobbies.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jobbies.jobbies.service.impl.UserDetailsServiceImpl;
import com.jobbies.jobbies.util.UtilSecurityJWT;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UtilSecurityJWT utilSecurityJWT; // nuetsro filtro configurado
	private AuthenticationConfiguration authenticationConfiguration;

	@Autowired
	public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, UtilSecurityJWT utilSecurityJWT) {
		this.authenticationConfiguration = authenticationConfiguration;
		this.utilSecurityJWT = utilSecurityJWT;
	}

	// 1 securityFilterChain
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity.csrf(csrf -> csrf.disable()) // desabilitanos esta proteccion ya que es una app web
				.cors(cors -> cors.configurationSource(this.corsConfigurationSource()))
				//.httpBasic(Customizer.withDefaults())// se usa en apps con login y no se usan token.
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // configuramos
																												// sin
																												// estado
				.authorizeHttpRequests(http -> {
					// endpoint publicos
					http.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll();// permite a todos
					http.requestMatchers(HttpMethod.GET, "/api/jobs-all/*/pg").authenticated(); // Solo usuarios autenticados
					// endpoints privado
					http.requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("USER");// los que tienen
																								// autirizacion con
																								// ROLE_USER
					http.requestMatchers(HttpMethod.PUT, "/api/users").hasAnyRole("USER");
					http.requestMatchers(HttpMethod.DELETE, "/api/users").hasAnyRole("USER");
					http.requestMatchers(HttpMethod.POST, "/api/users").hasAnyRole("USER");
																							// pueden acceder
					http.requestMatchers(HttpMethod.GET, "/api/users-register").permitAll();
					// http.requestMatchers(HttpMethod.GET,
					// "/users-register").hasAuthority("CREATE");//los que tienen autorización
					// CREATE
					http.anyRequest().denyAll(); // cualquier otro request no especificado denegar el acceso
				}).addFilterBefore(new JwtTokenValidator(utilSecurityJWT), BasicAuthenticationFilter.class) // agregamos
																											// nuestro
																											// filtro
																											// antes del
																											// fitro
																											// authentication
				.build();

	}

	// 2 getAuthenticationManager
	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return this.authenticationConfiguration.getAuthenticationManager();
	}

	// 3 AuthenticationProvider
	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsServiceImpl) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(this.passwordEnconder());
		// provider.setUserDetail
		provider.setUserDetailsService(userDetailsServiceImpl);
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEnconder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200",
				"https://redesigned-happiness-p5x57674vj4cwp5-4200.app.github.dev")); // Orígenes permitidos
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
		configuration.setAllowedHeaders(Arrays.asList("*")); // Cabeceras permitidas
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration); // Aplica a todas las rutas
		return source;
	}

}
