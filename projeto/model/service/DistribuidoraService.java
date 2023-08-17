package com.projeto.model.service;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.projeto.model.model.Distribuidora;
import com.projeto.model.repository.DistribuidoraDao;
import com.projeto.persistence.ConexaoBancoDados;



public class DistribuidoraService {
	private static final String UNIT_NAME = "projeto";
	
	@PersistenceContext(unitName = UNIT_NAME)
	private EntityManager entityManager;
	private DistribuidoraDao distribuidoraDAO;
	

	public DistribuidoraService() {
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
		distribuidoraDAO = new DistribuidoraDao(this.entityManager);		//inicializa pra nao ficar NULL
	}
	
	private void fecharConexaoBancoDados() {
		if(this.getEntityManager().isOpen()) {
			this.getEntityManager().close();	//fecha a transacao se tiver aberta
		}		
	}
	
	public void save (Distribuidora distribuidora) {		
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			distribuidoraDAO.save(distribuidora);	//mandou pro Dao gravar
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
	
	public void update(Distribuidora distribuidora) {
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			distribuidoraDAO.update(distribuidora);	//manda pro DAO gravar
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
	
	public void delete (Distribuidora distribuidora) {
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			distribuidoraDAO.delete(distribuidora);	//manda pro DAO gravar
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
	
	public Distribuidora findDistribuidoraById(int id) {
		Distribuidora distribuidora =  distribuidoraDAO.findById(id);
		fecharConexaoBancoDados();
		return distribuidora;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public int countTotalRegistrosDistribuidora() { 
		 return distribuidoraDAO.countTotalRegistrosDistribuidora();
	}


	public List<Distribuidora> carregarListaDistribuidora(int paginaAtual, int registrosPorPagina) {
		List<Distribuidora> x = distribuidoraDAO.listaDistribuidoraPorPaginacao(paginaAtual, registrosPorPagina);
		fecharConexaoBancoDados();
		return x;
	}


	public List<Distribuidora> carregarListaDistribuidora() {
		return distribuidoraDAO.carregarListaDistribuidora();
	} 

}
