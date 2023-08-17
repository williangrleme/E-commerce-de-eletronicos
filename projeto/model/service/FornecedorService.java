// SERVICE EH QUEM VALIDA AS INFORMACOES PARA FAZER A TRANSACAO COM O BD
// FAZ A PONTE ENTRE REPOSITORY E O DAO

package com.projeto.model.service;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.projeto.model.model.Fornecedor;
import com.projeto.model.repository.FornecedorDao;
import com.projeto.persistence.ConexaoBancoDados;

public class FornecedorService {		//faz conexao com que chegue no BD
	
	private static final String UNIT_NAME = "projeto";
	
	@PersistenceContext(unitName = UNIT_NAME)
	private EntityManager entityManager;
	private FornecedorDao fornecedorDAO;
	
	public FornecedorService() {	
		this.entityManager = null;
		abrirConexaoBancoDados();
	}
	
	
	private void abrirConexaoBancoDados() { 
		if(Objects.isNull(this.entityManager)) { 		//se for a primeira vez, vai ser nula, entao abre e inicializa o DAO
			setEntityManager(ConexaoBancoDados.getConexao().getEntityManager()); 	//faz conexao com o BD
		}
		if(!this.getEntityManager().isOpen()) {	//se nao tiver aberta, pode ter sido criada, mas ta fechada
			setEntityManager(ConexaoBancoDados.getConexao().getEntityManager()); 	//faz conexao com o BD
		}
		fornecedorDAO = new FornecedorDao(this.entityManager);		//inicializa pra nao ficar NULL
	}
	
	private void fecharConexaoBancoDados() {
		if(this.getEntityManager().isOpen()) {
			this.getEntityManager().close();	//fecha a transacao se tiver aberta
		}		
	}
	
	public void save (Fornecedor fornecedor) {		
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			fornecedorDAO.save(fornecedor);	//mandou pro Dao gravar
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
	
	public void update(Fornecedor fornecedor) {
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			fornecedorDAO.update(fornecedor);	//manda pro DAO gravar
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
	
	public void delete (Fornecedor fornecedor) {
		abrirConexaoBancoDados();
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			fornecedorDAO.delete(fornecedor);	//manda pro DAO gravar
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
	
	public Fornecedor findFornecedorById(int id) {
		Fornecedor fornecedor =  fornecedorDAO.findById(id);
		fecharConexaoBancoDados();
		return fornecedor;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public int countTotalRegistrosFornecedor() { 
		 return fornecedorDAO.countTotalRegistrosFornecedor();
	}


	public List<Fornecedor> carregarListaFornecedor(int paginaAtual, int registrosPorPagina) {
		List<Fornecedor> x = fornecedorDAO.listaFornecedorPorPaginacao(paginaAtual, registrosPorPagina);
		fecharConexaoBancoDados();
		return x;
	}


	public List<Fornecedor> carregarListaFornecedor() {
		return fornecedorDAO.carregarListaFornecedor();
	} 
	
}
