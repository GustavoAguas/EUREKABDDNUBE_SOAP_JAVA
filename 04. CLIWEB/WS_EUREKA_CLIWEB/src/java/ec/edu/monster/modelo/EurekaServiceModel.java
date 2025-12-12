/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.modelo;

/**
 *
 * @author ckan1
 */
public class EurekaServiceModel {

    public int regDeposito(java.lang.String cuenta, double importe) {
        ec.edu.monster.ws.WSEureka_Service service = new ec.edu.monster.ws.WSEureka_Service();
        ec.edu.monster.ws.WSEureka port = service.getWSEurekaPort();
        return port.regDeposito(cuenta, importe);
    }

    public int regRetiro(java.lang.String cuenta, double importe) {
        ec.edu.monster.ws.WSEureka_Service service = new ec.edu.monster.ws.WSEureka_Service();
        ec.edu.monster.ws.WSEureka port = service.getWSEurekaPort();
        return port.regRetiro(cuenta, importe);
    }

    public int regTransferencia(java.lang.String cuentaOrigen, java.lang.String cuentaDestino, double importe) {
        ec.edu.monster.ws.WSEureka_Service service = new ec.edu.monster.ws.WSEureka_Service();
        ec.edu.monster.ws.WSEureka port = service.getWSEurekaPort();
        return port.regTransferencia(cuentaOrigen, cuentaDestino, importe);
    }

    public java.util.List<ec.edu.monster.ws.Movimiento> traerMovimientos(java.lang.String cuenta) {
        ec.edu.monster.ws.WSEureka_Service service = new ec.edu.monster.ws.WSEureka_Service();
        ec.edu.monster.ws.WSEureka port = service.getWSEurekaPort();
        return port.traerMovimientos(cuenta);
    }

    
}
