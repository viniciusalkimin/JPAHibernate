package br.com.alura.loja.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "livros")
public class Livro extends Produto {

	@Column
	private String autor;
	@Column
	private Integer numeroDePagina;

	public Livro() {

	}

	public Livro(String nome, String descricao, Categoria categoria, String autor, Integer numeroDePagina) {
		super();
		this.setNome(nome);
		this.setDataCadastro(getDataCadastro());
		this.setDescricao(descricao);
		this.setCategoria(categoria);
		this.autor = autor;
		this.numeroDePagina = numeroDePagina;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Integer getNumeroDePagina() {
		return numeroDePagina;
	}

	public void setNumeroDePagina(Integer numeroDePagina) {
		this.numeroDePagina = numeroDePagina;
	}

}