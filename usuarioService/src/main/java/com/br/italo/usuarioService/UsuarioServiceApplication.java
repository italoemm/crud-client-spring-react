package com.br.italo.usuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.br.italo.usuarioService.model.Role;
import com.br.italo.usuarioService.model.User;
import com.br.italo.usuarioService.repository.RoleRepository;
import com.br.italo.usuarioService.repository.UserRepository;

@SpringBootApplication
public class UsuarioServiceApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(UsuarioServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		if(!userRepository.findUserByNome("admin").isPresent()) {
			Role role = new Role(1L,"ROLE_ADMIN","Administrador");
			Role role2 = new Role(2L,"ROLE_COMUM","Administrador");
			roleRepository.save(role);
			roleRepository.save(role2);
	
			User admin = new User("admin", "$2a$12$gC.LZo43msC7pi9op6LynuNYf7yc5YKPaPNb4UbI7pFg0UuiNlTxa", role);
			User comum = new User("comum", "$2a$12$gC.LZo43msC7pi9op6LynuNYf7yc5YKPaPNb4UbI7pFg0UuiNlTxa", role2);
	
			userRepository.save(admin);
			userRepository.save(comum);
		}
	}

}
