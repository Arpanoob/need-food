package com.needfood.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editText1;
    private EditText editText2;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        editText1 = findViewById(R.id.email);
        textView = findViewById(R.id.to_signup);
        editText2 = findViewById(R.id.password);
        button = findViewById(R.id.login_btn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        button.setOnClickListener(view -> {
            String email = editText1.getText().toString();
            String password = editText2.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
                });
            }
        });

        textView.setOnClickListener(view -> {
            Intent intent=new Intent(this,SignupActivity.class);
            startActivity(intent);
            finish();
        });
    }
}