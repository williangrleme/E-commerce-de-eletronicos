package com.projeto.model.repository;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.projeto.model.model.Pedido;

public class PedidoDao {
	private EntityManager entityManager;	// Variavel pra fazer as conexoes com o BD
	
	public PedidoDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void save(Pedido pedido) {
		this.getEntityManager().persist(pedido);		//insercao dos dados
	}
	public void update(Pedido pedido) {
		this.getEntityManager().merge(pedido);		//atualizacao dos dados
	}
	
	public void delete(Pedido pedido){		// apaga os dados
		this.getEntityManager().remove(entityManager.getReference(Pedido.class, pedido.getId()));
	}
	
	public Pedido findById (int id){			//acha pela chave primaria
		return this.getEntityManager().find(Pedido.class, id);	//usa o hashcode pra procurar o objeto comparando o que ta na aplicacao e oq ta no BD
	}
	
	@SuppressWarnings("unchecked")
	public List<Pedido> findAll(){		//lista a entidade em questao
		Query query = this.getEntityManager().createQuery("SELCT a FROM Pedido a");		//CONSULTA SQL
		List<Pedido> listapedido = query.getResultList();
		return listapedido;
	}
	
	
	//Lista paginada pra ser apresentada pro usuario
	@SuppressWarnings("unchecked")
	public List<Pedido> listaPedidoPorPaginacao(int paginaAtual,int registrosPorPagina){
		List <Pedido> listaPedido = new ArrayList<Pedido>();
		if(paginaAtual >= 0) {
			Query query = this.getEntityManager().createQuery("SELECT a FROM Pedido a")
					.setFirstResult(paginaAtual)
					.setMaxResults(registrosPorPagina);
			listaPedido = query.getResultList();	//hibernate salvando nos
		}
		return listaPedido;
		
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public int countTotalRegistrosPedido() {
		Query query = this.getEntityManager().createQuery("SELECT count(a) FROM Pedido a");	//vai contar todos os registros dentro da entidade 
		Long total = (Long) query.getSingleResult();
		return total.intValue();	//cast pra long
	}

	public List<Pedido> carregarListaPedido() {
		TypedQuery<Pedido> query = this.getEntityManager().createQuery("SELECT e FROM Pedido e",Pedido.class); // tras tudo indepedente da quantidade
		return query.getResultList();		
	}

}
