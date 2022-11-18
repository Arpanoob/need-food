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
    private EditText email;
    private EditText password;
    private Button button;
    private TextView toSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            toHomeActivity();
        }

        email = findViewById(R.id.email);
        toSignup = findViewById(R.id.to_signup);
        password = findViewById(R.id.password);
        button = findViewById(R.id.login_btn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        button.setOnClickListener(view -> {
            String email = this.email.getText().toString();
            String password = this.password.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fields are required.", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                    toHomeActivity();
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Unable to Login.", Toast.LENGTH_SHORT).show();
                });
            }
        });

        toSignup.setOnClickListener(view -> {
            Intent intent=new Intent(this,SignupActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void toHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}