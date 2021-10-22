package com.br.italo.usuarioService.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Embeddable
public class EnderecoEntity {

	@Column(name = "cidade")
	private String cidade;
	
	@Column(name = "uf")
	private String uf;
	
	@Column(name = "bairro")
	private String bairro;
	
	@Column(name = "logradouro")
	private String logradouro;
	
	@Column(name = "cep")
	private String cep;
	
	@Column(name = "complemento")
	private String complemento;
	
	public EnderecoEntity() {
		// TODO Auto-generated constructor stub
	}

	public EnderecoEntity(String cidade, String uf, String bairro, String logradouro, String cep) {
		super();
		this.cidade = cidade;
		this.uf = uf;
		this.bairro = bairro;
		this.logradouro = logradouro;
		this.cep = cep;
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

	@Override
	public String toString() {
		return "Endereco [cidade=" + cidade + ", uf=" + uf + ", bairro=" + bairro + ", logradouro=" + logradouro
				+ ", cep=" + cep + "]";
	}
	
	

}
