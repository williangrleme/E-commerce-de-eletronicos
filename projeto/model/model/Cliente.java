package com.projeto.model.model;

import java.util.List;
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

@Entity	 //diz que isso tudo é uma entidade
@Table(name = "TAB_CLIENTE") // se nao colocar o nome aqui ele pega o nome que vc coloca na classe 
public class Cliente {
	private int id;
	private String nome;
	private String telefone;
	private String email;
	private String rua;
	private String numero;
	private String cidade;
	private String estado;
	private String bairro;
	private String cep;
	private String cpf;
	
	private List<Pedido> listaPedidos;
	

	public Cliente() {
	}

	@Id 		//da pra colocar em cima do metodo ou em cima do atributo msm
	@GeneratedValue(strategy = GenerationType .IDENTITY)	//quem gerencia a PK é o BD
	@Column(name = "CLIENTE_ID")		//associa o ID objeto com o campo na tabela
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
	
	@Column(name = "CLIENTE_TELEFONE", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getTelefone() {
		return telefone;
	}


	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Column(name = "CLIENTE_EMAIL", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "CLIENTE_RUA", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getRua() {
		return rua;
	}


	public void setRua(String rua) {
		this.rua = rua;
	}

	@Column(name = "CLIENTE_NUMERO", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Column(name = "CLIENTE_CIDADE", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getCidade() {
		return cidade;
	}


	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@Column(name = "CLIENTE_ESTADO", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Column(name = "CLIENTE_BAIRRO", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getBairro() {
		return bairro;
	}


	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@Column(name = "CLIENTE_CEP", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}

	@Column(name = "CLIENTE_CPF", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	@Override
	public String toString() {
		return "Cliente [nome=" + nome + ", telefone=" + telefone + ", email=" + email + ", rua=" + rua + ", numero="
				+ numero + ", cidade=" + cidade + ", estado=" + estado + ", bairro=" + bairro + ", cep=" + cep
				+ ", cpf=" + cpf + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(bairro, cep, cidade, cpf, email, estado, nome, numero, rua, telefone);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(bairro, other.bairro) && Objects.equals(cep, other.cep)
				&& Objects.equals(cidade, other.cidade) && Objects.equals(cpf, other.cpf)
				&& Objects.equals(email, other.email) && Objects.equals(estado, other.estado)
				&& Objects.equals(nome, other.nome) && Objects.equals(numero, other.numero)
				&& Objects.equals(rua, other.rua) && Objects.equals(telefone, other.telefone);
	}

	@OneToMany(mappedBy = "cliente")
	public List<Pedido> getListaPedidos() {
		return listaPedidos;
	}

	public void setListaPedidos(List<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}
	
	

}
