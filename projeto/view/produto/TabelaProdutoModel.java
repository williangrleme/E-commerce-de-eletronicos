package com.projeto.view.produto;

import java.util.*;
import javax.swing.table.AbstractTableModel;

import com.projeto.model.model.Produto;

public class TabelaProdutoModel extends AbstractTableModel {
	
	private static final long serialVersionUID = -80531848382948512L;



	private final String Colunas[] = {
			"CODIGO",
			"NOME",
			"CATEGORIA",
			"QUANTIDADE",
			"PRECO",
			"CODIGO DE BARRAS",
			"MARCA",
			"MODELO",
			"PESO",
			"GARANTIA"
			
	};
	
	
	private static final int CODIGO   = 	    0;
	private static final int NOME      =        1;
	private static final int CATEGORIA =        2;
	private static final int QUANTIDADE =       3;
	private static final int PRECO =            4;
	private static final int CODIGO_DE_BARRAS = 5;
	private static final int MARCA =    	    6;
	private static final int MODELO =    	    7;
	private static final int PESO =     		8;
	private static final int GARANTIA =     	9;
	
	private List<Produto> listaProduto;
	
	public Produto getProduto(int index){	//forma de quando clicar na linha, recuperar ela
		return getListaProduto().get(index);	//retorna da lista o indice que vc quer
	}
	
	public void saveProduto(Produto produto) {			//salva o objeto dentro da lista
		getListaProduto().add(produto);		//smp coloca ele na ultima posicao
		//fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);		//na hr que salva ele da um refresh na tabela e mostra pro usuario
		
	}
	
	public void updateProduto (Produto produto, int index) {
		getListaProduto().set(index, produto);
		fireTableRowsUpdated(index,index);	//atualiza na tabela 
	}
	
	public void removeProduto(int index) {
		getListaProduto().remove(index);
		fireTableRowsDeleted(index,index);
	}
	
	public void removeAll() {	
		getListaProduto().clear();	//apaga td na tabela
		fireTableDataChanged();
	}
	
	public TabelaProdutoModel() {
		listaProduto = new ArrayList<Produto>();
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}
	
	public String getColumnName(int index){		//metodo que retorna a posicao da coluna
		return getColunas()[index];
	} 

	@Override
	public int getRowCount() {		//total de linhas que tem na tabela
		return getListaProduto().size();
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
		Produto produto = getListaProduto().get(rowIndex);	//recupera o objeto a partir da lista 
		
		switch(columnIndex) {
		case CODIGO:
			return produto.getId();
			
		case NOME:
			return produto.getNome();
			
		case CATEGORIA:
			return produto.getCategoria();
		
		case QUANTIDADE:
			return produto.getQuantidade();
		
		case PRECO:
			return produto.getPreco();
		
		case CODIGO_DE_BARRAS:
			return produto.getCodigoBarras();
			
		case MARCA:
			return produto.getMarca();
		
		case MODELO:
			return produto.getModelo();
		
		case PESO:
			return produto.getPeso();
		
		case GARANTIA:
			return produto.getGarantia();
			
		default:
			return produto;
		}
	}
	
	public Class<?> getColumnclass(int ColumnIndex){	//retorna o objeto de cada coluna
		//if(getListFornecedor().isEmpty()) {	// se a lista ta vazia
			//return Object.class;	//retorna a classe pai
		//}
		//return getValueAt(0,ColumnIndex).getClass();	//retorna o elemento em determinada coluna
		
		switch(ColumnIndex) {
		case CODIGO:
			return Integer.class;
			
		case NOME:
			return String.class;

		case CATEGORIA:
			return String.class;
		
		case QUANTIDADE:
			return Integer.class;
		
		case PRECO:
			return Float.class;
		
		case CODIGO_DE_BARRAS:
			return Integer.class;
			
		case MARCA:
			return String.class;
		
		case MODELO:
			return String.class;
		
		case PESO:
			return Float.class;
		
		case GARANTIA:
			return String.class;
			
		default:
			return null;
		}
		
		
	}
}
	