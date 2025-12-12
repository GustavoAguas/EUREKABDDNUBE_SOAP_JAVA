package ec.edu.monster.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ec.edu.monster.R;
import ec.edu.monster.controlador.LoginControlador;

public class MainActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button btnLogin;
    private ProgressBar progressBar;
    private LoginControlador loginController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginController = new LoginControlador();

        initializeViews();
        setupLoginButton();
    }
    private void initializeViews() {
        editTextUsername = findViewById(R.id.txtUsername);
        editTextPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupLoginButton() {
        btnLogin.setOnClickListener(v -> performLogin());
    }

    private void performLogin() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mostrar progreso
        btnLogin.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        Boolean isLoggedIn = loginController.login(username, password);
        progressBar.setVisibility(View.GONE);
        btnLogin.setEnabled(true);

        if (isLoggedIn) {
            // Login exitoso
            Toast.makeText(MainActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
            // Aquí puedes iniciar la siguiente actividad
            startActivity(new Intent(MainActivity.this, MenuActivity.class));
            finish();
        } else {
            // Credenciales incorrectas
            Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
    }

}