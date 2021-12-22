package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Livro;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProdutos {

	public static void main(String[] args) {

		cadastrarProduto();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		
		Produto p = produtoDAO.buscarProdutoPorId(1l);
		System.out.println(p.getPreco());
		
//		List<Produto>todosProdutos = produtoDAO.buscarPorNome("Macbook");
//		todosProdutos.forEach(prod -> System.out.println(prod.getNome()));
		
		List<Produto>todosProdutos = produtoDAO.buscarPorCategoria("NOTEBOOKS");
		todosProdutos.forEach(prod -> System.out.println(prod.getNome()));
		
		BigDecimal precoProduto = produtoDAO.buscarPrecoProdutoComNome("Macbook");
		System.out.println("Preço do produto pesquisado: " + precoProduto);
//		
//		EntityManager em = JPAUtil.getEntityManager();
//		CategoriaDAO categoriaDAO = new CategoriaDAO(em);
//		ProdutoDAO produtoDAO = new ProdutoDAO(em);
//
//		Categoria celulares = new Categoria("CELULARES");
////		Produto celular = new Produto("Galaxy", "Galaxy Note 15", new BigDecimal("3000"), celulares);
//		
//		em.getTransaction().begin();
//		em.persist(celulares);
//		celulares.setNome("Smartphones");
//		
//		em.flush();
//		em.clear();
//		
//		celulares = em.merge(celulares);
//		celulares.setNome("SMARTPHONES");
//		em.flush();
//		

//		EntityManagerFactory factory = Persistence
//				.createEntityManagerFactory("loja");
//		
//		EntityManager em = factory.createEntityManager();

//		em.getTransaction().begin();
//		em.persist(celular);
//		categoriaDAO.cadastrar(celulares);
//		produtoDAO.cadastrar(celular);
//		em.getTransaction().commit();
//		em.close();

	}

	private static void cadastrarProduto() {
		EntityManager em = JPAUtil.getEntityManager();
		CategoriaDAO categoriaDAO = new CategoriaDAO(em);
		ProdutoDAO produtoDAO = new ProdutoDAO(em);

		Categoria notebooks = new Categoria("NOTEBOOKS");
		Categoria outros = new Categoria("OUTROS");
		Produto notebook = new Produto("Chromebook", "Notebook Google", new BigDecimal("2500"), notebooks);
		Produto macbook = new Produto("Macbook", "Macbook Apple", new BigDecimal("7500"), notebooks);
		Livro livro = new Livro("Quero ser Dev", "Livro para ser DEV", outros, "Vinicius", 250);
		

		em.getTransaction().begin();
		categoriaDAO.cadastrar(notebooks);
		categoriaDAO.cadastrar(outros);
		produtoDAO.cadastrar(notebook);
		produtoDAO.cadastrar(macbook);
		produtoDAO.cadastrar(livro);

		em.getTransaction().commit();
		em.close();
	}

}
