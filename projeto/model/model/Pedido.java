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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TAB_PEDIDO")

public class Pedido {
	private int id;
	private String status;
	private String quantidade;
	private Cliente cliente;

	public Pedido() {
	}

	@Id 		//da pra colocar em cima do metodo ou em cima do atributo msm
	@GeneratedValue(strategy = GenerationType .IDENTITY)	//quem gerencia a PK é o BD
	@Column(name = "PEDIDO_ID")		//associa o ID objeto com o campo na tabela
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "PEDIDO_STATUS", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "PEDIDO_QUANTIDADE", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public Pedido(int id, String status, String quantidade) {
		super();
		this.id = id;
		this.status = status;
		this.quantidade = quantidade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, quantidade, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return id == other.id && Objects.equals(quantidade, other.quantidade) && Objects.equals(status, other.status);
	}

	@ManyToOne(fetch = FetchType.LAZY)		
	@JoinColumn(name = "CLIENTE_ID",nullable = true)		//passando a fk
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


}
