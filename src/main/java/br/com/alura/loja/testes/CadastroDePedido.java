package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ClienteDAO;
import br.com.alura.loja.dao.PedidoDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendaVo;

public class CadastroDePedido {
	public static void main(String[] args) {

		popularBanco();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		ClienteDAO clienteDAO = new ClienteDAO(em);

		Produto produto = produtoDAO.buscarProdutoPorId(1l);
		Cliente cliente = clienteDAO.buscarClientePorId(1l);

		em.getTransaction().begin();

		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(5, pedido, produto));

		PedidoDAO pedidoDAO = new PedidoDAO(em);
		pedidoDAO.cadastrar(pedido);

		em.getTransaction().commit();

		BigDecimal valorTotalVendido = pedidoDAO.valorTotalVendido();
		System.out.println("Total vendido: R$ " + valorTotalVendido);
		System.out.println("O total vendido foi: R$ " + valorTotalVendido);

		List<RelatorioDeVendaVo> relatorio = pedidoDAO.relatorioDeVendas();
		relatorio.forEach(System.out::println);

//		List<Object[]> relatorio = pedidoDAO.relatorioDeVendas();
//		relatorio.forEach(obj -> {
//			System.out.println(obj[0]);
//			System.out.println(obj[1]);
//			System.out.println(obj[2]);
//		});

	}

	private static void popularBanco() {

		Categoria notebooks = new Categoria("NOTEBOOKS");
		Produto macbook = new Produto("Macbook", "Macbook Apple", new BigDecimal("7500"), notebooks);
		Cliente cliente = new Cliente("Vinicius", "456123789");

		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		ClienteDAO clienteDAO = new ClienteDAO(em);
		CategoriaDAO categoriaDAO = new CategoriaDAO(em);
		clienteDAO.cadastrar(cliente);

		em.getTransaction().begin();
		categoriaDAO.cadastrar(notebooks);
		produtoDAO.cadastrar(macbook);
		clienteDAO.cadastrar(cliente);

		em.getTransaction().commit();
		em.close();
	}

}
