package com.projeto.view.distribuidora;

import java.util.*;
import javax.swing.table.AbstractTableModel;
import com.projeto.model.model.Distribuidora;


public class TabelaDistribuidoraModel extends AbstractTableModel{
	
	private final String Colunas[] = {
			"CODIGO",
			"NOME",
			"EMAIL",
			"TELEFONE",
			"RUA",
			"NUMERO",
			"CIDADE",
			"ESTADO",
			"BAIRRO",
			"CEP"
	};
	
	private static final int CODIGO =   0;
	private static final int NOME =     1;
	private static final int EMAIL =  	2;
	private static final int TELEFONE = 3;
	private static final int RUA = 4;
	private static final int NUMERO = 5;
	private static final int CIDADE = 6;
	private static final int ESTADO = 7;
	private static final int BAIRRO = 8;
	private static final int CEP = 9;
	
	private List<Distribuidora> listDistribuidora;
	
	public Distribuidora getDistribuidora(int index){	//forma de quando clicar na linha, recuperar ela
		return getListDistribuidora().get(index);	//retorna da lista o indice que vc quer
	}
	
	public void saveDistribuidora (Distribuidora distribuidora) {			//salva o objeto dentro da lista
		getListDistribuidora().add(distribuidora);		//smp coloca ele na ultima posicao
		//fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);		//na hr que salva ele da um refresh na tabela e mostra pro usuario
		
	}
	
	public void updateDistribuidora(Distribuidora distribuidora, int index) {
		getListDistribuidora().set(index, distribuidora);
		fireTableRowsUpdated(index,index);	//atualiza na tabela 
	}
	
	public void removeDistribuidora(int index) {
		getListDistribuidora().remove(index);
		fireTableRowsDeleted(index,index);
	}
	
	public void removeAll() {	
		getListDistribuidora().clear();	//apaga td na tabela
		fireTableDataChanged();
	}
	

	public TabelaDistribuidoraModel()  {
		listDistribuidora = new ArrayList<Distribuidora>();
	}

	public List<Distribuidora> getListDistribuidora() {
		return listDistribuidora;
	}

	public void setListDistribuidora(List<Distribuidora> listDistribuidora) {
		this.listDistribuidora = listDistribuidora;
	}
	

	public String getColumnName(int index){		//metodo que retorna a posicao da coluna
		return getColunas()[index];
	} 

	@Override
	public int getRowCount() {		//total de linhas que tem na tabela
		return getListDistribuidora().size();
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
		Distribuidora distribuidora = getListDistribuidora().get(rowIndex);	//recupera o objeto a partir da lista 
		
		switch(columnIndex) {
		case CODIGO:
			return distribuidora.getId();
			
		case NOME:
			return distribuidora.getNome();
			
		case EMAIL:
			return distribuidora.getEmail();
			
		case TELEFONE: 
			return distribuidora.getTelefone();
		
		case RUA: 
			return distribuidora.getRua();

		case NUMERO: 
			
			return distribuidora.getNumero();
			
		case CIDADE: 
			return distribuidora.getCidade();
			
		case ESTADO: 
			return distribuidora.getEstado();
			
		case BAIRRO: 
			return distribuidora.getBairro();
			
		case CEP: 
			return distribuidora.getCep();
			
		default:
			return distribuidora;
		}
	}
	
	public Class<?> getColumnclass(int ColumnIndex){	//retorna o objeto de cada coluna
		switch(ColumnIndex) {
		case CODIGO:
			return Integer.class;
			
		case NOME:
			return String.class;
			
		case EMAIL:
			return String.class;
			
		case TELEFONE: 
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
