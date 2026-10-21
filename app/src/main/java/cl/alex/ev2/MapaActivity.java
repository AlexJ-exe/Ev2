package cl.alex.ev2;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        db = FirebaseFirestore.getInstance();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        cargarIncidenciasEnMapa();
    }

    private void cargarIncidenciasEnMapa() {
        db.collection("incidencias")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        Toast.makeText(MapaActivity.this, "No hay incidencias para mostrar en el mapa.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    LatLng primeraUbicacion = null;

                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        String titulo = doc.getString("titulo");
                        String categoria = doc.getString("categoria");
                        String latStr = doc.getString("latitud");
                        String lngStr = doc.getString("longitud");

                        if (latStr != null && lngStr != null) {
                            try {
                                double lat = Double.parseDouble(latStr);
                                double lng = Double.parseDouble(lngStr);
                                LatLng punto = new LatLng(lat, lng);

                                mMap.addMarker(new MarkerOptions()
                                        .position(punto)
                                        .title(titulo != null ? titulo : "Incidencia")
                                        .snippet(categoria != null ? categoria : ""));

                                if (primeraUbicacion == null) {
                                    primeraUbicacion = punto;
                                }
                            } catch (NumberFormatException ignored) {
                            }
                        }
                    }

                    if (primeraUbicacion != null) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(primeraUbicacion, 14f));
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MapaActivity.this, "Error al obtener datos para el mapa: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}