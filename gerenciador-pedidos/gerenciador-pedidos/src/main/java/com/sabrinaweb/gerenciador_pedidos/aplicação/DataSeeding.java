package com.sabrinaweb.gerenciador_pedidos.aplicação;

import com.sabrinaweb.gerenciador_pedidos.model.Categoria;
import com.sabrinaweb.gerenciador_pedidos.model.Pedido;
import com.sabrinaweb.gerenciador_pedidos.model.Produto;
import com.sabrinaweb.gerenciador_pedidos.repository.CategoriaRepository;
import com.sabrinaweb.gerenciador_pedidos.repository.PedidoRepository;
import com.sabrinaweb.gerenciador_pedidos.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSeeding {
    private ProdutoRepository produtoRepository;
    private PedidoRepository pedidoRepository;
    private CategoriaRepository categoriaRepository;

    public DataSeeding(ProdutoRepository produtoRepository, PedidoRepository pedidoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public void salvaDadosIniciais(){
//        Categoria c1 = new Categoria("Cosméticos");
//        Categoria c2 = new Categoria("Eletrônicos");
//        Categoria c3 = new Categoria("Alimentícios");
//
//        Produto p1 = new Produto("Tablet Galaxy S2", 3560.00);
//        Produto p2 = new Produto("Perfume Dior Home", 1600.00);
//        Produto p3 = new Produto("Molho de Tomate Premium", 20.00);
//
//        Pedido pd1 = new Pedido(LocalDate.parse("2026-01-14"));
//        Pedido pd2 = new Pedido(LocalDate.parse("2026-01-28"));
//        Pedido pd3 = new Pedido(LocalDate.parse("2026-01-25"));
//
//        produtoRepository.saveAll(List.of(p1, p2, p3));
//        pedidoRepository.saveAll(List.of(pd1, pd2, pd3));
//        categoriaRepository.saveAll(List.of(c1, c2, c3));

        Categoria c4 = new Categoria("Papelaria");
        Categoria c5 = new Categoria("Vestimenta");
        Categoria c6 = new Categoria("Eletrodomésticos");

        Produto p4 = new Produto("Air Fryer", 560.00);
        Produto p5 = new Produto("Caderno Capadura", 43.00);
        Produto p6 = new Produto("Calça Social", 90.00);

        c4.adicionaProduto(p5);
        c5.adicionaProduto(p6);
        c6.adicionaProduto(p4);

        categoriaRepository.saveAll(List.of(c4, c5, c6));

        
    }
}
