/**
 * 
 */
package com.fido.poc.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


/**
 *
 * @author Sumanth
 * 
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
		return new MvcRequestMatcher.Builder(introspector);
	}

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
		
		
		http.csrf().ignoringRequestMatchers(PathRequest.toH2Console());
	    http.headers().frameOptions().sameOrigin();
	    http.authorizeHttpRequests().requestMatchers(PathRequest.toH2Console()).permitAll();
		
	    http.requiresChannel().anyRequest().requiresSecure();


		http.authorizeHttpRequests()
				.requestMatchers(mvc.pattern("/**"), mvc.pattern("/signup"), mvc.pattern("/signup/start"),
						mvc.pattern("/signup/finish"), mvc.pattern("/signup/finish"),
						mvc.pattern("/webauthn/login/start"),
	                    mvc.pattern("/webauthn/login/finish"),
	                    mvc.pattern("/webauthn/login"),
	                    mvc.pattern("favicon.ico"))
				.permitAll().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.anyRequest().authenticated();

//		AuthenticationFilter authenticationFilter = new AuthenticationFilter(fidoAuthenticationManager,
//				new FidoAuthenticationConverter());
//		authenticationFilter.setRequestMatcher(new AntPathRequestMatcher("/fido/login"));
//		authenticationFilter.setSuccessHandler(new FidoLoginSuccessHandler());
//		authenticationFilter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
//		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}