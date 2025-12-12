/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.controlador;

import ec.edu.monster.modelo.EurekaServiceModel;
import ec.edu.monster.view.ConsoleView;
import ec.edu.monster.util.util;
import ec.edu.monster.ws.Movimiento;
import java.util.List;
/**
 *
 * @author ckan1
 */
public class ConsoleControler {
    private final ConsoleView view = new ConsoleView();
    private final EurekaServiceModel model = new EurekaServiceModel();
    private static final String VALID_USER = "MONSTER";
    private static final String VALID_PASSWORD_HASH = util.hashPassword("MONSTER9");

    public void start() {
        if (authenticateUser()) {
            mainMenu();
        } else {
            view.showExitMessage();
        }
    }

    private boolean authenticateUser() {
        for (int attempts = 3; attempts > 0; attempts--) {
            view.showLoginPrompt();
            String user = view.getInput();
            view.showPasswordPrompt();
            String password = view.getInput();

            if (VALID_USER.equals(user) && VALID_PASSWORD_HASH.equals(util.hashPassword(password))) {
                return true;
            } else {
                view.showInvalidLogin();
            }
        }
        return false;
    }

    private void mainMenu() {
        while (true) {
            view.showMenu();
            String option = view.getInput();

            switch (option) {
                case "1":
                    handleOperation("deposito");
                    break;
                case "2":
                    handleOperation("retiro");
                    break;
                case "3":
                    handleOperation("transferencia");
                    break;
                case "4":
                    consultMovements();
                    break;
                case "5":
                    view.showExitMessage();
                    return;
                default:
                    view.showError("Opción no válida.");
            }
        }
    }

    private void handleOperation(String operationType) {
        try {
            if ("transferencia".equals(operationType)) {
                // Solicitar cuenta de origen y destino
                System.out.println("\n==== Operación ====");
                System.out.print("Ingrese la cuenta de origen: ");
                String sourceAccount = view.getInput();

                System.out.print("Ingrese la cuenta de destino: ");
                String destinationAccount = view.getInput();

                view.showAmountPrompt();
                double amount = Double.parseDouble(view.getInput());

                if (amount <= 0) {
                    view.showError("El monto debe ser positivo.");
                    return;
                }

                // Llamar al modelo para realizar la transferencia
                int result = model.regTransferencia(sourceAccount, destinationAccount, amount);
                
                if (result == 1) {
                    view.showSuccessMessage(operationType);
                } else if (result == -1) {
                    System.out.println("La cuenta de origen no existe o no está activa.");
                } else if (result == -2) {
                    System.out.println("El saldo saldo insuficiente en la cuenta de origen para realizar la transferencia.");
                } else if (result == -2) {
                    System.out.println("La cuenta de destino no existe o no está activa.");
                }                else {
                    view.showFailureMessage(operationType);
                }
            } else {
                // Operaciones existentes
                view.showDepositPrompt();
                String account = view.getInput();
                view.showAmountPrompt();
                double amount = Double.parseDouble(view.getInput());

                if (amount <= 0) {
                    view.showError("El monto debe ser positivo.");
                    return;
                }

                int result;
                switch (operationType) {
                    case "deposito":
                        result = model.regDeposito(account, amount);
                        break;
                    case "retiro":
                        result = model.regRetiro(account, amount);
                        break;
                    default:
                        result = 0;
                        break;
                }

                if (result == 1) {
                    view.showSuccessMessage(operationType);
                } else if (result == -1) {
                    System.out.println("La cuenta no existe o no esta activa");
                } else if (result == -2) {
                    System.out.println("El saldo no es suficiente para realizar la operación.");
                }else {
                    view.showFailureMessage(operationType);
                }
            }
        } catch (NumberFormatException e) {
            view.showError("El monto ingresado no es válido.");
        } catch (Exception e) {
            view.showError("Error al procesar la operación: " + e.getMessage());
        }
    }

    private void consultMovements() {
        try {
            view.showAccountPrompt();
            String account = view.getInput();
            List<Movimiento> movements = model.traerMovimientos(account);

            if (movements.isEmpty()) {
                view.showError("No se encontraron movimientos.");
            } else {
                StringBuilder sb = new StringBuilder();
                movements.forEach(mov -> sb.append(String.format("Cuenta: %s, Tipo: %s, Acción: %s  Monto: %.2f, Fecha: %s%n",
                        mov.getCuenta(), mov.getTipo(), mov.getAccion(), mov.getImporte(), mov.getFecha())));
                view.showMovements(sb.toString());
            }
        } catch (Exception e) {
            view.showError("Error al consultar movimientos: " + e.getMessage());
        }
    }
}
