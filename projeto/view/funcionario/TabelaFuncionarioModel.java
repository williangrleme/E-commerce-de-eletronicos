package com.projeto.view.funcionario;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.projeto.model.model.Funcionario;

public class TabelaFuncionarioModel extends AbstractTableModel {
	
	private final String Colunas[] = {
			"CODIGO",
			"NOME",
			"TELEFONE",
			"CARGO",
			"CPF",
			"GENERO",
			"SALARIO",
			"DATANASCIMENTO",
			"RUA",
			"NUMERO",
			"CIDADE",
			"ESTADO",
			"BAIRRO",
			"CEP"
			
	};
	
	private static final int CODIGO =   		0;
	private static final int NOME =     		1;
	private static final int TELEFONE =     	2;
	private static final int CARGO =     		3;
	private static final int CPF =     			4;
	private static final int GENERO =     		5;
	private static final int SALARIO =     		6;
	private static final int DATANASCIMENTO =   7;
	private static final int RUA =     			8;
	private static final int NUMERO =     		9;
	private static final int CIDADE =     		10;
	private static final int ESTADO =     		11;
	private static final int BAIRRO =     		12;
	private static final int CEP =     			13;



	private List<Funcionario> listFuncionario;
	
	public Funcionario getFuncionario(int index){	//forma de quando clicar na linha, recuperar ela
		return getListFuncionario().get(index);	//retorna da lista o indice que vc quer
	}
	
	public void saveFuncionario(Funcionario funcionario) {			//salva o objeto dentro da lista
		getListFuncionario().add(funcionario);		//smp coloca ele na ultima posicao
		//fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);		//na hr que salva ele da um refresh na tabela e mostra pro usuario
		
	}
	
	public void updateFuncionario (Funcionario funcionario, int index) {
		getListFuncionario().set(index, funcionario);
		fireTableRowsUpdated(index,index);	//atualiza na tabela 
	}
	
	public void removeFuncionario(int index) {
		getListFuncionario().remove(index);
		fireTableRowsDeleted(index,index);
	}
	
	public void removeAll() {	
		getListFuncionario().clear();	//apaga td na tabela
		fireTableDataChanged();
	}
	
	public TabelaFuncionarioModel() {
		listFuncionario = new ArrayList<Funcionario>();
	}

	public List<Funcionario> getListFuncionario() {
		return listFuncionario;
	}

	public void setListFuncionario(List<Funcionario> listFuncionario) {
		this.listFuncionario = listFuncionario;
	}
	
	
	public String getColumnName(int index){		//metodo que retorna a posicao da coluna
		return getColunas()[index];
	} 

	@Override
	public int getRowCount() {		//total de linhas que tem na tabela
		return getListFuncionario().size();
	}

	@Override
	public int getColumnCount() {	//total de colunas que tem na tabela
		return getColunas().length;		//quantidade de colunas 
	}

	public String[] getColunas() {
		return Colunas;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {	//tipo de dado que vai ser manipulado
		Funcionario funcionario = getListFuncionario().get(rowIndex);	//recupera o objeto a partir da lista 
		
		switch(columnIndex) {
		case CODIGO:
			return funcionario.getId();
			
		case NOME:
			return funcionario.getNome();
			
		case TELEFONE:
			return funcionario.getTelefone();
		
		case CARGO:
			return funcionario.getCargo();
	
		case CPF:
			return funcionario.getCpf();
			
		case GENERO:
			return funcionario.getGenero();
			
		case SALARIO:
			return funcionario.getSalario();
			
		case DATANASCIMENTO:
			return funcionario.getDataNascimento();
			
		case RUA:
			return funcionario.getRua();
			
		case NUMERO:
			return funcionario.getNumero();
			
		case CIDADE:
			return funcionario.getCidade();
			
		case ESTADO:
			return funcionario.getEstado();
			
		case BAIRRO:
			return funcionario.getBairro();
			
		case CEP:
			return funcionario.getCep();
			
		default:
			return funcionario;
		}
	}
	
	public Class<?> getColumnclass(int ColumnIndex){	//retorna o objeto de cada coluna
		switch(ColumnIndex) {
		case CODIGO:
			return Integer.class;
			
		case NOME:
			return String.class;
			
		case TELEFONE:
			return String.class;
		
		case CARGO:
			return String.class;
	
		case CPF:
			return String.class;
			
		case GENERO:
			return String.class;
			
		case SALARIO:
			return String.class;
			
		case DATANASCIMENTO:
			return String.class;
			
		case RUA:
			return String.class;
			
		case NUMERO:
			return String.class;
			
		case CIDADE:
			return String.class;
			
		case ESTADO:
			return String.class;
			
		case BAIRRO:
			return String.class;
			
		case CEP:
			return String.class;
			
		default:
			return null;
		}
		
		
	}
}
