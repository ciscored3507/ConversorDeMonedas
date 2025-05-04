package ConversorDeMonedas.interfaz;

import ConversorDeMonedas.servicios.ConvertidorDeMoneda;
import ConversorDeMonedas.servicios.ServicioDeArchivos;

import java.util.Random;
import java.util.Scanner;

public class ConsolaUI {
    private ConvertidorDeMoneda convertidor;
    private ServicioDeArchivos archivoServicio;
    private Scanner scanner;
    private int intentosFallidos = 0;
    private final int MAX_INTENTOS = 3;
    // Metodo limpiar
    private void limpiarConsola() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            // Fallback: imprime 50 líneas vacías
            System.out.println("\n".repeat(50));
        }
    }
    
    public ConsolaUI() {
        this.convertidor = new ConvertidorDeMoneda();
        this.archivoServicio = new ServicioDeArchivos();
        this.scanner = new Scanner(System.in);
    }
    
    public void iniciar() {
        boolean salir = false;
        
        while (!salir) {
            try {
                System.out.println(" ************************************* ");
                System.out.println("|________Conversor de monedas_________|");
                System.out.println("|1. MXN - Peso mexicano               |");
                System.out.println("|2. ARS - Peso argentino              |");
                System.out.println("|3. BOB - Boliviano                   |");
                System.out.println("|4. BRL - Real brasileno              |");
                System.out.println("|5. CLP - Peso chileno                |");
                System.out.println("|6. COP - Peso colombiano             |");
                System.out.println("|7. USD - Dólar Estadounidense        |");
                System.out.println("|_____________________________________|");
                System.out.println("|X. Salir                             |");
                System.out.println("|_____________________________________|");
                System.out.print("Selecciona la moneda de entrada: ");
                
                String opcion = scanner.nextLine().trim();
                
                if (opcion.equalsIgnoreCase("X")) {
                    salir = true;
                    continue;
                }
                
                String monedaOrigen = obtenerMoneda(opcion);
                if (monedaOrigen == null) {
                    System.out.println("_______________________________________");
                    System.out.println("Opcion no valida. Intente de nuevo.");
                    continue;
                }

                //Limpia consola
                limpiarConsola();
                // Reiniciar contador al tener éxito
                intentosFallidos = 0;
                
                System.out.println("_______________________________________");
                System.out.println("Moneda Seleccionada: " + monedaOrigen + " - " + 
                                 convertidor.obtenerNombreMoneda(monedaOrigen));
                System.out.print("Ingresa la cantidad a convertir: ");
                
                double cantidad;
                try {
                    cantidad = Double.parseDouble(scanner.nextLine());
                    if (cantidad <= -1) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Cantidad no valida.\n-⚠\uFE0FDebe ser un numero positivo.\n-❗Las letras no son numeros.");
                    System.out.println("_______________________________________");
                    continue;
                }
                
                // Selección de moneda destino
                System.out.println(" ************************************* ");
                System.out.println("|________Conversor de monedas_________|");
                System.out.println("|1. MXN - Peso mexicano               |");
                System.out.println("|2. ARS - Peso argentino              |");
                System.out.println("|3. BOB - Boliviano                   |");
                System.out.println("|4. BRL - Real brasileno              |");
                System.out.println("|5. CLP - Peso chileno                |");
                System.out.println("|6. COP - Peso colombiano             |");
                System.out.println("|7. USD - Dólar Estadounidense        |");
                System.out.println("|_____________________________________|");
                System.out.println("|X. Salir                             |");
                System.out.println("|_____________________________________|");
                System.out.print("Selecciona la moneda de salida: ");
                
                opcion = scanner.nextLine().trim();
                
                if (opcion.equalsIgnoreCase("X")) {
                    salir = true;
                    continue;
                }
                
                String monedaDestino = obtenerMoneda(opcion);
                if (monedaDestino == null) {
                    System.out.println("Opcion no valida. Intente de nuevo.");
                    System.out.println("_______________________________________");
                    continue;
                }

                System.out.println("_______________________________________");
                System.out.println("Moneda Seleccionada: " + monedaDestino + " - " + 
                                 convertidor.obtenerNombreMoneda(monedaDestino));
                
                // Realizar conversión
                double resultado = convertidor.convertir(cantidad, monedaOrigen, monedaDestino);
                
                // Mostrar resultado


                // Primero verificamos si es conversión de 0 o misma moneda
                if (resultado == 0 || monedaOrigen.equals(monedaDestino)) {
                    System.out.println("_______________________________________");
                    System.out.println("En serio!!!");
                    System.out.printf("¿Quieres convertir %.2f %s (%s) a %s (%s)?\n",
                            cantidad,
                            monedaOrigen,
                            convertidor.obtenerNombreMoneda(monedaOrigen),
                            monedaDestino,
                            convertidor.obtenerNombreMoneda(monedaDestino));

                    if (resultado == 0) {
                        System.out.println("¿Seguro que necesitabas un programa para saber que 0 es igual a 0?");
                        System.out.println("Resultado: 0.0 (implementacion perfecta, pero ¿para qué?)");
                        System.out.println("(Al menos esta conversion nunca tendra problemas de redondeo)");
                    } else {
                        System.out.println("Se convirtio correctamente");
                        System.out.println("La app funciona, pero no era necesario");
                        System.out.println("Al menos ya sabes que tienen el mismo valor");
                    }
                    System.out.println("_______________________________________");
                } else {
                    // Mostrar resultado normal de conversión
                    System.out.println(" ************************************* ");
                    System.out.println("|_________Conversor de monedas________|");
                    System.out.println("|       ¡¡Conversión realizada!!      |");
                    System.out.println("|_____________________________________|");
                    System.out.printf("%s - %s : %.2f\n",
                            monedaOrigen,
                            convertidor.obtenerNombreMoneda(monedaOrigen),
                            cantidad);
                    System.out.printf("%s - %s : %.2f\n",
                            monedaDestino,
                            convertidor.obtenerNombreMoneda(monedaDestino),
                            resultado);
                    System.out.println("|_____________________________________|");
                }
                
                // Guardar en archivo
                archivoServicio.guardarConversion(monedaOrigen, monedaDestino, cantidad, resultado);

                System.out.println("Presiona enter para continuar.");
                System.out.println("X para salir.");
                System.out.println("_______________________________________");
                String continuar = scanner.nextLine().trim();
                if (continuar.equalsIgnoreCase("X")) {
                    salir = true;
                }
                
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Presiona enter para continuar.");
                System.out.println("_______________________________________");
                scanner.nextLine();
            }
        }

        scanner.close();
        System.out.println("_______________________________________");
        System.out.println("Gracias por usar el conversor de monedas. ¡Hasta pronto!");
        System.out.println("_______________________________________");
    }
    
    private String obtenerMoneda(String opcion) {
        switch (opcion) {
            case "1": return "MXN";
            case "2": return "ARS";
            case "3": return "BOB";
            case "4": return "BRL";
            case "5": return "CLP";
            case "6": return "COP";
            case "7": return "USD";
            default:
                intentosFallidos++;
                if (intentosFallidos >= MAX_INTENTOS) {
                    if (opcion.equals("42")){
                        System.out.println("Correcto! Pero 42 es la respuesta al universo,");
                        System.out.println("no a un conversor de monedas. Intenta con 1-7");
                    }else {
                        mostrarChisteProgramador();
                    }
                    intentosFallidos = 0; // Reiniciamos el contador
                }
                return null;
        }
    }
    private void mostrarChisteProgramador() {
        String[] chistes = {
                "Cuidado! Tu indecision podria causar un stack overflow.",
                "Hay 10 tipos de personas: las que saben binario y las que no.",
                "No eres indeciso... solo estas en un loop while(condicionNoClara).",
                "Mi codigo tiene 99 problemas, pero el indentado no es uno.",
                "No es que no sepas elegir, es que estas considerando todos los edge cases.",
                "Tus intentos son como mis commits: 'prueba 1', 'prueba 2', 'a ver ahora'..."
        };

        int chisteAleatorio = new Random().nextInt(chistes.length);

        System.out.println("\nUps! Parece que te cuesta elegir una opcion...");
        System.out.println("Aqui tienes un chiste para relajarte:");
        System.out.println(chistes[chisteAleatorio]);
        System.out.println("Intentalo de nuevo, esta vez con mas calma\n");
        System.out.println("_______________________________________");
    }
}