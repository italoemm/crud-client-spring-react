package com.br.italo.usuarioService.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.br.italo.usuarioService.model.EmailEntity;
import com.br.italo.usuarioService.model.EnderecoEntity;
import com.br.italo.usuarioService.model.TelefoneEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class LoginDTO {


	@NotEmpty(message = "N\u00E3o pode ser vazio")
	private String login;
	
	@NotEmpty(message = "N\u00E3o pode ser vazio")
	private String senha;
	
	
	public LoginDTO() {
		// TODO Auto-generated constructor stub
	}


	public LoginDTO(@NotEmpty(message = "N\u00E3o pode ser vazio") String login,
			@NotEmpty(message = "N\u00E3o pode ser vazio") String senha) {
		super();
		this.login = login;
		this.senha = senha;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
