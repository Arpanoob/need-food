package com.needfood.client;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DonateActivity extends AppCompatActivity implements LocationListener {
    private static final int LOCATION_PERMISSION_CODE = 101;
    private static final int COARSE_LOCATION_PERMISSION_CODE = 102;

    private ImageView photo;
    private EditText name, phone;
    private Button donateBtn;
    private LocationManager locationManager;

    private String photoUrl = null, username = null;
    private boolean isPhotoPicked = false;
    private double latitude = 0, longitude = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        username = getIntent().getStringExtra("NAME");

        photo = findViewById(R.id.photo);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        donateBtn = findViewById(R.id.donate_btn);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            checkLocationPermission();
        } else {
            getCurrentLocation();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        name.setText(username);
        name.setEnabled(false);

        ActivityResultLauncher<Intent> pickImageResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            try {
                Intent intent = result.getData();
                photoUrl = intent.getData().toString();
                Glide.with(this).load(photoUrl).into(photo);
                isPhotoPicked = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        photo.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            pickImageResult.launch(intent);
        });

        donateBtn.setOnClickListener(view -> {
            if (!isPhotoPicked) {
                Toast.makeText(this, "Photo required.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (name.getText().toString().isEmpty()) {
                Toast.makeText(this, "Name required.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (phone.getText().toString().isEmpty()) {
                Toast.makeText(this, "Phone required.", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10 , this);
            } else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, this);
            }
        } else {
            Toast.makeText(this, "Location is mandatory.", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, LOCATION_PERMISSION_CODE);
        } else if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION }, COARSE_LOCATION_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE || requestCode == COARSE_LOCATION_PERMISSION_CODE) {
            getCurrentLocation();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(this);
    }
}