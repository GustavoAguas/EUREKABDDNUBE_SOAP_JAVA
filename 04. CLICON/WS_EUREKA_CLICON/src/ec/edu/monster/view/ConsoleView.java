/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.view;

import java.util.Scanner;

/**
 *
 * @author ckan1
 */
public class ConsoleView {
    private final Scanner scanner = new Scanner(System.in);

    public void showLoginPrompt() {
        System.out.println("==== Login ====");
        System.out.print("Usuario: ");
    }

    public void showPasswordPrompt() {
        System.out.print("Contraseña: ");
    }

    public void showMenu() {
        System.out.println("\n==== Menú ====");
        System.out.println("1. Registrar Depósito");
        System.out.println("2. Registrar Retiro");
        System.out.println("3. Registrar Transferencia");
        System.out.println("4. Consultar Movimientos");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public void showInvalidLogin() {
        System.out.println("Usuario o contraseña incorrectos. Intente de nuevo.");
    }

    public void showExitMessage() {
        System.out.println("¡Gracias por usar el sistema!");
    }

    public void showDepositPrompt() {
        System.out.println("\n==== Operación ====");
        System.out.print("Ingrese número de cuenta: ");
    }

    public void showAmountPrompt() {
        System.out.print("Ingrese el importe: ");
    }

    public void showSuccessMessage(String operationType) {
        System.out.println(operationType.substring(0, 1).toUpperCase() + operationType.substring(1) + " registrado exitosamente.");
    }

    public void showFailureMessage(String operationType) {
        System.out.println("Error al registrar " + operationType + ".");
    }

    public void showAccountPrompt() {
        System.out.print("Ingrese número de cuenta para consultar movimientos: ");
    }

    public void showMovements(String movimientos) {
        System.out.println("\n==== Movimientos ====");
        System.out.println(movimientos);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public String getInput() {
        return scanner.nextLine();
    }
}