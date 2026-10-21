package cl.alex.ev2;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor acelerometro;
    private TextView tvSensorValores, tvSensorEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vincular vistas
        Button btnRegistro = findViewById(R.id.btnRegistro);
        Button btnListado = findViewById(R.id.btnListado);
        Button btnMapa = findViewById(R.id.btnMapa);
        tvSensorValores = findViewById(R.id.tvSensorValores);
        tvSensorEstado = findViewById(R.id.tvSensorEstado);

        // Navegación
        btnRegistro.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RegistroActivity.class)));
        btnListado.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ListadoActivity.class)));
        btnMapa.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapaActivity.class)));

        // Sensor Acelerómetro
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        // Si el telefono no tiene el sensor bla bla
        if (acelerometro == null) {
            tvSensorValores.setText("N/A");
            tvSensorEstado.setText("Sin sensor acelerómetro disponible.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (acelerometro != null && sensorManager != null) {
            sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            tvSensorValores.setText(String.format(Locale.getDefault(), "X: %.2f  Y: %.2f  Z: %.2f", x, y, z));

            double aceleracion = Math.sqrt(x * x + y * y + z * z);
            if (aceleracion > 12.0) {
                tvSensorEstado.setText("¡Movimiento detectadp!");
            } else {
                tvSensorEstado.setText("Dispositivo Estable");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}