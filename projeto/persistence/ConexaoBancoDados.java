// CARA QUE FAZ O ACESSO FISICO MESMO COM O BD PARA FAZER AS TRANSACOES NECESSARIAS NO PROJETO
package com.projeto.persistence;
import javax.persistence.*;
import java.util.*;

public class ConexaoBancoDados {
	
	
	private String UNIT_NAME = "projeto";	//nome do projeto
	
	private static ConexaoBancoDados CONEXAO;	//a classe inicializa ela msm
	private static EntityManagerFactory FACTORY;
	
	private ConexaoBancoDados() {
		if(FACTORY == null) {
			FACTORY = getFactory();
		}
	}
	
	public static ConexaoBancoDados getConexao(){	
		if (CONEXAO == null) {		// se n tiver conexao, cria ela (inicializa)
			CONEXAO = new ConexaoBancoDados();
		}
		return CONEXAO;		//dai se tiver ou dps que inicializou, retorna ela
	}
	
	public EntityManager getEntityManager() {
		return FACTORY.createEntityManager();
	}
	
	private EntityManagerFactory getFactory() {		//fazendo conexao
		
		Map<String,String> properties = new HashMap<String, String>();	//cria um mapa na memoria com uma chave do tipo string que recebe um valor string tbm
		
		
		//properties.put("javax.persistence.schema-generation.database.action", "drop-and-create"); //cria as tabela do banco e deleta as que ja existe, quando o projeto ja tiver pronto comenta ou apaga essa linha 
		properties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");				//carrega a classe do hibernate pra conectar com o BD
		properties.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/projeto?createDatabaseIfNotExist=true&useSSL=false");	// Essa parte eh individual de cada Banco
		properties.put("hibernate.connection.username", "root");
		properties.put("hibernate.connection.password", "");	// senha do banco
		properties.put("hibernate.c3p0.min_size", "10");		//permite 10 conexoes simultaneas minimas
		properties.put("hibernate.c3p0.max_size" ,"20" );		//limite de 20 conexoes simultaneas 
		properties.put("hibernate.c3p0.acquire_increment","1");		//numero de conexoes que quer que seja criadas
		properties.put("hibernate.c3p0.idle_test_period" ,"3000");	//mantem no periodo de 3ms
		properties.put("hibernate.c3p0.max_statements","50" );
		properties.put("hibernate.c3p0.timeout","1800" ); // se ngm tiver usando ele desconecta 
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");	//dialetica do mysql 5
		//properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
		properties.put("hibernate.show_sql", "true");	//mostar o SQl que ta sendo executado
		properties.put("hibernate.format_sql", "true");	//quando for pra producao comenta
		properties.put("useUnicode", "true");
		properties.put("characterEncoding", "UTF-8");	//reconhece os assentos br
		properties.put("hibernate.default_schema", "projeto");	
		properties.put("hibernate.hbm2ddl.auto", "update"); // aqui eh pra atualizar o banco e n ficar apagando ele
		
		return Persistence.createEntityManagerFactory(UNIT_NAME, properties);
	}

}
