package ConversorDeMonedas.servicios;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServicioDeArchivos {

    // Guarda una conversión en archivo JSON
    public void guardarConversion(String monedaOrigen, String monedaDestino, 
                                double cantidad, double resultado) {

        String nombreArchivo = "resultadoConsulta.json";    // Archivo de salida
        LocalDateTime ahora = LocalDateTime.now();          // Fecha-hora actual

        // Formateador para la fecha (ISO-like)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Bloque try-with-resources (cierra automáticamente el FileWriter)
        try (FileWriter writer = new FileWriter(nombreArchivo, true)) {
            String json = String.format("{\n" +
                    "  \"fecha\": \"%s\",\n" +          // Timestamp
                    "  \"monedaOrigen\": \"%s\",\n" +   // Moneda origen
                    "  \"cantidad\": %.2f,\n" +         // Cantidad formateada
                    "  \"monedaDestino\": \"%s\",\n" +  // Moneda destino
                    "  \"resultado\": %.2f\n" +         // Resultado formateado
                    "}\n", 
                    ahora.format(formatter), monedaOrigen, cantidad, monedaDestino, resultado);
            
            writer.write(json);         // Escribe el registro JSON
            writer.write("\n");     // Separador para múltiples entradas
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo: " + e.getMessage());
        }
    }
}