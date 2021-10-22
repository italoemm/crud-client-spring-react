package com.br.italo.usuarioService.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tbl_email")
public class EmailEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "em_id")
	private Long id;
	
	@Column(name = "em_email")
	private String email;

	@Column
	@JsonIgnore
	private LocalDateTime created_datetime;
	
	@Column 
	@JsonIgnore
	private LocalDateTime updated_datetime;
	
	public EmailEntity() {
		// TODO Auto-generated constructor stub
	}

	public EmailEntity(Long id, String email, LocalDateTime created_datetime, LocalDateTime updated_datetime) {
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


	@Override
	public String toString() {
		return "EmailEntity [id=" + id + ", email=" + email + ", created_datetime=" + created_datetime
				+ ", updated_datetime=" + updated_datetime + "]";
	}
	
	
}
