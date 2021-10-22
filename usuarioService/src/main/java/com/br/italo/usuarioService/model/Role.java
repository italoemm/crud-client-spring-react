package com.br.italo.usuarioService.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;


@Entity
@Table(name="tbl_role")
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="role_id")
	    private Long id;

	    @Column(name="role_nome")
	    private String nome;

	    @Column(name="role_descricao")
	    private String descricao;
	    
	    public Role() {
			// TODO Auto-generated constructor stub
		}
	    
	    public Role(Long id, String nome, String descricao) {
			super();
			this.id = id;
			this.nome = nome;
			this.descricao = descricao;
		}

		public Long getId() {
	        return id;
	    }

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public void setId(Long id) {
			this.id = id;
		}

		@Override
		public String getAuthority() {
			// TODO Auto-generated method stub
			return this.nome;
		}

}
