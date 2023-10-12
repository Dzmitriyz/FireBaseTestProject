package com.example.firebasetestproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText edLogin, edPassword;
    Button btReg, btEnter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){
    edLogin = findViewById(R.id.edLogin);
    edPassword = findViewById(R.id.edPassword);
    btReg = findViewById(R.id.btReg);
    btEnter = findViewById(R.id.btEnter);
    mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser != null){
            Toast.makeText(this, "User not null ", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "User null", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickSignUp(View view){
        if(!TextUtils.isEmpty(edLogin.getText().toString()) && !TextUtils.isEmpty(edPassword.getText().toString())) {
            mAuth.createUserWithEmailAndPassword(edLogin.getText().toString().trim(), edPassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void onClickSignIn(View view){
        if(!TextUtils.isEmpty(edLogin.getText().toString()) && !TextUtils.isEmpty(edPassword.getText().toString())) {
            mAuth.signInWithEmailAndPassword(edLogin.getText().toString().trim(), edPassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "User Sign in", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}