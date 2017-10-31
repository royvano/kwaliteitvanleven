package com.example.kwaliteitvanleven;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Locatie extends AppCompatActivity {


    private TextView firstLabel;
    private TextView titel;
    private TextView secondLabel;
    private TextView thirdLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locatie);

        firstLabel = (TextView) findViewById(R.id.latitude);
        secondLabel = (TextView) findViewById(R.id.longitude);
        thirdLabel = (TextView) findViewById(R.id.city);
        titel = (TextView) findViewById(R.id.titel);

        getLoc();
    }

    public void getLoc() {

        ActivityCompat.requestPermissions(Locatie.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        GPStracker g = new GPStracker(getApplicationContext());

        Location loc = g.getLocation();
        if (loc != null) {
            double lat = loc.getLatitude();
            double lon = loc.getLongitude();

            //Toast.makeText(this, "" + lat + lon, Toast.LENGTH_SHORT).show();
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(lat, lon, 1);
            } catch (IOException e) {
                e.printStackTrace();
                titel.setText("Je locatie is niet gevonden..");
            }

            String city = addresses.get(0).getLocality();
            String street = addresses.get(0).getAddressLine(0);
            String zip = addresses.get(0).getPostalCode();
            String country = addresses.get(0).getCountryName();

            firstLabel.setText(street);
            secondLabel.setText(city + ", " + zip);
            thirdLabel.setText(country);
            titel.setText("Je locatie is gevonden!");


            //Toast.makeText(this, city + "", Toast.LENGTH_SHORT).show();
        }
    }

    public void GoToQuestion() {
        // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //intent.putExtra("EXTRA_LAT", lat);
        //intent.putExtra("EXTRA_LONG", lon);
        //startActivity(intent);
        Intent intent = new Intent(getApplicationContext(), hulpActivity.class);
        intent.putExtra("BUTTON_PRESSED", "Ja");
        startActivity(intent);

    }
}