package com.projeto.view.fornecedor;

import java.util.*;
import javax.swing.table.AbstractTableModel;

import com.projeto.model.model.Fornecedor;

public class TabelaFornecedorModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private final String Colunas[] = {
			"CODIGO",
			"NOME",
			"CNPJ",
			"CPF",
			"EMAIL",
			"TELEFONE"
	};
	
	
	private static final int CODIGO =   0;
	private static final int NOME =     1;
	private static final int CNPJ =  	2;
	private static final int CPF = 		3;
	private static final int EMAIL =  	4;
	private static final int TELEFONE = 5;
	
	private List<Fornecedor> listFornecedor;
	
	public Fornecedor getFornecedor(int index){	//forma de quando clicar na linha, recuperar ela
		return getListFornecedor().get(index);	//retorna da lista o indice que vc quer
	}
	
	public void saveFornecedor(Fornecedor fornecedor) {			//salva o objeto dentro da lista
		getListFornecedor().add(fornecedor);		//smp coloca ele na ultima posicao
		//fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);		//na hr que salva ele da um refresh na tabela e mostra pro usuario
		
	}
	
	public void updateFornecedor (Fornecedor fornecedor, int index) {
		getListFornecedor().set(index, fornecedor);
		fireTableRowsUpdated(index,index);	//atualiza na tabela 
	}
	
	public void removeFornecedor(int index) {
		getListFornecedor().remove(index);
		fireTableRowsDeleted(index,index);
	}
	
	public void removeAll() {	
		getListFornecedor().clear();	//apaga td na tabela
		fireTableDataChanged();
	}
	
	public TabelaFornecedorModel() {
		listFornecedor = new ArrayList<Fornecedor>();
	}

	public List<Fornecedor> getListFornecedor() {
		return listFornecedor;
	}

	public void setListFornecedor(List<Fornecedor> listFornecedor) {
		this.listFornecedor = listFornecedor;
	}
	
	
	public String getColumnName(int index){		//metodo que retorna a posicao da coluna
		return getColunas()[index];
	} 

	@Override
	public int getRowCount() {		//total de linhas que tem na tabela
		return getListFornecedor().size();
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
		Fornecedor fornecedor = getListFornecedor().get(rowIndex);	//recupera o objeto a partir da lista 
		
		switch(columnIndex) {
		case CODIGO:
			return fornecedor.getId();
			
		case NOME:
			return fornecedor.getNome();
			
		case CNPJ:
			return fornecedor.getCnpj();
			
		case CPF:
			return fornecedor.getCpf();
			
		case EMAIL:
			return fornecedor.getEmail();
			
		case TELEFONE: 
			return fornecedor.getTelefone();
			
		default:
			return fornecedor;
		}
	}
	
	public Class<?> getColumnclass(int ColumnIndex){	//retorna o objeto de cada coluna
		switch(ColumnIndex) {
		case CODIGO:
			return Integer.class;
			
		case NOME:
			return String.class;
			
		case CNPJ:
			return String.class;

		case CPF:
			return String.class;
		
		case EMAIL:
			return String.class;
			
		case TELEFONE: 
			return String.class;
			
		default:
			return null;
		}
		
		
	}
}
