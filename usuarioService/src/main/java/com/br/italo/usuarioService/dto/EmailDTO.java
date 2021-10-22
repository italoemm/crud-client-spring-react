package com.br.italo.usuarioService.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EmailDTO {

	private Long id;

	@NotEmpty(message = "N\u00E3o pode ser vazio")
	@Email
	private String email;

	@JsonIgnore
	private LocalDateTime created_datetime = LocalDateTime.now();

	@JsonIgnore
	private LocalDateTime updated_datetime = LocalDateTime.now();
	
	public EmailDTO(Long id, String email, LocalDateTime created_datetime, LocalDateTime updated_datetime) {
		super();
		this.id = id;
		this.email = email;
		this.created_datetime = created_datetime;
		this.updated_datetime = updated_datetime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getCreated_datetime() {
		return created_datetime;
	}

	public void setCreated_datetime(LocalDateTime created_datetime) {
		this.created_datetime = created_datetime;
	}

	public LocalDateTime getUpdated_datetime() {
		return updated_datetime;
	}

	public void setUpdated_datetime(LocalDateTime updated_datetime) {
		this.updated_datetime = updated_datetime;
	}
	
	
	

}
