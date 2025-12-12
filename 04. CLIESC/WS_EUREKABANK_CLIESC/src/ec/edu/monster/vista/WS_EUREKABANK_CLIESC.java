package ec.edu.monster.vista;

import javax.swing.SwingUtilities;

public class WS_EUREKABANK_CLIESC {

    public static void main(String[] args) {
        // Ejecutar GUI en el hilo correcto
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Cambiar el look and feel a uno más bonito (opcional)
                    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                } catch (Exception ex) {
                    // Si falla, sigue con el default
                }

                // Crear y mostrar la ventana de login
                VistaLogin login = new VistaLogin();
                login.setLocationRelativeTo(null);  // Centrar en pantalla
                login.setVisible(true);
            }
        });
    }
}