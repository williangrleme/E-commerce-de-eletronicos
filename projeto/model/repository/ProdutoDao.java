package com.projeto.model.repository;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.projeto.model.model.Produto;

public class ProdutoDao {	
private EntityManager entityManager;	// Variavel pra fazer as conexoes com o BD

	
	public ProdutoDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void save(Produto produto) {
		this.getEntityManager().persist(produto);		//insercao dos dados
	}
	public void update(Produto produto) {
		this.getEntityManager().merge(produto);		//atualizacao dos dados
	}
	
	public void delete(Produto produto){		// apaga os dados
		this.getEntityManager().remove(entityManager.getReference(Produto.class, produto.getId()));
	}
	
	public Produto findById (int id){			//acha pela chave primaria
		return this.getEntityManager().find(Produto.class, id);	//usa o hashcode pra procurar o objeto comparando o que ta na aplicacao e oq ta no BD
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> findAll(){		//lista a entidade em questao
		Query query = this.getEntityManager().createQuery("SELCT a FROM Produto a");		//CONSULTA SQL
		List<Produto> listaProduto = query.getResultList();
		return listaProduto;
	}
	
	
	//Lista paginada pra ser apresentada pro usuario
	@SuppressWarnings("unchecked")
	public List<Produto> listaProdutoPorPaginacao(int paginaAtual,int registrosPorPagina){
		List <Produto> listaProduto = new ArrayList<Produto>();
		if(paginaAtual >= 0) {
			Query query = this.getEntityManager().createQuery("SELECT a FROM Produto a")
					.setFirstResult(paginaAtual)
					.setMaxResults(registrosPorPagina);
			listaProduto = query.getResultList();	//hibernate salvando nos
		}
		return listaProduto;
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public int countTotalRegistrosProduto() {
		Query query = this.getEntityManager().createQuery("SELECT count(a) FROM Produto a");	//vai contar todos os registros dentro da entidade produto
		Long total = (Long) query.getSingleResult();
		return total.intValue();	//cast pra long
	}
}
