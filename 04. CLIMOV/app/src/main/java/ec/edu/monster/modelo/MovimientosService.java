package ec.edu.monster.modelo;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MovimientosService {
    String NAMESPACE = Config.NAMESPACE;
    String URL = Config.BASE_URL;
    String METHOD_NAME = "traerMovimientos";
    String SOAP_ACTION = NAMESPACE + METHOD_NAME;

    public List<Movimiento> obtenerMovimientos(String cuenta) {
        List<Movimiento> movimientos = new ArrayList<>();

        try {
            // Crea el objeto de solicitud SOAP
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cuenta", cuenta);

            // Configura el sobre SOAP
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            envelope.dotNet = false;

            // Configura el transporte HTTP
            HttpTransportSE transport = new HttpTransportSE(URL, 20000);
            transport.call(SOAP_ACTION, envelope);

            // Procesa la respuesta SOAP
            Object response = envelope.getResponse();

            if (response instanceof Vector) {
                // Si es un Vector, iterar sobre los elementos
                Vector<?> vectorResponse = (Vector<?>) response;
                for (Object obj : vectorResponse) {
                    SoapObject movimientoObj = (SoapObject) obj;
                    Movimiento movimiento = parseMovimiento(movimientoObj);
                    if (movimiento != null) {
                        movimientos.add(movimiento);
                    }
                }
            } else if (response instanceof SoapObject) {
                // Si es un único objeto, procesarlo directamente
                SoapObject movimientoObj = (SoapObject) response;
                Movimiento movimiento = parseMovimiento(movimientoObj);
                if (movimiento != null) {
                    movimientos.add(movimiento);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movimientos;
    }

    // Método auxiliar para parsear un SoapObject en un Movimiento
    private Movimiento parseMovimiento(SoapObject movimientoObj) {
        try {
            return new Movimiento(
                    movimientoObj.getProperty("cuenta").toString(),
                    Integer.parseInt(movimientoObj.getProperty("nromov").toString()),
                    movimientoObj.getProperty("fecha").toString(),
                    movimientoObj.getProperty("tipo").toString(),
                    movimientoObj.getProperty("accion").toString(),
                    Double.parseDouble(movimientoObj.getProperty("importe").toString())
            );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ParseMovimiento", "Error al parsear el movimiento: " + e.getMessage());
            return null;
        }
    }

}
