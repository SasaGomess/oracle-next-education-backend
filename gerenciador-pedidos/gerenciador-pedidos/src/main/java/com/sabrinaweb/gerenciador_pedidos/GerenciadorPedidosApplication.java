package com.sabrinaweb.gerenciador_pedidos;

import com.sabrinaweb.gerenciador_pedidos.aplicação.DataSeeding;
import com.sabrinaweb.gerenciador_pedidos.repository.CategoriaRepository;
import com.sabrinaweb.gerenciador_pedidos.repository.PedidoRepository;
import com.sabrinaweb.gerenciador_pedidos.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GerenciadorPedidosApplication implements CommandLineRunner {
	private ProdutoRepository produtoRepository;
	private PedidoRepository pedidoRepository;
	private CategoriaRepository categoriaRepository;

	public GerenciadorPedidosApplication(ProdutoRepository produtoRepository, PedidoRepository pedidoRepository, CategoriaRepository categoriaRepository) {
		this.produtoRepository = produtoRepository;
		this.pedidoRepository = pedidoRepository;
		this.categoriaRepository = categoriaRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorPedidosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		DataSeeding seeding = new DataSeeding(produtoRepository, pedidoRepository, categoriaRepository);
		seeding.salvaDadosIniciais();
	}
}
