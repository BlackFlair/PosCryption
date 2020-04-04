package com.black.flair.poscryption;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements LocationListener {

    Button encBtn, decBtn, clearBtn;
    EditText textET;
    EditText textTV;
    int asciiChar;
    String inputText;
    char compText;
    StringBuilder outputText = new StringBuilder();
    double latitude;
    double longitude;
    private double latlong = 1234;

    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        clearBtn = findViewById(R.id.clearBtn);
        encBtn = findViewById(R.id.encBtn);
        decBtn = findViewById(R.id.decBtn);
        textET = findViewById(R.id.textET);
        textTV = findViewById(R.id.textTV);

        encBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cryptionCode();

                inputText = textET.getText().toString();

                //TODO: Encryption Algorithm

                for(int i = 0; i < inputText.length(); i++){

                    asciiChar = (int) inputText.charAt(i);
                    compText = (char) (asciiChar + latlong);
                    outputText.append(compText);
                }

                textET.setText(null);
                textET.setVisibility(View.GONE);
                encBtn.setVisibility(View.INVISIBLE);
                decBtn.setVisibility(View.INVISIBLE);

                textTV.setVisibility(View.VISIBLE);
                textTV.setText(outputText);
            }


        });

        decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cryptionCode();

                inputText = textET.getText().toString();

                //TODO: Decryption Algorithm

                for(int i = 0; i < inputText.length(); i++){

                    asciiChar = (int) inputText.charAt(i);
                    compText = (char) (asciiChar - latlong);
                    outputText.append(compText);
                }

                textET.setText(null);
                textET.setVisibility(View.GONE);
                encBtn.setVisibility(View.INVISIBLE);
                decBtn.setVisibility(View.INVISIBLE);

                textTV.setVisibility(View.VISIBLE);
                textTV.setText(outputText);
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                outputText.delete(0,outputText.length());

                textTV.setVisibility(View.GONE);
                textET.setVisibility(View.VISIBLE);
                encBtn.setVisibility(View.VISIBLE);
                decBtn.setVisibility(View.VISIBLE);

                textTV.setText(null);
                textET.setText(null);
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

        longitude = location.getLongitude() * 1000000;
        latitude = location.getLatitude() * 1000000;

        System.out.println("Latitude : " + latitude + "\nLongitude : " + longitude);

        latlong = latitude % longitude;

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



    void cryptionCode(){

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }

        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        onLocationChanged(location);

    }

}