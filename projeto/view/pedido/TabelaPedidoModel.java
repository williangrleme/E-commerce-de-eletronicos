package com.projeto.view.pedido;
import java.util.*;

import javax.swing.table.AbstractTableModel;
import com.projeto.model.model.Pedido;

public class TabelaPedidoModel extends AbstractTableModel {
	private final String Colunas[] = {
			"CODIGO",
			"STATUS",
			"QUANTIDADE"
	};
	
	
	private static final int CODIGO =   		0;
	private static final int STATUS =  			1;
	private static final int QUANTIDADE = 		2;

	private List<Pedido> listPedido;

	public List<Pedido> getListPedido() {
		return listPedido;
	}

	public void setListPedido(List<Pedido> listPedido) {
		this.listPedido = listPedido;
	}

	
	public Pedido getPedido(int index){	//forma de quando clicar na linha, recuperar ela
		return getListPedido().get(index);	//retorna da lista o indice que vc quer
	}
	
	public void savePedido(Pedido pedido) {			//salva o objeto dentro da lista
		getListPedido().add(pedido);		//smp coloca ele na ultima posicao
		//fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);		//na hr que salva ele da um refresh na tabela e mostra pro usuario
		
	}
	
	public void updatePedido(Pedido pedido, int index) {
		getListPedido().set(index, pedido);
		fireTableRowsUpdated(index,index);	//atualiza na tabela 
	}
	
	public void removePedido(int index) {
		getListPedido().remove(index);
		fireTableRowsDeleted(index,index);
	}
	
	public void removeAll() {	
		getListPedido().clear();	//apaga td na tabela
		fireTableDataChanged();
	}
	
	public TabelaPedidoModel() {
		listPedido = new ArrayList<Pedido>();
	}

	public String getColumnName(int index){		//metodo que retorna a posicao da coluna
		return getColunas()[index];
	} 

	@Override
	public int getRowCount() {		//total de linhas que tem na tabela
		return getListPedido().size();
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
		Pedido pedido = getListPedido().get(rowIndex);	//recupera o objeto a partir da lista 
		
		switch(columnIndex) {
		case CODIGO:
			return pedido.getId();
			
		case STATUS:
			return pedido.getStatus();
			
		case QUANTIDADE:
			return pedido.getQuantidade();
			
		default:
			return pedido;
		}
	}
	
	public Class<?> getColumnclass(int ColumnIndex){	//retorna o objeto de cada coluna
		switch(ColumnIndex) {
		case CODIGO:
			return Integer.class;
			
		case STATUS:
			return String.class;

		case QUANTIDADE:
			return String.class;
			
		default:
			return null;
		}
		
	}

}
