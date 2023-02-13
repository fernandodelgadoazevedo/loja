package com.games.loja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.games.loja.model.Cadastro;

@Repository
public interface CadastroRepository extends JpaRepository<Cadastro, Long> {
	
	List<Cadastro> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

	
}