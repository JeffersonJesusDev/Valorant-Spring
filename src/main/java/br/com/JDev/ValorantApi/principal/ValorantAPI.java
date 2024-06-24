package br.com.JDev.ValorantApi.principal;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ValorantAPI {

    public String obterDados(String endereco) {
        try {

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endereco)).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Falha na requisição: Código de status " + response.statusCode());
            }
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao fazer requisição HTTP: " + e.getMessage(), e);
        }
    }
}