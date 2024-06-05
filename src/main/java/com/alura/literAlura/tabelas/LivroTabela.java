package com.alura.literAlura.tabelas;

import com.alura.literAlura.model.Autor;
import com.alura.literAlura.model.Livro;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "livros")
public class LivroTabela {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;

    @ManyToOne
    private AutorTabela autor;

    private String idioma;
    private int numeroDeDownloads;

    public LivroTabela() {}

    public LivroTabela(Livro livro) {
        this.nome = livro.titulo();
        this.autor = new AutorTabela(livro.autores().get(0));
        this.idioma = livro.idioma().get(0);
        this.numeroDeDownloads = livro.downloads();
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getNumeroDeDownloads() {
        return numeroDeDownloads;
    }

    public void setNumeroDeDownloads(int numeroDeDownloads) {
        this.numeroDeDownloads = numeroDeDownloads;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public AutorTabela getAutor() {
        return autor;
    }

    public void setAutor(AutorTabela autor) {
        this.autor = autor;
    }
}
