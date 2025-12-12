/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.prueba;

import ec.edu.monster.modelo.EurekaServiceModel;

/**
 *
 * @author ckan1
 */
public class PruebaDesposito {
    public static void main(String[] args) {
        try {
            String cuenta = "00100001";
            double importe = 20;
            EurekaServiceModel service = new EurekaServiceModel();
            int result = service.regDeposito(cuenta, importe);
            System.err.println(result);
            System.out.println("Proceso ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
