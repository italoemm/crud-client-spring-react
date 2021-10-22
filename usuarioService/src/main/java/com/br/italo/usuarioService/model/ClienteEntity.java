package com.br.italo.usuarioService.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "tbl_cliente")
public class ClienteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cli_id")
	private Long id;
	
	@Column(name = "cli_nome", nullable = false)
	private String nome;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JoinColumn(name="em_cli_id", referencedColumnName = "cli_id")
	private Set<EmailEntity> emails =  new HashSet<EmailEntity>();
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JoinColumn(name="tel_cli_id", referencedColumnName = "cli_id")
	private Set<TelefoneEntity> telefones = new HashSet<TelefoneEntity>();
	
	@Column
	@JsonIgnore
	private LocalDateTime created_datetime;
	
	@Column 
	@JsonIgnore
	private LocalDateTime updated_datetime;

	@Embedded
	@AttributeOverrides({
		  @AttributeOverride( name = "cidade", column = @Column(name = "cli_cidade")),
		  @AttributeOverride( name = "uf", column = @Column(name = "cli_uf")),
		  @AttributeOverride( name = "bairro", column = @Column(name = "cli_bairro")),
		  @AttributeOverride( name = "logradouro", column = @Column(name = "cli_logradouro")),
		  @AttributeOverride( name = "cep", column = @Column(name = "cli_cep")),
		  @AttributeOverride( name = "complemento", column = @Column(name = "cli_complemento"))
		})
	private EnderecoEntity endereco;

	
	
	
	public ClienteEntity() {
		super();
	}

	public ClienteEntity(Long id, String nome, Set<EmailEntity> emails, Set<TelefoneEntity> telefones,
			LocalDateTime created_datetime, LocalDateTime updated_datetime, EnderecoEntity endereco) {
		super();
		this.id = id;
		this.nome = nome;
		this.emails = emails;
		this.telefones = telefones;
		this.created_datetime = created_datetime;
		this.updated_datetime = updated_datetime;
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<EmailEntity> getEmails() {
		return emails;
	}

	public void setEmails(Set<EmailEntity> emails) {
		this.emails = emails;
	}

	public Set<TelefoneEntity> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<TelefoneEntity> telefones) {
		this.telefones = telefones;
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

	public EnderecoEntity getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoEntity endereco) {
		this.endereco = endereco;
	}
	
	
	 
	
	
}
