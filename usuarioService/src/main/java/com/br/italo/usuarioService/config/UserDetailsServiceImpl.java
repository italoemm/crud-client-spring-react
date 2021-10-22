package com.br.italo.usuarioService.config;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.br.italo.usuarioService.model.User;
import com.br.italo.usuarioService.repository.UserRepository;

import static org.springframework.security.core.userdetails.User.withUsername;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findUserByNome(username);
		return withUsername(user.get().getNome())
				.password(user.get().getSenha())
				.authorities(user.get().getRoles())
				.accountExpired(false)
				.credentialsExpired(false)
				.disabled(false)
				.build();
		}
	
	public Optional<UserDetails> LoadUsernameByToken(String token) {
		if(jwtProvider.isValidToken(token)) {
			return Optional.of(
					withUsername(jwtProvider.getUsername(token))
					.authorities(jwtProvider.getRoles(token))
					.password("")
					.accountExpired(false)
					.credentialsExpired(false)
					.disabled(false)
					.build());
		}
		return Optional.empty();
	}
}
