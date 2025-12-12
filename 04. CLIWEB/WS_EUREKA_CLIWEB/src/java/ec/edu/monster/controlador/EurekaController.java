/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.controlador;

import ec.edu.monster.modelo.EurekaServiceModel;
import ec.edu.monster.util.util;
import ec.edu.monster.ws.Movimiento;
import java.util.List;

/**
 *
 * @author ckan1
 */
public class EurekaController {
    private static final String USUARIO_PREDEFINIDO = "MONSTER";
    private static final String PASSWORD_HASH_PREDEFINIDO = util.hashPassword("MONSTER9");
    
    public List<Movimiento> traerMovimientos(String cuenta){
        EurekaServiceModel service = new EurekaServiceModel();
        return service.traerMovimientos(cuenta);
    }
    
    public int regDeposito(String cuenta, double importe){
        EurekaServiceModel service = new EurekaServiceModel();
        return service.regDeposito(cuenta, importe);
    }
    
    public int regRetiro(String cuenta, double importe){
        EurekaServiceModel service = new EurekaServiceModel();
        return service.regRetiro(cuenta, importe);
    }
    
    public int regTransferencia(String cuentaOrigen, String cuentaDestino, double importe){
        EurekaServiceModel service = new EurekaServiceModel();
        return service.regTransferencia(cuentaOrigen,cuentaDestino , importe);
    }
    
    public boolean autenticarUsuario(String usuario, String passwordIngresada) {
        // Verifica si el usuario coincide
        if (!USUARIO_PREDEFINIDO.equals(usuario)) {
            return false;
        }

        // Hash de la contraseña ingresada
        String hashedInputPassword = util.hashPassword(passwordIngresada);

        // Compara el hash ingresado con el quemado
        return hashedInputPassword.equals(PASSWORD_HASH_PREDEFINIDO);
    }
}
