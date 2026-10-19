package cl.alex.ev2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegistroActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private EditText etTitulo, etDescripcion, etFechaHora, etLatitud, etLongitud;
    private Spinner spCategoria;
    private Button btnGuardar, btnObtenerUbicacion;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //  initializes
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Las referencias
        etTitulo = findViewById(R.id.etTitulo);
        etDescripcion = findViewById(R.id.etDescripcion);
        etFechaHora = findViewById(R.id.etFechaHora);
        etLatitud = findViewById(R.id.etLatitud);
        etLongitud = findViewById(R.id.etLongitud);
        spCategoria = findViewById(R.id.spCategoria);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnObtenerUbicacion = findViewById(R.id.btnObtenerUbicacion);

        // Cargar Categoríass
        String[] categorias = {"Seleccione categoría", "A", "B", "C", "D", "E"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spCategoria.setAdapter(adapter);

        // Prellenar Fecha y Hora
        String fechaActual = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
        etFechaHora.setText(fechaActual);

        // Evento botón GPS
        btnObtenerUbicacion.setOnClickListener(v -> solicitarPermisoYObtenerUbicacion());

        // Para Guardar
        btnGuardar.setOnClickListener(v -> validarYGuardar());
    }

    private void solicitarPermisoYObtenerUbicacion() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            obtenerUbicacionActual();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void obtenerUbicacionActual() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    etLatitud.setText(String.valueOf(location.getLatitude()));
                    etLongitud.setText(String.valueOf(location.getLongitude()));
                    Toast.makeText(RegistroActivity.this, "Ubicación obtenida con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistroActivity.this, "Active el GPS del dispositivo para obtener coordenadas", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUbicacionActual();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void validarYGuardar() {
        String titulo = etTitulo.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();
        String fechaHora = etFechaHora.getText().toString().trim();
        String latitud = etLatitud.getText().toString().trim();
        String longitud = etLongitud.getText().toString().trim();
        int categoriaPos = spCategoria.getSelectedItemPosition();

        if (titulo.isEmpty() || descripcion.isEmpty() || categoriaPos == 0 || fechaHora.isEmpty() || latitud.isEmpty() || longitud.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos obligatorios", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Datos y ubicación validados correctamente", Toast.LENGTH_SHORT).show();
    }
}