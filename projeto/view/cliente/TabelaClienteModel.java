package com.projeto.view.cliente;
import java.util.*;
import javax.swing.table.AbstractTableModel;

import com.projeto.model.model.Cliente;

public class TabelaClienteModel extends AbstractTableModel{
		
	private final String Colunas[] = {
			"CODIGO",
			"NOME",
			"TELEFONE",
			"EMAIL",
			"RUA",
			"NUMERO",
			"CIDADE",
			"ESTADO",
			"BAIRRO",
			"CEP",
			"CPF"
	};
	
	private static final int CODIGO =  		0;
	private static final int NOME =     	1;
	private static final int TELEFONE =  	2;
	private static final int EMAIL = 		3;
	private static final int RUA =  		4;
	private static final int NUMERO = 		5;
	private static final int CIDADE = 		6;
	private static final int ESTADO = 		7;
	private static final int BAIRRO = 		8;
	private static final int CEP =			9;
	private static final int CPF = 			10;
	

	private List<Cliente> listCliente;
	
	public Cliente getCliente(int index){	//forma de quando clicar na linha, recuperar ela
		return getListCliente().get(index);	//retorna da lista o indice que vc quer
	}
	
	public void saveCliente(Cliente Cliente) {			//salva o objeto dentro da lista
		getListCliente().add(Cliente);		//smp coloca ele na ultima posicao
		//fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);		//na hr que salva ele da um refresh na tabela e mostra pro usuario
		
	}
	
	public void updateCliente (Cliente Cliente, int index) {
		getListCliente().set(index, Cliente);
		fireTableRowsUpdated(index,index);	//atualiza na tabela 
	}
	
	public void removeCliente(int index) {
		getListCliente().remove(index);
		fireTableRowsDeleted(index,index);
	}
	
	public void removeAll() {	
		getListCliente().clear();	//apaga td na tabela
		fireTableDataChanged();
	}
	
	public TabelaClienteModel() {
		listCliente = new ArrayList<Cliente>();
	}

	public List<Cliente> getListCliente() {
		return listCliente;
	}

	public void setListCliente(List<Cliente> listCliente) {
		this.listCliente = listCliente;
	}
	
	
	public String getColumnName(int index){		//metodo que retorna a posicao da coluna
		return getColunas()[index];
	} 

	@Override
	public int getRowCount() {		//total de linhas que tem na tabela
		return getListCliente().size();
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
		Cliente Cliente = getListCliente().get(rowIndex);	//recupera o objeto a partir da lista 
		
		switch(columnIndex) {
		case CODIGO:
			return Cliente.getId();
			
		case NOME:
			return Cliente.getNome();
			
		case TELEFONE:
			return Cliente.getTelefone();
			
		case EMAIL:
			return Cliente.getEmail();
			
		case RUA:
			return Cliente.getRua();
			
		case NUMERO:
			return Cliente.getNumero();
			
		case CIDADE:
			return Cliente.getCidade();
			
		case ESTADO:
			return Cliente.getEstado();
			
		case BAIRRO:
			return Cliente.getBairro();
			
		case CEP:
			return Cliente.getCep();
			
		case CPF:
			return Cliente.getCpf();
			
		default:
			return Cliente;
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
			
		case EMAIL:
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
			
		case CPF:
			return String.class;
			
		default:
			return null;
		}
		
		
	}

}
