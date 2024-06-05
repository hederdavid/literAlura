package com.alura.literAlura.repository;

import com.alura.literAlura.tabelas.LivroTabela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<LivroTabela, UUID> {
    @Query("SELECT l FROM LivroTabela l WHERE l.idioma = :idioma")
    List<LivroTabela> findByIdioma(@Param("idioma") String idioma);
}
