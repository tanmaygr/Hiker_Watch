package com.example.tanmay.hikerwatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    LocationManager locationManager;
    LocationListener locationListener;

    public void updateLocation(Location location) {
        Log.i("Location info", location.toString());
        TextView latText = findViewById(R.id.LatTextView5);
        TextView lonText = findViewById(R.id.lonTextView6);
        TextView altText = findViewById(R.id.ALtTextView5);
        TextView accText = findViewById(R.id.AccTextView5);

        latText.setText("Latitude: " + location.getLatitude());
        lonText.setText("Longitude: " + location.getLongitude());
        altText.setText("Altitude: " + location.getAltitude());
        accText.setText("Accuracy: " + location.getAccuracy());
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            String address = "Could not find address";
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addressList != null && addressList.size() > 0) {
                Log.i("PlaceInfo", addressList.get(0).toString());
                address = "Address: \n";

                if (addressList.get(0).getAddressLine(0) != null) {

                    address += addressList.get(0).getAddressLine(0) + " ";

                }

//                if (addressList.get(0).getThoroughfare() != null) {
//
//                    address += addressList.get(0).getThoroughfare() + "\n";
//
//                }
//
//                if (addressList.get(0).getLocality() != null) {
//
//                    address += addressList.get(0).getLocality() + "\n";
//
//                }
//
//                if (addressList.get(0).getPostalCode() != null) {
//
//                    address += addressList.get(0).getPostalCode() + "\n";
//
//                }
//
//                if (addressList.get(0).getCountryName() != null) {
//
//                    address += addressList.get(0).getCountryName();
//
//                }

            }

            TextView addressTextView = (TextView) findViewById(R.id.AddTextView5);

            addressTextView.setText(address);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                start();

            }
        }
        public void start(){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        }

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.i("Location Info", location.toString());
                    updateLocation(location);
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
            };
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    updateLocation(location);
                }
            }
        }

    }

