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

@Entity	 //diz que isso tudo é uma entidade
@Table(name = "TAB_FUNCIONARIO") // se nao colocar o nome aqui ele pega o nome que vc coloca na classe 


public class Funcionario {
	
	private Distribuidora distribuidora;
	
	private int id;
	private String nome;
	private String telefone;
	private String cargo;
	private String cpf;
	private String genero;
	private String salario;
	private String dataNascimento;
	private String rua;
	private String numero;
	private String cidade;
	private String estado;
	private String bairro;
	private String cep;
	
	public Funcionario() {
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

	@Column(name = "CLIENTE_NOME", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
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
	@Column(name = "CLIENTE_CARGO", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	@Column(name = "CLIENTE_CPF", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	@Column(name = "CLIENTE_GENERO", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	@Column(name = "CLIENTE_SALARIO", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getSalario() {
		return salario;
	}

	public void setSalario(String salario) {
		this.salario = salario;
	}

	@Column(name = "CLIENTE_DATANASCIMENTO", length = 80, nullable = true)		//associa o ID objeto com o campo na tabela
	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
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

	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", cargo=" + cargo + ", cpf="
				+ cpf + ", genero=" + genero + ", salario=" + salario + ", dataNascimento=" + dataNascimento + ", rua="
				+ rua + ", numero=" + numero + ", cidade=" + cidade + ", estado=" + estado + ", bairro=" + bairro
				+ ", cep=" + cep + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(bairro, cargo, cep, cidade, cpf, dataNascimento, estado, genero, id, nome, numero, rua,
				salario, telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		return Objects.equals(bairro, other.bairro) && Objects.equals(cargo, other.cargo)
				&& Objects.equals(cep, other.cep) && Objects.equals(cidade, other.cidade)
				&& Objects.equals(cpf, other.cpf) && Objects.equals(dataNascimento, other.dataNascimento)
				&& Objects.equals(estado, other.estado) && Objects.equals(genero, other.genero) && id == other.id
				&& Objects.equals(nome, other.nome) && Objects.equals(numero, other.numero)
				&& Objects.equals(rua, other.rua) && Objects.equals(salario, other.salario)
				&& Objects.equals(telefone, other.telefone);
	}

	@ManyToOne(fetch = FetchType.LAZY)		
	@JoinColumn(name = "DISTRIBUIDORA_ID",nullable = true)		//passando a fk
	public Distribuidora getDistribuidora() {
		return distribuidora;
	}

	public void setDistribuidora(Distribuidora distribuidora) {
		this.distribuidora = distribuidora;
	}
	

}
