package com.creama.ideatoolkit.components2.action;

import java.io.Serializable;
import java.util.Date;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

public class Action extends
		com.gw.android.first_components.my_fragment.ComponentSimpleModel
		implements Serializable {
	private static final long serialVersionUID = 5209706701349219565L;
	private int tipo;
	private int idIdeia;
	private int idComentario;
	private int idUsuario;
	private String nomeUsuario;
	private String titulo;
	private String texto;
	private Date dataHora;

	// NovoComentario(0), NovaIdeia(1), NovoAndamento(2);

	public SpannedString getTexto() {
		int color = 0xff428BCA;
		SpannedString str = new SpannedString("");
		SpannableString aux1, aux2, aux3;

		switch (tipo) {
		case 0: // Novo comentário
			aux1 = new SpannableString(nomeUsuario);
			aux1.setSpan(new StyleSpan(Typeface.BOLD), 0, aux1.length(), 0);
			aux1.setSpan(new ForegroundColorSpan(color), 0, aux1.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			aux2 = new SpannableString(titulo + ": ");
			aux2.setSpan(new StyleSpan(Typeface.BOLD), 0, aux2.length(), 0);
			aux2.setSpan(new ForegroundColorSpan(color), 0, aux2.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			aux3 = new SpannableString("\"" + texto + "...\"");
			aux3.setSpan(new StyleSpan(Typeface.ITALIC), 0, aux3.length(), 0);

			str = (SpannedString) TextUtils.concat(aux1, " comentou na ideia ",
					aux2, aux3);
			break;

		case 1: // Nova ideia
			aux1 = new SpannableString(nomeUsuario);
			aux1.setSpan(new StyleSpan(Typeface.BOLD), 0, aux1.length(), 0);
			aux1.setSpan(new ForegroundColorSpan(color), 0, aux1.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			aux2 = new SpannableString(titulo);
			aux2.setSpan(new StyleSpan(Typeface.BOLD), 0, aux2.length(), 0);
			aux2.setSpan(new ForegroundColorSpan(color), 0, aux2.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			str = (SpannedString) TextUtils.concat(aux1,
					" criou uma nova ideia: ", aux2);
			break;

		case 2: // Novo andamento
			aux1 = new SpannableString(nomeUsuario);
			aux1.setSpan(new StyleSpan(Typeface.BOLD), 0, aux1.length(), 0);
			aux1.setSpan(new ForegroundColorSpan(color), 0, aux1.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			aux2 = new SpannableString(titulo + ": ");
			aux2.setSpan(new StyleSpan(Typeface.BOLD), 0, aux2.length(), 0);
			aux2.setSpan(new ForegroundColorSpan(color), 0, aux2.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			aux3 = new SpannableString("\"" + texto + "...\"");
			aux3.setSpan(new StyleSpan(Typeface.ITALIC), 0, aux3.length(), 0);

			str = (SpannedString) TextUtils.concat(aux1,
					" marcou um novo andamento na ideia ", aux2, aux3);
			break;
		}

		return str;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getIdIdeia() {
		return idIdeia;
	}

	public void setIdIdeia(int idIdeia) {
		this.idIdeia = idIdeia;
	}

	public int getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(int idComentario) {
		this.idComentario = idComentario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}