package ec.edu.monster.vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ec.edu.monster.R;

public class MenuActivity extends AppCompatActivity {
    private Button btnSalir;
    private Button btnDeposito;
    private Button btnConsultar;
    private Button btnTransferir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inizializar();
        btnDeposito.setOnClickListener(v->{
            irDeposito();
        });
        btnConsultar.setOnClickListener(v->{
            irConsulta();
        });

        btnSalir.setOnClickListener(v->{
            salir();
        });
        btnTransferir.setOnClickListener(v->{
            irTransferir();
        });

    }

    public void inizializar(){
        btnSalir = findViewById(R.id.btnSalir);
        btnDeposito = findViewById(R.id.btnDeposito);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnTransferir = findViewById(R.id.btnTransferir);
    }

    public void irDeposito(){
        startActivity(new Intent(MenuActivity.this, DepositoActivity.class));
        finish();
    }

    public void irConsulta(){
        startActivity(new Intent(MenuActivity.this, MovimientoActivity.class));
        finish();
    }

    public void irTransferir(){
        startActivity(new Intent(MenuActivity.this, TransferenciaActivity.class));
        finish();
    }

    public void salir(){
        startActivity(new Intent(MenuActivity.this, MainActivity.class));
        finish();
    }
}