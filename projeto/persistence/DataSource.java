package com.projeto.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

public class DataSource {
private static final String UNIT_NAME = "projeto";
	
	@PersistenceContext(unitName = UNIT_NAME)
	private EntityManager entityManager; 

private Connection connection = null;
	
	public DataSource() {
		this.entityManager = ConexaoBancoDados.getConexao().getEntityManager();
	}
	   
	//n da pra conectar o entitymanager com a tabela jasper utlizando a conexao com o banco que usa pra aplicacao
	// dai tem que criar essa conexao individualmente aqui
	public Connection getConnection() {
		
		Session session =  this.entityManager.unwrap(Session.class);
		
		Conexao conexao = new Conexao();
		session.doWork(conexao);
		
		connection = conexao.getConnection();
		
		return connection;
	}
	
	
	private static class Conexao implements Work{
		
		private Connection connection;
		
		@Override
		public void execute(Connection connection) throws SQLException {
			
			this.connection = connection;
		}
		
		public Connection getConnection() {
			return this.connection;
		}
	}
}
