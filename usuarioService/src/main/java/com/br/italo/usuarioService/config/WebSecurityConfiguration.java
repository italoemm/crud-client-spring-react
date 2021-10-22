package com.br.italo.usuarioService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	AuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*http.
	    authorizeRequests().
	    anyRequest().
	    permitAll();
		
		 	http.csrf().disable();
		    http.headers().frameOptions().disable();
		*/
		// DESCOMENTA ESSE TRECHO PARA LIBERAR A TODAS AS REQUISICOES
		
		http.authorizeRequests()
			.antMatchers("/api/v1/user/**").permitAll()
			.antMatchers("/h2/**").permitAll()
			.anyRequest().authenticated();
	

        http.csrf().disable();
        http.headers().frameOptions().disable();
        
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);	
        http
        .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
        .addFilterBefore(new JwtTokenFilter(userDetailsServiceImpl), UsernamePasswordAuthenticationFilter.class);
	
        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
	
	}

	@Bean
	public AuthenticationEntryPoint jwtAuthenticationEntryPoint() {
		return (req, res, auth) -> {
			res.sendError(HttpStatus.FORBIDDEN.value(),"Time out - Efetue Login Novamente");
		};
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		//web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
		super.configure(web);
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
	
}
