package com.needfood.client;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;
import com.needfood.client.databinding.ActivityDonateDetailBinding;
import com.needfood.client.models.Donate;

public class DonateDetailActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityDonateDetailBinding binding;
    private double lat = 0, log = 0;
    private String name = null, phone = null, address = null;

    private TextView mName, mPhone, mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDonateDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        name = getIntent().getStringExtra("NAME");
        phone = getIntent().getStringExtra("PHONE");
        address = getIntent().getStringExtra("ADDRESS");
        lat = getIntent().getDoubleExtra("LAT", 0.0);
        log = getIntent().getDoubleExtra("LONG", 0.0);

        mName = findViewById(R.id.name);
        mPhone = findViewById(R.id.phone);
        mAddress = findViewById(R.id.address);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng = new LatLng(lat, log);
        mMap.addMarker(new MarkerOptions().position(latLng).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setMinZoomPreference(15);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mName.setText(name);
        mPhone.setText(phone);
        mAddress.setText(address);
    }
}