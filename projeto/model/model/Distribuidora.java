package com.projeto.model.model;
import java.util.*;
import javax.persistence.*;

@Entity	 //diz que isso tudo é uma entidade
@Table(name = "TAB_DISTRIBUIDORA") // se nao colocar o nome aqui ele pega o nome que vc coloca na classe 

public class Distribuidora {
	private int id;
	private String nome;
	private String email;
	private String telefone;
	private String rua;
	private String numero;
	private String cidade;
	private String estado;
	private String bairro;
	private String cep;
	
	private List<Fornecedor> listaFornecedor;
	private List<Funcionario> listaFuncionario;
	
	public Distribuidora() {
		
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(bairro, cep, cidade, email, estado, id, nome, numero, rua, telefone);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Distribuidora other = (Distribuidora) obj;
		return Objects.equals(bairro, other.bairro) && Objects.equals(cep, other.cep)
				&& Objects.equals(cidade, other.cidade) && Objects.equals(email, other.email)
				&& Objects.equals(estado, other.estado) && id == other.id && Objects.equals(nome, other.nome)
				&& Objects.equals(numero, other.numero) && Objects.equals(rua, other.rua)
				&& Objects.equals(telefone, other.telefone);
	}

	@Id 		//da pra colocar em cima do metodo ou em cima do atributo msm
	@GeneratedValue(strategy = GenerationType .IDENTITY)	//quem gerencia a PK é o BD
	@Column(name = "DISTRIBUIDORA_ID")		//associa o ID objeto com o campo na tabela
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "DISTRIBUIDORA_NOME", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "DISTRIBUIDORA_EMAIL", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "DISTRIBUIDORA_TELEFONE", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@Column(name = "DISTRIBUIDORA_RUA", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}
	
	@Column(name = "DISTRIBUIDORA_NUMERO", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Column(name = "DISTRIBUIDORA_CIDADE", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	@Column(name = "DISTRIBUIDORA_ESTADO", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@Column(name = "DISTRIBUIDORA_BAIRRO", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	@Column(name = "DISTRIBUIDORA_CEP", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public String toString() {
		return "Distribuidora [id=" + id + ", nome=" + nome + ", email=" + email + ", telefone=" + telefone + ", rua="
				+ rua + ", numero=" + numero + ", cidade=" + cidade + ", estado=" + estado + ", bairro=" + bairro
				+ ", cep=" + cep + "]";
	}

	@OneToMany(mappedBy = "distribuidora")
	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}


	@OneToMany(mappedBy = "distribuidora")
	public List<Funcionario> getListaFuncionario() {
		return listaFuncionario;
	}


	public void setListaFuncionario(List<Funcionario> listaFuncionario) {
		this.listaFuncionario = listaFuncionario;
	}
}
