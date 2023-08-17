package com.projeto.model.repository;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import com.projeto.model.model.Distribuidora;

public class DistribuidoraDao {
	private EntityManager entityManager;

	public DistribuidoraDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	

	public void save(Distribuidora distribuidora) {
		this.getEntityManager().persist(distribuidora);		//insercao dos dados
	}
	public void update(Distribuidora distribuidora) {
		this.getEntityManager().merge(distribuidora);		//atualizacao dos dados
	}
	
	public void delete(Distribuidora distribuidora){		// apaga os dados
		this.getEntityManager().remove(entityManager.getReference(Distribuidora.class, distribuidora.getId()));
	}
	
	public Distribuidora findById (int id){			//acha pela chave primaria
		return this.getEntityManager().find(Distribuidora.class, id);	//usa o hashcode pra procurar o objeto comparando o que ta na aplicacao e oq ta no BD
	}
	
	@SuppressWarnings("unchecked")
	public List<Distribuidora> findAll(){		//lista a entidade em questao
		Query query = this.getEntityManager().createQuery("SELCT a FROM Distribuidora a");		//CONSULTA SQL
		List<Distribuidora> listaDistribuidora = query.getResultList();
		return listaDistribuidora;
	}
	
	
	//Lista paginada pra ser apresentada pro usuario
	@SuppressWarnings("unchecked")
	public List<Distribuidora> listaDistribuidoraPorPaginacao(int paginaAtual,int registrosPorPagina){
		List <Distribuidora> listaDistribuidora = new ArrayList<Distribuidora>();
		if(paginaAtual >= 0) {
			Query query = this.getEntityManager().createQuery("SELECT a FROM Distribuidora a")
					.setFirstResult(paginaAtual)
					.setMaxResults(registrosPorPagina);
			listaDistribuidora = query.getResultList();	//hibernate salvando nos
		}
		return listaDistribuidora;
		
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public int countTotalRegistrosDistribuidora() {
		Query query = this.getEntityManager().createQuery("SELECT count(a) FROM Distribuidora a");	//vai contar todos os registros dentro da entidade 
		Long total = (Long) query.getSingleResult();
		return total.intValue();	//cast pra long
	}

	public List<Distribuidora> carregarListaDistribuidora() {
		TypedQuery<Distribuidora> query = this.getEntityManager().createQuery("SELECT e FROM Distribuidora e",Distribuidora.class); // tras tudo indepedente da quantidade
		return query.getResultList();		
	}

}
