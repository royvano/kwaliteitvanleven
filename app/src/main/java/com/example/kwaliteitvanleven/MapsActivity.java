package com.example.kwaliteitvanleven;

import android.*;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView firstLabel;
    private TextView titel;
    private TextView secondLabel;
    private TextView thirdLabel;
    private double longi = 10.0;
    private double lati = 10.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        firstLabel = (TextView) findViewById(R.id.latitude);
        secondLabel = (TextView) findViewById(R.id.longitude);
        thirdLabel = (TextView) findViewById(R.id.city);
        titel = (TextView) findViewById(R.id.titel);

        getLoc();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void getLoc() {

        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        GPStracker g = new GPStracker(getApplicationContext());

        Location loc = g.getLocation();
        if (loc != null) {
            lati = loc.getLatitude();
            longi = loc.getLongitude();

            //Toast.makeText(this, "" + lat + lon, Toast.LENGTH_SHORT).show();
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(lati, longi, 1);
            } catch (IOException e) {
                e.printStackTrace();
                titel.setText("Je locatie is niet gevonden..");
            }

            String city = addresses.get(0).getLocality();
            String street = addresses.get(0).getAddressLine(0);
            String zip = addresses.get(0).getPostalCode();
            String country = addresses.get(0).getCountryName();

            firstLabel.setText("");
            secondLabel.setText("");
            thirdLabel.setText("");
            titel.setText("Hulp in " + city);


            //Toast.makeText(this, city + "", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add marker and move the camera
        LatLng loca = new LatLng(lati, longi);
        mMap.addMarker(new MarkerOptions().position(loca).title("Je locatie"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loca, 12));
    }
}
