package com.br.italo.usuarioService.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.br.italo.usuarioService.enums.TipoTelefone;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tbl_telefone")
public class TelefoneEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tel_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "tel_tipo")
	private TipoTelefone tipoTelefone;

	@Column(name = "tel_numero")
	private String numero;
	
	//@ManyToOne
	//@JoinColumn(name="em_cli_id", referencedColumnName = "cli_id")
	//private ClienteEntity cliente;
	
	@Column
	@JsonIgnore
	private LocalDateTime created_datetime;
	
	@Column 
	@JsonIgnore
	private LocalDateTime updated_datetime;
	
	public TelefoneEntity() {
		// TODO Auto-generated constructor stub
	}

	public TelefoneEntity(Long id, TipoTelefone tipoTelefone, String numero) {
		super();
		this.id = id;
		this.tipoTelefone = tipoTelefone;
		this.numero = numero;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoTelefone getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(TipoTelefone tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	
}
