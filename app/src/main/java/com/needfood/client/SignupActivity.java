package com.needfood.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    EditText editText1;
    EditText editText2;
    EditText name;
    Button button;
    FirebaseAuth mAuth;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        textView = findViewById(R.id.lg);
        editText1 = findViewById(R.id.email);
        editText2 = findViewById(R.id.password);
        name = findViewById(R.id.name);
        button = findViewById(R.id.signup_btn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        button.setOnClickListener(view -> {
            String email = editText1.getText().toString();
            String pass = editText2.getText().toString();
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(authResult -> {
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }).addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                });
            }
        });

        textView.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}

