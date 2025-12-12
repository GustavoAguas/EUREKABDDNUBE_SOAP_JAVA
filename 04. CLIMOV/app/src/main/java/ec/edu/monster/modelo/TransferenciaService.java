package ec.edu.monster.modelo;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class TransferenciaService {
    private static final String NAMESPACE = Config.NAMESPACE;
    private static final String URL = Config.BASE_URL;

    public String registrarTransaccion(String cuentaOrigen, String cuentaDestino, double importe, String method_name) {
        String SOAP_ACTION = NAMESPACE + method_name;

        try {
            // Crea el objeto de solicitud SOAP
            SoapObject request = new SoapObject(NAMESPACE, method_name);

            // Agrega las propiedades al objeto SOAP
            request.addProperty("cuentaOrigen", cuentaOrigen);
            request.addProperty("cuentaDestino", cuentaDestino);
            request.addProperty("importe", String.valueOf(importe));

            // Configura el sobre SOAP
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            envelope.dotNet = false; // Cambia a true si el servicio SOAP es .NET

            // Configura el transporte HTTP
            HttpTransportSE transport = new HttpTransportSE(URL, 20000);

            // Llama al servicio con el SOAP_ACTION correcto
            transport.call(SOAP_ACTION, envelope);

            // Procesa la respuesta SOAP
            Object response = envelope.getResponse();
            if (response instanceof SoapPrimitive) {
                String estado = response.toString();
                switch (estado) {
                    case "1":
                        return "¡Éxito! Proceso registrado exitosamente.";
                    case "-1":
                        return "Error: cuenta no existe o no está activa.";
                    default:
                        return "Estado desconocido: " + estado;
                }
            } else {
                return "Respuesta inesperada del servidor: " + response.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Imprime el error detallado para diagnóstico
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            Log.e("Error", sw.toString());
            return "Error al procesar la solicitud: " + sw.toString();
        }
    }
}
