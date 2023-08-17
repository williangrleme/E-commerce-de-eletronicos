package com.projeto.model.repository;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import com.projeto.model.model.Funcionario;

public class FuncionarioDao {
	
	private EntityManager entityManager;	// Variavel pra fazer as conexoes com o BD


	public FuncionarioDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	

	public void save(Funcionario funcionario) {
		this.getEntityManager().persist(funcionario);		//insercao dos dados
	}
	public void update(Funcionario funcionario) {
		this.getEntityManager().merge(funcionario);		//atualizacao dos dados
	}
	
	public void delete(Funcionario funcionario){		// apaga os dados
		this.getEntityManager().remove(entityManager.getReference(Funcionario.class, funcionario.getId()));	}
	
	public Funcionario findById (int id){			//acha pela chave primaria
		return this.getEntityManager().find(Funcionario.class, id);	//usa o hashcode pra procurar o objeto comparando o que ta na aplicacao e oq ta no BD
	}
	
	@SuppressWarnings("unchecked")
	public List<Funcionario> findAll(){		//lista a entidade em questao
		Query query = this.getEntityManager().createQuery("SELCT a FROM Funcionario a");		//CONSULTA SQL
		List<Funcionario> listaFuncionario = query.getResultList();
		return listaFuncionario;
	}
	
	
	//Lista paginada pra ser apresentada pro usuario
	@SuppressWarnings("unchecked")
	public List<Funcionario> listaFuncionarioPorPaginacao(int paginaAtual,int registrosPorPagina){
		List <Funcionario> listaFuncionario = new ArrayList<Funcionario>();
		if(paginaAtual >= 0) {
			Query query = this.getEntityManager().createQuery("SELECT a FROM Funcionario a")
					.setFirstResult(paginaAtual)
					.setMaxResults(registrosPorPagina);
			listaFuncionario = query.getResultList();	//hibernate salvando nos
		}
		return listaFuncionario;
		
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public int countTotalRegistrosFuncionario() {
		Query query = this.getEntityManager().createQuery("SELECT count(a) FROM Funcionario a");	//vai contar todos os registros dentro da entidade fornecedor
		Long total = (Long) query.getSingleResult();
		return total.intValue();	//cast pra long
	}

	public List<Funcionario> carregarListaFuncionario() {
		TypedQuery<Funcionario> query = this.getEntityManager().createQuery("SELECT e FROM Funcionario e",Funcionario.class); // tras tudo indepedente da quantidade
		return query.getResultList();		
	}

}
