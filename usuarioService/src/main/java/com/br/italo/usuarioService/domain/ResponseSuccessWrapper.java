package com.br.italo.usuarioService.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseSuccessWrapper {
	
	private int status;
	private String timestamp;
	private Object dados;
	private Integer paginaAtual;
	private Integer totalItens;
	private Integer totalPaginas;
	
	public ResponseSuccessWrapper(HttpStatus status, Page<?> pageElement) {
		this.status = status.value();
		this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		this.dados = pageElement.getContent();
		this.paginaAtual = pageElement.getNumber();
		this.totalItens = Integer.parseInt(String.valueOf(pageElement.getTotalElements()));
		this.totalPaginas = pageElement.getTotalPages();

	}
	
	public ResponseSuccessWrapper(HttpStatus status, Object dados) {
		super();
		this.status = status.value();
		this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		this.dados = dados;
	}
	
	public ResponseSuccessWrapper() {
		// TODO Auto-generated constructor stub
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public Object getDados() {
		return dados;
	}
	public void setDados(Object dados) {
		this.dados = dados;
	}
	public Integer getPaginaAtual() {
		return paginaAtual;
	}
	public void setPaginaAtual(int paginaAtual) {
		this.paginaAtual = paginaAtual;
	}
	public Integer getTotalItens() {
		return totalItens;
	}
	public void setTotalItens(int totalItens) {
		this.totalItens = totalItens;
	}
	public Integer getTotalPaginas() {
		return totalPaginas;
	}
	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	
}
