package com.alura.literAlura.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    private final HttpClient client = HttpClient.newHttpClient();

    public String obterDados(String endereco) {
        try {
            URI uri = new URI(endereco);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if (statusCode == 301) {
                String redirectUrl = response.headers().firstValue("Location").orElse("");
                System.out.println("Redirecionado para: " + redirectUrl);

                URI novaUri = uri.resolve(redirectUrl);

                request = HttpRequest.newBuilder()
                        .uri(novaUri)
                        .build();

                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }
            return response.body();

        } catch (IOException | InterruptedException | URISyntaxException e) {
            System.err.println("Erro ao fazer a requisição HTTP: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
