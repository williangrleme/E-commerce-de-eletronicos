package com.projeto.model.model;
import java.util.*;
import javax.persistence.*;

import com.projeto.model.service.FornecedorService;


@Entity	 //diz que isso tudo é uma entidade
@Table(name = "TAB_FORNECEDOR") // se nao colocar o nome aqui ele pega o nome que vc coloca na classe 

public class Fornecedor {

	private int id;
	private String nome;
	private String cpf;
	private String cnpj;
	private String email;
	private String telefone;
	
	private List<Produto> listaProdutos;
	private Distribuidora distribuidora;
	
	public Fornecedor() {

	} 
	
	
	@Override
	public int hashCode() {		//comparacao em tempo de execucao pra ver se o objeto que to manipulando no momento sofreu alguma mudanca
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	} 

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fornecedor other = (Fornecedor) obj;
		if (id != other.id)
			return false;
		return true;
	}



	@Id 		//da pra colocar em cima do metodo ou em cima do atributo msm
	@GeneratedValue(strategy = GenerationType .IDENTITY)	//quem gerencia a PK é o BD
	@Column(name = "FORNECEDOR_ID")		//associa o ID objeto com o campo na tabela
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "FORNECEDOR_NOME", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
		
	@Column(name = "FORNECEDOR_CPF", length = 80, nullable = true)
	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Column(name = "FORNECEDOR_CNPJ", length = 80, nullable = true)
	public String getCnpj() {
		return cnpj;
	}


	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Column(name = "FORNECEDOR_EMAIL", length = 80, nullable = true)
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	@Column(name = "FORNECEDOR_TELEFONE", length = 20, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@OneToMany(mappedBy = "fornecedor")
	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	@Override
	public String toString() {
		return "Fornecedor [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", cnpj=" + cnpj + ", email=" + email
				+ ", telefone=" + telefone + "]";
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	@ManyToOne(fetch = FetchType.LAZY)		
	@JoinColumn(name = "DISTRIBUIDORA_ID",nullable = true)		//passando a fk
	public Distribuidora getDistribuidora(){
		return distribuidora;
	}

	public void setDistribuidora(Distribuidora distribuidora) {
		this.distribuidora = distribuidora;
	}
	
}
