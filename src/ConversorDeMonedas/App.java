// 1. Crea una instancia de la clase ConsolaUI (Interfaz de Usuario por Consola)
// 2. Invoca el metodo iniciar() para comenzar el flujo del programa
package ConversorDeMonedas;

import ConversorDeMonedas.interfaz.ConsolaUI;

public class App {
    public static void main(String[] args) {
        // Instancia la interfaz de usuario por consola
        ConsolaUI interfaz = new ConsolaUI();
        // Inicia el flujo principal de la aplicación
        interfaz.iniciar();
    }
}