package com.needfood.client;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.needfood.client.tabs.HomeFragment;
import com.needfood.client.tabs.ProfileFragment;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        navigationView = findViewById(R.id.bottom_navigation_bar);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_parent, new HomeFragment()).commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onStart() {
        super.onStart();
        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_parent, new HomeFragment()).commit(); break;
                case R.id.profile: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_parent, new ProfileFragment()).commit(); break;
            }
            return true;
        });
    }
}