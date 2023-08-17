package com.projeto.model.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TAB_PRODUTO")

public class Produto {
	private int id;
	private String nome;
	private String categoria;
	private Integer quantidade;
	private Float preco;
	private Integer codigoBarras;
	private String marca;
	private String modelo;
	private Float peso;
	private String garantia;
	private Fornecedor fornecedor;
	
	
	public Produto(){
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return id == other.id;
	}

	public Produto(int id, String nome, String categoria, Integer quantidade, Float preco, Integer codigoBarras,
			String marca, String modelo, Float peso, String garantia) {
		super();	//chama o pai da classe
		this.id = id;
		this.nome = nome;
		this.categoria = categoria;
		this.quantidade = quantidade;
		this.preco = preco;
		this.codigoBarras = codigoBarras;
		this.marca = marca;
		this.modelo = modelo;
		this.peso = peso;
		this.garantia = garantia;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUTO_ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "PRODUTO_NOME", nullable = false,length = 80)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "PRODUTO_CATEGORIA", nullable = false,length = 50)
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	@Column(name = "PRODUTO_QUANTIDADE", nullable = false)
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Column(name = "PRODUTO_PRECO", nullable = false)
	public Float getPreco() {
		return preco;
	}
	public void setPreco(Float preco) {
		this.preco = preco;
	}
	
	@Column(name = "PRODUTO_CODIGO_DE_BARRAS", nullable = false)
	public Integer getCodigoBarras() {
		return codigoBarras;
	}
	public void setCodigoBarras(Integer codigoBarras) {
		this.codigoBarras = codigoBarras;
	}
	@Column(name = "PRODUTO_MARCA", nullable = false,length = 50)
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	@Column(name = "PRODUTO_MODELO", nullable = false,length = 50)
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	@Column(name = "PRODUTO_PESO", nullable = false)
	public Float getPeso() {
		return peso;
	}
	public void setPeso(Float peso) {
		this.peso = peso;
	}
	
	@Column(name = "PRODUTO_GARANTIA", nullable = false)
	public String getGarantia() {
		return garantia;
	}

	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", categoria=" + categoria + ", quantidade=" + quantidade
				+ ", preco=" + preco + ", codigoBarras=" + codigoBarras + ", marca=" + marca + ", modelo=" + modelo
				+ ", peso=" + peso + ", garantia=" + garantia + "]";
	}
	
	//EAGER = le a entidade e as sub entidades automaticamente (um de cada vez), é a pesquisa ansiosa (N+1)
	//LAZY = pesquisa preguicosa, diz pro hibernate que na hr que ler a entidade nao precisa ler as sub entidades
	@ManyToOne(fetch = FetchType.LAZY)		
	@JoinColumn(name = "FORNECEDOR_ID",nullable = true)		//passando a fk
	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

}
