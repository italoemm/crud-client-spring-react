package com.br.italo.usuarioService.controller;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.br.italo.usuarioService.aop.ResourceNotFoundException;
import com.br.italo.usuarioService.domain.ResponseSuccessWrapper;
import com.br.italo.usuarioService.dto.LoginDTO;
import com.br.italo.usuarioService.model.User;
import com.br.italo.usuarioService.repository.UserRepository;
import com.br.italo.usuarioService.service.UserService;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1")
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(path="/user/{username}",method = RequestMethod.GET)
	public ResponseEntity<?>  testarApi(@PathVariable String username) {
		ResponseSuccessWrapper resp =  new ResponseSuccessWrapper(HttpStatus.OK, userRepository.findUserByNome(username).orElseThrow(() -> new ResourceNotFoundException( username)));

	return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(path="/user/autentica",method = RequestMethod.POST)
	public ResponseEntity<?> signin(@RequestBody @Valid LoginDTO loginDto) {
		Map<String, Object> map = new HashMap<>();
		map.put("token", "Bearer " + userService.signin(loginDto.getLogin(), loginDto.getSenha()).orElseThrow(() ->  new ResourceNotFoundException( loginDto.getLogin())));
		map.put("usuario", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		ResponseSuccessWrapper resp =  new ResponseSuccessWrapper(HttpStatus.OK, map);
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	/*@ExceptionHandler(HttpServerErrorException.class)
	public void HttpServerErrorException(HttpServerErrorException ex, HttpServletResponse res) throws IOException {
		res.sendError(ex.getStatusCode().value(),ex.getMessage());
	}*/
	
}
