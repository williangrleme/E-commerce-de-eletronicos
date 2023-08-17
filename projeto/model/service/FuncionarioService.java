package com.projeto.model.service;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.projeto.model.model.Funcionario;
import com.projeto.model.repository.FuncionarioDao;
import com.projeto.persistence.ConexaoBancoDados;

public class FuncionarioService {
	private static final String UNIT_NAME = "projeto";
	@PersistenceContext(unitName = UNIT_NAME)
	private EntityManager entityManager;
	private FuncionarioDao funcionarioDAO;

	public FuncionarioService() {
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
		funcionarioDAO = new FuncionarioDao(this.entityManager);		//inicializa pra nao ficar NULL
	}
	
	private void fecharConexaoBancoDados() {
		if(this.getEntityManager().isOpen()) {
			this.getEntityManager().close();	//fecha a transacao se tiver aberta
		}		
	}
	
	public void save (Funcionario funcionario) {		
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			funcionarioDAO.save(funcionario);	//mandou pro Dao gravar
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
	
	public void update(Funcionario funcionario) {
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			funcionarioDAO.update(funcionario);	//manda pro DAO gravar
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
	
	public void delete (Funcionario funcionario) {
		EntityTransaction trx = this.getEntityManager().getTransaction();	//abre a a transacao com o BD
		try {
			trx.begin();	//inicia a transacao
			funcionarioDAO.delete(funcionario);	//manda pro DAO gravar
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
	
	public Funcionario findFuncionarioById(int id) {
		Funcionario funcionario =  funcionarioDAO.findById(id);
		fecharConexaoBancoDados();
		return funcionario;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public int countTotalRegistrosFuncionario() { 
		 return funcionarioDAO.countTotalRegistrosFuncionario();
	}


	public List<Funcionario> carregarListaFuncionario(int paginaAtual, int registrosPorPagina) {
		List<Funcionario> x = funcionarioDAO.listaFuncionarioPorPaginacao(paginaAtual, registrosPorPagina);
		fecharConexaoBancoDados();
		return x;
	}


	public List<Funcionario> carregarListaFuncionario() {
		return funcionarioDAO.carregarListaFuncionario();
	} 

}
