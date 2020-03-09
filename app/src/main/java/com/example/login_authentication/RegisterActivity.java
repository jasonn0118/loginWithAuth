package com.example.login_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText edName, edEmail, edPass, edPhone;
    Button btnRegister;
    TextView txtLogin;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edName = findViewById(R.id.edFullName);
        edEmail = findViewById(R.id.edEmail);
        edPass = findViewById(R.id.edPassword);
        edPhone = findViewById(R.id.edPhone);
        btnRegister = findViewById(R.id.btnLogin);
        txtLogin = findViewById(R.id.txtLoginRegistered);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString().trim();
                String password = edPass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    edEmail.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    edPass.setError("Password is required.");
                    return;
                }
                if(password.length() < 6){
                    edPass.setError("Password needs to be longer than 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //register an user in Firebase.
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else{
                            Toast.makeText(RegisterActivity.this, "Error!! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}
