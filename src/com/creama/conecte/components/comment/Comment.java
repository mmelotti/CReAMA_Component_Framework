package com.creama.conecte.components.comment;

import java.io.Serializable;
import java.util.Date;

public class Comment extends
com.gw.android.first_components.my_fragment.ComponentSimpleModel implements Serializable {
	private static final long serialVersionUID = 4664095570516480803L;
	private String nome, texto, foto;
	private int idIdeia, idUsuario;
	private Long id;
	private boolean andamento;
	private Date dataCriacao;

	public Long getId() {
		return id;
	}
	
	public int getIdIdeia() {
		return idIdeia;
	}

	public int getIdUsuario() {
		return idUsuario;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getUserPhotoUrl() {
		return foto;
	}
	
	public String getTexto() {
		return texto;
	}
	
	public boolean isAndamento() {
		return andamento;
	}
	
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public void setIdIdeia(int idIdeia) {
		this.idIdeia = idIdeia;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAndamento(boolean andamento) {
		this.andamento = andamento;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
}