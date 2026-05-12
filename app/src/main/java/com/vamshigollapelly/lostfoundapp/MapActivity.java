package com.vamshigollapelly.lostfoundapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseHelper db;
    private FusedLocationProviderClient fusedClient;
    private double userLat = 0, userLng = 0;
    private int radiusKm = 10;
    private SeekBar seekRadius;
    private TextView tvRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        db          = new DatabaseHelper(this);
        fusedClient = LocationServices
                .getFusedLocationProviderClient(this);

        seekRadius = findViewById(R.id.seekRadius);
        tvRadius   = findViewById(R.id.tvRadius);
        Button btnFilter = findViewById(R.id.btnFilter);

        // SeekBar for radius 1-50 km
        seekRadius.setMax(49);
        seekRadius.setProgress(9); // default 10km
        tvRadius.setText("Radius: 10 km");

        seekRadius.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override public void onProgressChanged(
                            SeekBar s, int progress, boolean fromUser) {
                        radiusKm = progress + 1;
                        tvRadius.setText("Radius: " + radiusKm + " km");
                    }
                    @Override public void onStartTrackingTouch(SeekBar s){}
                    @Override public void onStopTrackingTouch(SeekBar s){}
                });

        btnFilter.setOnClickListener(v -> {
            if (mMap != null) {
                mMap.clear();
                loadMarkersWithRadius();
            }
        });

        // Initialise map fragment
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getUserLocationThenLoad();
    }

    private void getUserLocationThenLoad() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    300);
            return;
        }
        mMap.setMyLocationEnabled(true);
        fusedClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                userLat = location.getLatitude();
                userLng = location.getLongitude();
                LatLng userPos = new LatLng(userLat, userLng);
                mMap.moveCamera(CameraUpdateFactory
                        .newLatLngZoom(userPos, 12));
            }
            loadMarkersWithRadius();
        });
    }

    private void loadMarkersWithRadius() {
        List<LostFoundItem> items = db.getAllItems(null);
        int count = 0;

        for (LostFoundItem item : items) {
            if (item.getLatitude() == 0
                    && item.getLongitude() == 0) continue;

            // Calculate distance from user
            float[] results = new float[1];
            android.location.Location.distanceBetween(
                    userLat, userLng,
                    item.getLatitude(), item.getLongitude(),
                    results);
            float distanceKm = results[0] / 1000f;

            // Only show if within radius
            if (userLat != 0 && distanceKm > radiusKm) continue;

            LatLng pos = new LatLng(
                    item.getLatitude(), item.getLongitude());

            // Red marker for Lost, Green for Found
            float markerColor = "Lost".equals(item.getType())
                    ? BitmapDescriptorFactory.HUE_RED
                    : BitmapDescriptorFactory.HUE_GREEN;

            mMap.addMarker(new MarkerOptions()
                    .position(pos)
                    .title(item.getType() + ": " + item.getDescription())
                    .snippet(item.getLocation() + " | "
                            + item.getCategory())
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(markerColor)));
            count++;
        }

        Toast.makeText(this,
                count + " items shown within " + radiusKm + " km",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int req,
                                           String[] perms, int[] grants) {
        super.onRequestPermissionsResult(req, perms, grants);
        if (req == 300 && grants.length > 0
                && grants[0] == PackageManager.PERMISSION_GRANTED)
            getUserLocationThenLoad();
    }
}
