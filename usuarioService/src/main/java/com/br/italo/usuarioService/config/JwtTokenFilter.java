package com.br.italo.usuarioService.config;

import java.io.IOException;
import java.security.Security;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

public class JwtTokenFilter extends GenericFilterBean{
	 private static final String BEARER = "Bearer";
	 private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);
	 UserDetailsServiceImpl userDetailsServiceImpl;
	 
	public JwtTokenFilter(UserDetailsServiceImpl userDetailsServiceImpl) {
		super();
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		String headerValue = ((HttpServletRequest)req).getHeader("Authorization");
		getBearerToken(headerValue).ifPresent(token -> {

			Optional<UserDetails> user = userDetailsServiceImpl.LoadUsernameByToken(token);
				if(user.isPresent()) {
					 UserDetails userDetails = user.get();
					 SecurityContextHolder.getContext().setAuthentication(
		                        new PreAuthenticatedAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
				}
	
		});
		
		chain.doFilter(req, res);	
	}

	private Optional<String> getBearerToken(String headerVal) {
        if (headerVal != null && headerVal.startsWith(BEARER)) {
            return Optional.of(headerVal.replace(BEARER, "").trim());
        }
        return Optional.empty();
    }
}
