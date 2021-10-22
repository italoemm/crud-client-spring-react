package com.br.italo.usuarioService.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.italo.usuarioService.config.JwtProvider;
import com.br.italo.usuarioService.model.User;
import com.br.italo.usuarioService.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private AuthenticationManager authenticationManager;
	private JwtProvider jwtProvider;
	private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEnconder,JwtProvider jwtProvider) {
		super();
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.jwtProvider = jwtProvider;
		}
	
	public Optional<Authentication> auth(String username, String password) {
		Authentication auth;
		try {
			auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
			return Optional.of(auth);
		}catch (AuthenticationException e) {
			LOGGER.error("Crendenciais incorreta uusario: {}, senha: {}",username,password);
			System.out.println("Crendenciais incorreta uusario");
		}
		return  Optional.empty();
	}
	
	public Optional<String> signin(String username,String password) {
		System.out.println("Novo Usuario logou");
		LOGGER.info("Novo Usuario logou");
		Optional<User> user = userRepository.findUserByNome(username);
		if(user.isPresent()) {
			//validate the user and save it in Spring context
			Optional<Authentication> auth = auth(username, password);
			if(auth.isPresent()) {
				SecurityContextHolder.getContext().setAuthentication(auth.get());
				return Optional.of(jwtProvider.createToken(username, user.get().getRoles()));
			} else { 
				return Optional.empty();
			}
		}
		LOGGER.error("username: {}, Nao foi encontrado",username);
		return Optional.empty();
	}
}
