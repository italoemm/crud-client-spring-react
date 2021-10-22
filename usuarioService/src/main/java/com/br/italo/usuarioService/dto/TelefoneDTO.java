package com.br.italo.usuarioService.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.br.italo.usuarioService.enums.TipoTelefone;
import com.br.italo.usuarioService.model.ClienteEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TelefoneDTO {

	private Long id;
	
	@NotEmpty(message = "N\u00E3o pode ser vazio")
	private String tipoTelefone;

	@NotEmpty(message = "N\u00E3o pode ser vazio")
	@Size(min = 10 , max = 11, message = "Telefone Invalido")
	private String numero;
		
	@JsonIgnore
	private LocalDateTime created_datetime;
	
	@JsonIgnore
	private LocalDateTime updated_datetime;

	public TelefoneDTO(Long id, String tipoTelefone, String numero, LocalDateTime created_datetime,
			LocalDateTime updated_datetime) {
		super();
		this.id = id;
		this.tipoTelefone = tipoTelefone;
		this.numero = numero;
		this.created_datetime = created_datetime;
		this.updated_datetime = updated_datetime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(String tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
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
