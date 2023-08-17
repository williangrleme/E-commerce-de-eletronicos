package com.projeto.model.service;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.projeto.model.model.Pedido;
import com.projeto.model.repository.PedidoDao;
import com.projeto.persistence.ConexaoBancoDados;

public class PedidoService {
	
	private static final String UNIT_NAME = "projeto";
	@PersistenceContext(unitName = UNIT_NAME)
	private EntityManager entityManager;
	private PedidoDao pedidoDAO;

	public PedidoService() {
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
		pedidoDAO = new PedidoDao(this.entityManager);		//inicializa pra nao ficar NULL
	}
	
	private void fecharConexaoBancoDados() {
		if(this.getEntityManager().isOpen()) {
			this.getEntityManager().close();	//fecha a transacao se tiver aberta
		}		
	}
	
	public void save (Pedido pedido) {		
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			pedidoDAO.save(pedido);	//mandou pro Dao gravar
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
	
	public void update(Pedido pedido) {
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			pedidoDAO.update(pedido);	//manda pro DAO gravar
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
	
	public void delete (Pedido pedido) {
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			pedidoDAO.delete(pedido);	//manda pro DAO gravar
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
	
	public Pedido findPedidoById(int id) {
		Pedido pedido =  pedidoDAO.findById(id);
		fecharConexaoBancoDados();
		return pedido;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public int countTotalRegistrosPedido() { 
		 return pedidoDAO.countTotalRegistrosPedido();
	}


	public List<Pedido> carregarListaPedido(int paginaAtual, int registrosPorPagina) {
		List<Pedido> x = pedidoDAO.listaPedidoPorPaginacao(paginaAtual, registrosPorPagina);
		fecharConexaoBancoDados();
		return x;
	}


	public List<Pedido> carregarListaPedido() {
		return pedidoDAO.carregarListaPedido();
	} 

}
