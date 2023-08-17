// SERVICE EH QUEM VALIDA AS INFORMACOES PARA FAZER A TRANSACAO COM O BD
// FAZ A PONTE ENTRE REPOSITORY E O DAO

package com.projeto.model.service;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.projeto.model.model.Produto;
import com.projeto.model.model.Produto;
import com.projeto.model.repository.ProdutoDao;
import com.projeto.model.repository.ProdutoDao;
import com.projeto.persistence.ConexaoBancoDados;

public class ProdutoService {

	private static final String UNIT_NAME = "projeto";
	
	@PersistenceContext(unitName = UNIT_NAME)
	private EntityManager entityManager;
	private ProdutoDao produtoDAO;
	
	public ProdutoService() {	
		this.entityManager = null;
	}
	
	
	private void abrirConexaoBancoDados() {
		if(Objects.isNull(this.entityManager)) { 		//se for a primeira vez, vai ser nula, entao abre e inicializa o DAO
			setEntityManager(ConexaoBancoDados.getConexao().getEntityManager()); 	//faz conexao com o BD
		}
		if(!this.getEntityManager().isOpen()) {	//se nao tiver aberta, pode ter sido criada, mas ta fechada
			setEntityManager(ConexaoBancoDados.getConexao().getEntityManager()); 	//faz conexao com o BD
		}
		produtoDAO = new ProdutoDao(this.entityManager);		//inicializa pra nao ficar NULL
	}
	
	private void fecharConexaoBancoDados() {
		if(this.getEntityManager().isOpen()) {
			this.getEntityManager().close();	//fecha a transacao se tiver aberta
		}		
	}
	
	public void save (Produto produto) {		
		abrirConexaoBancoDados();
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			produtoDAO.save(produto);	//mandou pro Dao gravar
			trx.commit(); 
		}catch(Throwable t) {
			t.printStackTrace();
			if(trx.isActive()) {		//se deu ruim ele volta pro inicio
				trx.rollback();
			}
		}finally {
			fecharConexaoBancoDados();
		}
		
	}
	
	public void update(Produto produto) {
		abrirConexaoBancoDados();
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			produtoDAO.update(produto);	//manda pro DAO gravar
			trx.commit(); 
		}catch(Throwable t) {
			t.printStackTrace();
			if(trx.isActive()) {			//se deu ruim ele volta pro inicio
				trx.rollback();
			}
		}finally {
			fecharConexaoBancoDados();
		}
	}
	
	public void delete (Produto produto) {
		abrirConexaoBancoDados();
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			produtoDAO.delete(produto);	//manda pro DAO gravar
			trx.commit(); 
		}catch(Throwable t) {
			t.printStackTrace();
			if(trx.isActive()) {			//se deu ruim ele volta pro inicio
				trx.rollback();
			}
		}finally {
			fecharConexaoBancoDados();
		}
	}
	
	public Produto findProdutoById(int id) {
		abrirConexaoBancoDados();
		Produto produto =  produtoDAO.findById(id);
		fecharConexaoBancoDados();
		return produto;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public int countTotalRegistrosProduto() { 
		 abrirConexaoBancoDados();
		 int x = produtoDAO.countTotalRegistrosProduto();
		 fecharConexaoBancoDados();
		 return x;
	}


	public List<Produto> carregarListaProduto(int paginaAtual, int registrosPorPagina) {
		abrirConexaoBancoDados();
		List<Produto> x = produtoDAO.listaProdutoPorPaginacao(paginaAtual, registrosPorPagina);
		fecharConexaoBancoDados();
		return x;
	} 

}
