/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.prueba;

import ec.edu.monster.modelo.EurekaServiceModel;
import ec.edu.monster.ws.Movimiento;
import java.util.List;

/**
 *
 * @author ckan1
 */
public class PruebaMovimiento {
    public static void main(String[] args){
        try {
            //dato de la prueba
            String cuenta = "00100002";
            //proceso
            EurekaServiceModel service = new EurekaServiceModel();
            List<Movimiento> lista = service.traerMovimientos(cuenta);
            //reporte
            for(Movimiento r : lista){
                System.out.println(r.getCuenta()+ " - " + r.getNromov()+ " - "+ r.getFecha()+ " - "+ r.getTipo()+ " - " + r.getAccion() + " - " + r.getImporte());
            }
        } catch (Exception e){
            e.printStackTrace();
        } 
    }
}
