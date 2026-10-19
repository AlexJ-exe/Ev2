package cl.alex.ev2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegistroActivity extends AppCompatActivity {

    private EditText etTitulo, etDescripcion, etFechaHora, etLatitud, etLongitud;
    private Spinner spCategoria;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Las referencias
        etTitulo = findViewById(R.id.etTitulo);
        etDescripcion = findViewById(R.id.etDescripcion);
        etFechaHora = findViewById(R.id.etFechaHora);
        etLatitud = findViewById(R.id.etLatitud);
        etLongitud = findViewById(R.id.etLongitud);
        spCategoria = findViewById(R.id.spCategoria);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Cargar Categoríass
        String[] categorias = {"Seleccione categoría", "A", "B", "C", "D", "E"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spCategoria.setAdapter(adapter);

        // Prellenar Fecha y Hora
        String fechaActual = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
        etFechaHora.setText(fechaActual);

        // Para Guardar
        btnGuardar.setOnClickListener(v -> validarYGuardar());
    }

    private void validarYGuardar() {
        String titulo = etTitulo.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();
        String fechaHora = etFechaHora.getText().toString().trim();
        String latitud = etLatitud.getText().toString().trim();
        String longitud = etLongitud.getText().toString().trim();
        int categoriaPos = spCategoria.getSelectedItemPosition();

        // Validacion
        if (titulo.isEmpty()) {
            etTitulo.setError("El título es obligatorio");
            etTitulo.requestFocus();
            return;
        }

        if (descripcion.isEmpty()) {
            etDescripcion.setError("La descripción es obligatoria");
            etDescripcion.requestFocus();
            return;
        }

        if (categoriaPos == 0) {
            Toast.makeText(this, "Por favor seleccione una categoría", Toast.LENGTH_SHORT).show();
            return;
        }

        if (fechaHora.isEmpty()) {
            etFechaHora.setError("La fecha/hora es obligatoria");
            etFechaHora.requestFocus();
            return;
        }

        if (latitud.isEmpty()) {
            etLatitud.setError("La latitud es obligatoria");
            etLatitud.requestFocus();
            return;
        }

        if (longitud.isEmpty()) {
            etLongitud.setError("La longitud es obligatoria");
            etLongitud.requestFocus();
            return;
        }

        // Si todos es válido
        Toast.makeText(this, "Campos validados correctamente", Toast.LENGTH_SHORT).show();
    }
}