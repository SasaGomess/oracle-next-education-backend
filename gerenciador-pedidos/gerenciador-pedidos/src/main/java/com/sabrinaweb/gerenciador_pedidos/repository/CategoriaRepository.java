package com.sabrinaweb.gerenciador_pedidos.repository;

import com.sabrinaweb.gerenciador_pedidos.model.Categoria;
import com.sabrinaweb.gerenciador_pedidos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
