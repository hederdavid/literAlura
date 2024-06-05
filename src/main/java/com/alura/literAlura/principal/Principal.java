package com.alura.literAlura.principal;

import com.alura.literAlura.dto.AutorDTO;
import com.alura.literAlura.model.Autor;
import com.alura.literAlura.model.Livro;
import com.alura.literAlura.model.Root;
import com.alura.literAlura.repository.AutorRepository;
import com.alura.literAlura.repository.LivroRepository;
import com.alura.literAlura.services.ConsumoAPI;
import com.alura.literAlura.services.ConverteDados;
import com.alura.literAlura.tabelas.AutorTabela;
import com.alura.literAlura.tabelas.LivroTabela;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final Scanner SCANNER = new Scanner(System.in);
    private final String ENDERECO = "https://gutendex.com/books?search=";
    private final ConsumoAPI CONSUMO_API = new ConsumoAPI();
    private final ConverteDados CONVERTE_DADOS = new ConverteDados();

    public Principal(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }
    public void exibirInformacoes() {
        int opcao = -1;
        do {
            System.out.println("\n***************************");
            System.out.println("*       Biblioteca        *");
            System.out.println("***************************");
            System.out.println("1 - Buscar livro pelo título");
            System.out.println("2 - Listar livros registrados");
            System.out.println("3 - Listar autores registrados");
            System.out.println("4 - Listar autores vivos em um determinado ano");
            System.out.println("5 - Listar livros em um determinado idioma");
            System.out.println("0 - Sair");
            System.out.println("***************************");
            System.out.print("Escolha uma opção: ");
            opcao = SCANNER.nextInt();
            SCANNER.nextLine();

            switch (opcao) {
                case 1 -> buscarLivroPeloTitulo();
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivos();
                case 5 -> listarLivrosIdioma();
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void buscarLivroPeloTitulo() {
        System.out.print("Insira o nome do livro que você deseja cadastrar: ");
        String livro = SCANNER.nextLine().replace(" ", "%20").toLowerCase();
        var json = CONSUMO_API.obterDados(ENDERECO + livro);
        salvarDados(json);

    }

    private void listarLivrosRegistrados() {
        List<LivroTabela> livros = livroRepository.findAll();
        livros.forEach(livro -> System.out.println("********** LIVRO **********" +
                        "\nTítulo: " + livro.getNome() +
                        "\nAutor: " + livro.getAutor().getNome() +
                        "\nIdioma: " + livro.getIdioma() +
                        "\nDownloads: " + livro.getNumeroDeDownloads() + "\n"
                ));
    }

    private void listarAutoresRegistrados() {
        List<AutorTabela> autores = autorRepository.findAll();
        autores.forEach(a -> System.out.println("*********** AUTOR ***********" +
                "\nNome: " + a.getNome() +
                "\nAno de nascimento: " + a.getAnoNascimento() +
                "\nAno de falecimento: " + a.getAnoFalecimento() + "\n"));
    }

    public void listarAutoresVivos() {
        System.out.print("Insira o ano: ");
        int ano = SCANNER.nextInt();
        List<AutorTabela> autoresTabela = autorRepository.buscarAutoresVivos(ano);
        autoresTabela.stream()
                .map(this::converterParaDTO)
                .forEach(System.out::println);
    }

    private AutorDTO converterParaDTO(AutorTabela autorTabela) {
        return new AutorDTO(
                autorTabela.getNome(),
                autorTabela.getAnoNascimento(),
                autorTabela.getAnoFalecimento()
        );
    }

    private void listarLivrosIdioma() {
        System.out.print("Insira o idioma: ");
        String idioma = SCANNER.nextLine();
        List<LivroTabela> livros = livroRepository.findByIdioma(idioma);
        livros.forEach(livro -> System.out.println("********** LIVRO **********" +
                "\nTítulo: " + livro.getNome() +
                "\nAutor: " + livro.getAutor().getNome() +
                "\nIdioma: " + livro.getIdioma() +
                "\nDownloads: " + livro.getNumeroDeDownloads() + "\n"
        ));
    }


    private List<Livro> converterDados(String json) {
        Root root = CONVERTE_DADOS.obterDados(json, Root.class);
        System.out.println(root.resultado());
        return root.resultado();
    }

    private void salvarDados(String json) {
        List<Livro> livros = converterDados(json);
        Autor autorExistente = autorRepository.findByNome(livros.get(0).autores().get(0).nome());
        AutorTabela autorTabela;

        if (autorExistente != null) {
            autorTabela = new AutorTabela(autorExistente);
        } else {
            Autor autorNovo = new Autor(
                    livros.get(0).autores().get(0).nome(),
                    livros.get(0).autores().get(0).anoNascimento(),
                    livros.get(0).autores().get(0).anoFalecimento()
            );
            autorTabela = new AutorTabela(autorNovo);
            autorRepository.save(autorTabela);
        }

        LivroTabela livroTabela = new LivroTabela(livros.get(0));
        livroTabela.setAutor(autorTabela);
        livroRepository.save(livroTabela);
    }

}
