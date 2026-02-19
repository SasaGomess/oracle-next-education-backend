package com.sabrinaweb.gerenciador_pedidos.repository;

import com.sabrinaweb.gerenciador_pedidos.model.Pedido;
import com.sabrinaweb.gerenciador_pedidos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
