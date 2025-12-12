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

public class EurekabankService {
    String NAMESPACE =Config.NAMESPACE;
    String URL = Config.BASE_URL;

    public String registrarTransaccion(String cuenta, double importe, String method_name) {
        String SOAP_ACTION = NAMESPACE + method_name;
        try {
            // Crea el objeto de solicitud SOAP
            SoapObject request = new SoapObject(NAMESPACE, method_name);

            // Usar QName para los elementos
            PropertyInfo cuentaInfo = new PropertyInfo();
            cuentaInfo.setName("cuenta");
            cuentaInfo.setValue(cuenta);
            cuentaInfo.setType(String.class);
            request.addProperty(cuentaInfo);

            // Formatea el importe como String con punto decimal
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
            String importeFormatted = decimalFormat.format(importe);

            PropertyInfo importeInfo = new PropertyInfo();
            importeInfo.setName("importe");
            importeInfo.setValue(importeFormatted);
            importeInfo.setType(String.class);
            request.addProperty(importeInfo);

            // Configura el sobre SOAP para document/literal
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            envelope.dotNet = false;

            // Importante para servicios document/literal
            envelope.encodingStyle = null;

            // Configura el transporte HTTP
            HttpTransportSE transport = new HttpTransportSE(URL, 20000);

            // Llama al servicio con el soap action correcto
            transport.call(SOAP_ACTION, envelope);

            // Procesa la respuesta SOAP
            Object response = envelope.getResponse();
            if (response instanceof SoapPrimitive) {
                String estado = response.toString();
                if ("1".equals(estado)) {
                    return "¡Éxito! Proceso registrado exitosamente.";
                } else if ("-1".equals(estado)) {
                    return "Error: cuenta no existe o no está activa.";
                } else {
                    return "Estado desconocido: " + estado;
                }
            } else {
                return "Respuesta inesperada del servidor: " + response.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Imprime el stack trace completo para diagnóstico
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            Log.i("Error", sw.toString());
            return "Error detallado: " + sw.toString();
        }
    }
}
