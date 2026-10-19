package cl.alex.ev2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vincular
        Button btnRegistro = findViewById(R.id.btnRegistro);
        Button btnListado = findViewById(R.id.btnListado);
        Button btnMapa = findViewById(R.id.btnMapa);

        // Ir a Registro
        btnRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
            startActivity(intent);
        });

        // A Listado
        btnListado.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListadoActivity.class);
            startActivity(intent);
        });

        // Y a Mapa
        btnMapa.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapaActivity.class);
            startActivity(intent);
        });
    }
}