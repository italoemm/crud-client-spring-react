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

public class ClienteDTO {

	private Long id;
	
	@NotEmpty(message = "N\u00E3o pode ser vazio")
	@Size(min = 3, max = 100, message = "Deve ser maior que 3 caracteres e menor que 100 caracteres")
	@Pattern (regexp = "^[\\w&.\\- ]+$", message = "Apenas letras, espacos e numeros ")
	private String nome;
	
	@Size(min = 1 , message = "Deve ter ao menos um registro")
	@Valid
	private Set<EmailDTO> emails =  new HashSet<EmailDTO>();
	
	@Size(min = 1 , message = "Deve ter ao menos um registro")
	@Valid
	private Set<TelefoneDTO> telefones = new HashSet<TelefoneDTO>();
	
	@NotEmpty(message = "N\u00E3o pode ser vazio")
	private String cidade;
	
	@NotEmpty(message = "N\u00E3o pode ser vazio")
	private String uf;
	
	@NotEmpty(message = "N\u00E3o pode ser vazio")
	private String bairro;
	
	@NotEmpty(message = "N\u00E3o pode ser vazio")
	private String logradouro;
	
	@NotEmpty(message = "N\u00E3o pode ser vazio")
	private String cep;
	
	private String complemento;

	@JsonIgnore
	private LocalDateTime created_datetime = LocalDateTime.now();
	
	@JsonIgnore
	private LocalDateTime updated_datetime = LocalDateTime.now();

	
	
	public ClienteDTO(Long id, String nome, Set<EmailDTO> emails, Set<TelefoneDTO> telefones, String cidade, String uf,
			String bairro, String logradouro, String cep, String complemento, LocalDateTime created_datetime,
			LocalDateTime updated_datetime) {
		super();
		this.id = id;
		this.nome = nome;
		this.emails = emails;
		this.telefones = telefones;
		this.cidade = cidade;
		this.uf = uf;
		this.bairro = bairro;
		this.logradouro = logradouro;
		this.cep = cep;
		this.complemento = complemento;
		this.created_datetime = created_datetime;
		this.updated_datetime = updated_datetime;
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

	public Set<EmailDTO> getEmails() {
		return emails;
	}

	public void setEmails(Set<EmailDTO> emails) {
		this.emails = emails;
	}

	public Set<TelefoneDTO> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<TelefoneDTO> telefones) {
		this.telefones = telefones;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
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
