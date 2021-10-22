package com.br.italo.usuarioService.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tbl_usuario")
public class User {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "usr_id")
	    private Long id;

	    @Column(name = "usr_nome")
	    private String nome;

	    @Column(name = "usr_senha")
	    @JsonIgnore
	    private String senha;

	    /**
	     * Default Constructor.
	     */
	    protected User() {
	    }
	    
	    public User(String nome, String senha, Role role) {
	        this.nome = nome;
	        this.senha = senha;
	        this.roles = Arrays.asList(role);
	    }

	    @ManyToMany(fetch=FetchType.EAGER)
	    @JoinTable(name="tbl_user_role", joinColumns = @JoinColumn(name="usrol_usr_id", referencedColumnName = "usr_id"),
	    							inverseJoinColumns = @JoinColumn(name="usrol_role_id", referencedColumnName = "role_id"))
	    private List<Role> roles;

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

		public String getSenha() {
			return senha;
		}

		public void setSenha(String senha) {
			this.senha = senha;
		}

		public List<Role> getRoles() {
			return roles;
		}

		public void setRoles(List<Role> roles) {
			this.roles = roles;
		}
		
	  
}
