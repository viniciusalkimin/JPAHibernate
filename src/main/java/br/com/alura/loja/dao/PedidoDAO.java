package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.vo.RelatorioDeVendaVo;

public class PedidoDAO {

	private EntityManager em;

	public PedidoDAO(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}

	public void atualizar(Pedido pedido) {
		this.em.merge(pedido);
	}

	public void remover(Pedido pedido) {
		pedido = em.merge(pedido);
		this.em.remove(pedido);
	}

	public Pedido buscarPedidoPorId(Long id) {
		return em.find(Pedido.class, id);

	}

	public List<Pedido> buscarTodos() {
		String jpql = "SELECT p FROM Produto p";
		return em.createQuery(jpql, Pedido.class).getResultList();
	}

	public List<Pedido> buscarPorNome(String nome) {
		String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome"; /*
																		 * Ou
																		 * "SELECT p FROM Produto p WHERE p.nome = ?1";
																		 */
		return em.createQuery(jpql, Pedido.class).setParameter("nome", nome)
				.getResultList(); /* .setParameter(1, nome) */
	}

	public List<Pedido> buscarPorCategoria(String nome) {
		String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = :nome"; /* INNER JOIN */
		return em.createQuery(jpql, Pedido.class).setParameter("nome", nome).getResultList();
	}

	public BigDecimal valorTotalVendido() {
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
		return em.createQuery(jpql, BigDecimal.class).getSingleResult();
	}

	public List<RelatorioDeVendaVo> relatorioDeVendas() {
		String jpql = "SELECT new br.com.alura.loja.vo.RelatorioDeVendaVo ( produto.nome, SUM(item.quantidade), MAX(pedido.data)) "
				+ "FROM  Pedido pedido JOIN  pedido.itens item JOIN item.produto produto GROUP BY produto.nome ORDER BY item.quantidade DESC ";
		return em.createQuery(jpql, RelatorioDeVendaVo.class).getResultList(); /* SELECT new - Query orientada a obj */
	}

//	public List<Object[]> relatorioDeVendas() {
//		String jpql = "SELECT produto.nome, SUM(item.quantidade), MAX(pedido.data) "
//				+ "FROM  Pedido pedido JOIN  pedido.itens item JOIN item.produto produto GROUP BY produto.nome ORDER BY item.quantidade DESC ";
//		return em.createQuery(jpql, Object[].class).getResultList();
//	}
	
	public Pedido buscarPedidoComCliente(Long id) { 	/* quando faz um JOIN em uma fetch LAZY e o Entity manager está fechado */
		return em.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE  p.id = :id	", Pedido.class)
				.setParameter("id", id).getSingleResult(); 	/* Consulta planejada, para evitar LazyInitializationException*/
		
	}

}
