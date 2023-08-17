package com.projeto.model.repository;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import com.projeto.model.model.Cliente;

public class ClienteDao {
	private EntityManager entityManager;	// Variavel pra fazer as conexoes com o BD

	public ClienteDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	

	public void save(Cliente cliente) {
		this.getEntityManager().persist(cliente);		//insercao dos dados
	}
	public void update(Cliente cliente) {
		this.getEntityManager().merge(cliente);		//atualizacao dos dados
	}
	
	public void delete(Cliente cliente){		// apaga os dados
		this.getEntityManager().remove(entityManager.getReference(Cliente.class, cliente.getId()));
	}
	
	public Cliente findById (int id){			//acha pela chave primaria
		return this.getEntityManager().find(Cliente.class, id);	//usa o hashcode pra procurar o objeto comparando o que ta na aplicacao e oq ta no BD
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> findAll(){		//lista a entidade em questao
		Query query = this.getEntityManager().createQuery("SELCT a FROM Cliente a");		//CONSULTA SQL
		List<Cliente> listaCliente = query.getResultList();
		return listaCliente;
	}
	
	//Lista paginada pra ser apresentada pro usuario
	@SuppressWarnings("unchecked")
	public List<Cliente> listaClientePorPaginacao(int paginaAtual,int registrosPorPagina){
		List <Cliente> listaCliente = new ArrayList<Cliente>();
		if(paginaAtual >= 0) {
			Query query = this.getEntityManager().createQuery("SELECT a FROM Cliente a")
					.setFirstResult(paginaAtual)
					.setMaxResults(registrosPorPagina);
			listaCliente = query.getResultList();	//hibernate salvando nos
		}
		return listaCliente;
		
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public int countTotalRegistrosCliente() {
		Query query = this.getEntityManager().createQuery("SELECT count(a) FROM Cliente a");	//vai contar todos os registros dentro da entidade fornecedor
		Long total = (Long) query.getSingleResult();
		return total.intValue();	//cast pra long
	}

	public List<Cliente> carregarListaCliente() {
		TypedQuery<Cliente> query = this.getEntityManager().createQuery("SELECT e FROM Cliente e",Cliente.class); // tras tudo indepedente da quantidade
		return query.getResultList();		
	}

}
