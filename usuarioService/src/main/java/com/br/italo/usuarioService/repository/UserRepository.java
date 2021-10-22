package com.br.italo.usuarioService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.br.italo.usuarioService.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
		public Optional<User> findUserByNome(String nome);
}
