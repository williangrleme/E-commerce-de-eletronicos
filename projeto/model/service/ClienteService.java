package com.projeto.model.service;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.projeto.model.model.Cliente;
import com.projeto.model.repository.ClienteDao;
import com.projeto.persistence.ConexaoBancoDados;

public class ClienteService {
	private static final String UNIT_NAME = "projeto";
	
	@PersistenceContext(unitName = UNIT_NAME)
	private EntityManager entityManager;
	private ClienteDao clienteDAO;
	

	public ClienteService() {
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
		clienteDAO = new ClienteDao(this.entityManager);		//inicializa pra nao ficar NULL
	}
	
	private void fecharConexaoBancoDados() {
		if(this.getEntityManager().isOpen()) {
			this.getEntityManager().close();	//fecha a transacao se tiver aberta
		}		
	}
	
	public void save (Cliente cliente) {		
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			clienteDAO.save(cliente);	//mandou pro Dao gravar
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
	
	public void update(Cliente cliente) {
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			clienteDAO.update(cliente);	//manda pro DAO gravar
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
	
	public void delete (Cliente cliente) {
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			clienteDAO.delete(cliente);	//manda pro DAO gravar
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
	
	public Cliente findClienteById(int id) {
		Cliente cliente =  clienteDAO.findById(id);
		fecharConexaoBancoDados();
		return cliente;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public int countTotalRegistrosCliente() { 
		 return clienteDAO.countTotalRegistrosCliente();
	}


	public List<Cliente> carregarListaCliente(int paginaAtual, int registrosPorPagina) {
		List<Cliente> x = clienteDAO.listaClientePorPaginacao(paginaAtual, registrosPorPagina);
		fecharConexaoBancoDados();
		return x;
	}


	public List<Cliente> carregarListaCliente() {
		return clienteDAO.carregarListaCliente();
	} 


}
