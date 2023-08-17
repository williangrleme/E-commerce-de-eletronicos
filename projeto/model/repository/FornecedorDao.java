// FAZ AS OPERACOES BASICAS

package com.projeto.model.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.projeto.model.model.Fornecedor;

public class FornecedorDao {
	
	private EntityManager entityManager;	// Variavel pra fazer as conexoes com o BD

	
	public FornecedorDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void save(Fornecedor fornecedor) {
		this.getEntityManager().persist(fornecedor);		//insercao dos dados
	}
	public void update(Fornecedor fornecedor) {
		this.getEntityManager().merge(fornecedor);		//atualizacao dos dados
	}
	
	public void delete(Fornecedor fornecedor){		// apaga os dados
		this.getEntityManager().remove(entityManager.getReference(Fornecedor.class, fornecedor.getId()));
	}
	
	public Fornecedor findById (int id){			//acha pela chave primaria
		return this.getEntityManager().find(Fornecedor.class, id);	//usa o hashcode pra procurar o objeto comparando o que ta na aplicacao e oq ta no BD
	}
	
	@SuppressWarnings("unchecked")
	public List<Fornecedor> findAll(){		//lista a entidade em questao
		Query query = this.getEntityManager().createQuery("SELCT a FROM Fornecedor a");		//CONSULTA SQL
		List<Fornecedor> listaForncedor = query.getResultList();
		return listaForncedor;
	}
	
	
	//Lista paginada pra ser apresentada pro usuario
	@SuppressWarnings("unchecked")
	public List<Fornecedor> listaFornecedorPorPaginacao(int paginaAtual,int registrosPorPagina){
		List <Fornecedor> listaFornecedor = new ArrayList<Fornecedor>();
		if(paginaAtual >= 0) {
			Query query = this.getEntityManager().createQuery("SELECT a FROM Fornecedor a")
					.setFirstResult(paginaAtual)
					.setMaxResults(registrosPorPagina);
			listaFornecedor = query.getResultList();	//hibernate salvando nos
		}
		return listaFornecedor;
		
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public int countTotalRegistrosFornecedor() {
		Query query = this.getEntityManager().createQuery("SELECT count(a) FROM Fornecedor a");	//vai contar todos os registros dentro da entidade fornecedor
		Long total = (Long) query.getSingleResult();
		return total.intValue();	//cast pra long
	}

	public List<Fornecedor> carregarListaFornecedor() {
		TypedQuery<Fornecedor> query = this.getEntityManager().createQuery("SELECT e FROM Fornecedor e",Fornecedor.class); // tras tudo indepedente da quantidade
		return query.getResultList();		
	}
	
}
