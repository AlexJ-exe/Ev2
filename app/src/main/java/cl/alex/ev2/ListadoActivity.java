package cl.alex.ev2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ListadoActivity extends AppCompatActivity {

    private ListView lvIncidencias;
    private FirebaseFirestore db;
    private List<String> listaIncidenciasText;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        lvIncidencias = findViewById(R.id.lvIncidencias);
        db = FirebaseFirestore.getInstance();

        listaIncidenciasText = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaIncidenciasText);
        lvIncidencias.setAdapter(adapter);

        cargarIncidenciasDesdeFirestore();
    }

    private void cargarIncidenciasDesdeFirestore() {
        db.collection("incidencias")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listaIncidenciasText.clear();
                    if (queryDocumentSnapshots.isEmpty()) {
                        listaIncidenciasText.add("No hay incidencias registradas aún.");
                    } else {
                        for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                            String titulo = doc.getString("titulo");
                            String categoria = doc.getString("categoria");
                            String fechaHora = doc.getString("fechaHora");
                            String lat = doc.getString("latitud");
                            String lng = doc.getString("longitud");

                            String item = (titulo != null ? titulo : "Sin título") + " [" + (categoria != null ? categoria : "") + "]\n" +
                                    "Fecha: " + (fechaHora != null ? fechaHora : "") + "\n" +
                                    "Ubicación: " + (lat != null ? lat : "N/A") + ", " + (lng != null ? lng : "N/A");

                            listaIncidenciasText.add(item);
                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Manejar errores sin cerrar la aplicación
                    Toast.makeText(ListadoActivity.this, "Error de red al cargar datos: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                });
    }
}