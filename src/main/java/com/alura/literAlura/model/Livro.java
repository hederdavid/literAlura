package com.alura.literAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Livro(@JsonAlias("title") String titulo,
                    @JsonAlias("authors")List<Autor> autores,
                    @JsonAlias("languages") List<String> idioma,
                    @JsonAlias("download_count") int downloads) {

    @Override
    public String toString() {
        System.out.println();
        System.out.println("************* LIVRO *************");
        System.out.println();
        String autoresNomes = autores.stream()
                .map(Autor::nome)
                .collect(Collectors.joining(", "));
        String idiomas = String.join(", ", idioma);
        return String.format("""
                Título: %s
                Autor(es): %s
                Idioma(s): %s
                Número de downloads: %d
                """, titulo, autoresNomes, idiomas, downloads);
    }
}
