package ec.edu.monster.controlador;

import ec.edu.monster.modelo.EurekabankService;
import ec.edu.monster.modelo.TransferenciaService;

public class EurekabankControlador {
    private final EurekabankService eurekabankService;
    private final TransferenciaService transferenciaService;

    public EurekabankControlador() {
        this.eurekabankService = new EurekabankService();
        this.transferenciaService = new TransferenciaService();
    }

    public String registrarDep(String cuenta, double importe) {
        String estado = eurekabankService.registrarTransaccion(cuenta, importe,"regDeposito");
        if (estado != null) {
            return estado;
        }
        return "Error, en con el servicio.";
    }
    public String registrarRet(String cuenta, double importe) {
        String estado = eurekabankService.registrarTransaccion(cuenta, importe,"regRetiro");
        if (estado != null) {
            return estado;
        }
        return "Error, en con el servicio.";
    }

    public String registrarTrans(String cuenta,String cuentaDestino, double importe) {
        String estado = transferenciaService.registrarTransaccion(cuenta,cuentaDestino, importe,"regTransferencia");
        if (estado != null) {
            return estado;
        }
        return "Error, en con el servicio.";
    }

}
