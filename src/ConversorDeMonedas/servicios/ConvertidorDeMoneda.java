package ConversorDeMonedas.servicios;

import ConversorDeMonedas.api.TipoDeCambioAPI;
import ConversorDeMonedas.modelo.RespuestaTipoDeCambio;
import com.google.gson.Gson;

// Servicio principal para conversión de monedas.
// Realiza cálculos utilizando tasas de cambio actualizadas desde una API externa.
public class ConvertidorDeMoneda {
    private TipoDeCambioAPI api;
    private Gson gson;

    // Constructor que inicializa las dependencias:
    // - Conexión a la API de tasas de cambio
    // - Parser GSON para manejar respuestas JSON
    
    public ConvertidorDeMoneda() {
        this.api = new TipoDeCambioAPI();
        this.gson = new Gson();
    }

    // Convierte una cantidad entre dos monedas usando tasas actualizadas.
    // El metodo realiza
    // 1. Consulta a la API para obtener tasas actuales
    // 2. Convierte primero a USD como moneda intermedia
    // 3. Aplica la tasa de cambio destino
    public double convertir(double cantidad, String monedaOrigen, String monedaDestino) throws Exception {
        // 1. Inicialización de tasas
        double tasaOrigen = 1.0;
        double tasaDestino = 1.0;

        // 2. Obtener tasas desde API
        String jsonResponse = api.obtenerTasasDeCambio(monedaOrigen);
        RespuestaTipoDeCambio respuesta = gson.fromJson(jsonResponse, RespuestaTipoDeCambio.class);
        
        if (!respuesta.getResultado().equals("success")) {
            throw new Exception("Error al obtener tasas de cambio");
        }

        // 3. Obtener tasa destino
        switch (monedaDestino) {
            case "USD":
                tasaDestino = respuesta.getTasasDeCambio().getUsd();
                break;
            case "MXN":
                tasaDestino = respuesta.getTasasDeCambio().getMxn();
                break;
            case "ARS":
                tasaDestino = respuesta.getTasasDeCambio().getArs();
                break;
            case "BOB":
                tasaDestino = respuesta.getTasasDeCambio().getBob();
                break;
            case "BRL":
                tasaDestino = respuesta.getTasasDeCambio().getBrl();
                break;
            case "CLP":
                tasaDestino = respuesta.getTasasDeCambio().getClp();
                break;
            case "COP":
                tasaDestino = respuesta.getTasasDeCambio().getCop();
                break;
            default:
                throw new Exception("Moneda destino no soportada");
        }

        // 4. Obtener tasa origen (si no es USD)
        if (!monedaOrigen.equals("USD")) {
            switch (monedaOrigen) {
                case "MXN":
                    tasaOrigen = respuesta.getTasasDeCambio().getMxn();
                    break;
                case "ARS":
                    tasaOrigen = respuesta.getTasasDeCambio().getArs();
                    break;
                case "BOB":
                    tasaOrigen = respuesta.getTasasDeCambio().getBob();
                    break;
                case "BRL":
                    tasaOrigen = respuesta.getTasasDeCambio().getBrl();
                    break;
                case "CLP":
                    tasaOrigen = respuesta.getTasasDeCambio().getClp();
                    break;
                case "COP":
                    tasaOrigen = respuesta.getTasasDeCambio().getCop();
                    break;
                default:
                    throw new Exception("Moneda origen no soportada");
            }
        }

        // 5. Cálculo final (convertir a USD primero)
        double cantidadEnUsd = cantidad / tasaOrigen;
        return cantidadEnUsd * tasaDestino;
    }

    // 6. Obtiene el nombre completo de una moneda
    public String obtenerNombreMoneda(String codigoMoneda) {
        switch (codigoMoneda) {
            case "USD": return "Dolar Estadounidense";
            case "MXN": return "Peso mexicano";
            case "ARS": return "Peso argentino";
            case "BOB": return "Boliviano";
            case "BRL": return "Real brasileno";
            case "CLP": return "Peso chileno";
            case "COP": return "Peso colombiano";
            default: return "Desconocida";
        }
    }
}