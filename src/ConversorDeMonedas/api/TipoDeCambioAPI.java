package ConversorDeMonedas.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TipoDeCambioAPI {
    private static final String API_KEY = "c6ef9ba4bf33cd45dd71b0e6";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public String obtenerTasasDeCambio(String monedaBase) throws IOException, InterruptedException {
        String url = BASE_URL + API_KEY + "/latest/" + monedaBase;
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al conectar con la API: " + response.statusCode());
        }
        
        return response.body();
    }
}