package com.alura.literAlura.repository;

import com.alura.literAlura.model.Autor;
import com.alura.literAlura.tabelas.AutorTabela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<AutorTabela, UUID> {
    Autor findByNome(String nome);
    @Query("SELECT a FROM AutorTabela a WHERE (a.anoFalecimento IS NULL OR a.anoFalecimento > :ano) AND a.anoNascimento <= :ano")
    List<AutorTabela> buscarAutoresVivos(@Param("ano") int ano);

}
