package ec.edu.monster.vista;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ec.edu.monster.R;
import ec.edu.monster.controlador.EurekabankControlador;

public class RetiroActivity extends AppCompatActivity {

    private EditText txtCuenta;
    private EditText txtImporte;
    private Button btnProcesar;
    private Button btnRegresar;
    private ProgressBar progressBar;
    private EurekabankControlador eurekabankControlador;
    private ExecutorService executor;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_retiro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inizializar();
        btnRegresar.setOnClickListener(v->{
            regresar();
        });

        btnProcesar.setOnClickListener(v->{
            procesar();
        });
    }

    private void procesar() {
        String cuenta = txtCuenta.getText().toString().trim();
        String importeStr = txtImporte.getText().toString().trim();

        if (cuenta.isEmpty() || importeStr.isEmpty()) {
            mostrarMensajeError("Por favor, complete todos los campos");
            return;
        }

        try {
            double importe = Double.parseDouble(importeStr);

            // Mostrar ProgressBar
            ProgressBar progressBar = findViewById(R.id.progressBar);
            Button btnProcesar = findViewById(R.id.btnProcesar);

            progressBar.setVisibility(View.VISIBLE);
            btnProcesar.setEnabled(false);

            // Ejecutar en un hilo separado
            executor.execute(() -> {
                try {
                    String mensaje = eurekabankControlador.registrarRet(cuenta, importe);

                    // Actualizar UI en el hilo principal
                    handler.post(() -> {
                        progressBar.setVisibility(View.GONE);
                        btnProcesar.setEnabled(true);
                        mostrarMensajeExito(mensaje);
                    });
                } catch (Exception e) {
                    // Manejar errores en el hilo principal
                    handler.post(() -> {
                        progressBar.setVisibility(View.GONE);
                        btnProcesar.setEnabled(true);
                        mostrarMensajeError("Error al procesar: " + e.getMessage());
                    });
                }
            });

        } catch (NumberFormatException e) {
            mostrarMensajeError("Ingrese un importe válido");
        }
    }

    public void inizializar(){
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        eurekabankControlador = new EurekabankControlador();
        btnProcesar = findViewById(R.id.btnProcesar);
        btnRegresar = findViewById(R.id.btnRegresar);
        txtCuenta = findViewById(R.id.txtCuenta);
        txtImporte = findViewById(R.id.txtImporte);
        progressBar = findViewById(R.id.progressBar);
    }
    public void regresar(){
        startActivity(new Intent(RetiroActivity.this, MenuActivity.class));
        finish();
    }
    private void mostrarMensajeExito(String mensaje) {
        new AlertDialog.Builder(this)
                .setTitle("Éxito")
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .show();
    }

    private void mostrarMensajeError(String error) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(error)
                .setPositiveButton("Aceptar", null)
                .show();
    }

}