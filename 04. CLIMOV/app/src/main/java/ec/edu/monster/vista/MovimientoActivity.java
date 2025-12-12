package ec.edu.monster.vista;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ec.edu.monster.R;
import ec.edu.monster.controlador.MovimientosAdapter;
import ec.edu.monster.modelo.Movimiento;
import ec.edu.monster.modelo.MovimientosService;

public class MovimientoActivity extends AppCompatActivity {
    private RecyclerView rvMovimientos;
    private MovimientosService movimientosService;
    private ExecutorService executor;
    private Handler handler;
    private EditText txtCuenta;
    private Button btnConsultar;
    private Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnRegresar = findViewById(R.id.btnRegresar);
        txtCuenta = findViewById(R.id.txtCuenta);
        rvMovimientos = findViewById(R.id.rvMovimientos);
        rvMovimientos.setLayoutManager(new LinearLayoutManager(this));

        movimientosService = new MovimientosService();
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        btnConsultar.setOnClickListener(v->{
            String cuenta = txtCuenta.getText().toString().trim();
            cargarMovimientos(cuenta);
        });
        btnRegresar.setOnClickListener(v->{
            regresar();
        });

    }

    public void regresar(){
        startActivity(new Intent(MovimientoActivity.this, MenuActivity.class));
        finish();
    }

    private void cargarMovimientos(String cuenta) {
        executor.execute(() -> {
            List<Movimiento> movimientos = movimientosService.obtenerMovimientos(cuenta);

            handler.post(() -> {
                if (!movimientos.isEmpty()) {
                    MovimientosAdapter adapter = new MovimientosAdapter(movimientos);
                    rvMovimientos.setAdapter(adapter);
                } else {
                    rvMovimientos.setAdapter(null);
                    Toast.makeText(this, "No se encontraron movimientos", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}