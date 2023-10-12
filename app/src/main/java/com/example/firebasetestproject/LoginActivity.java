package com.example.firebasetestproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText edLogin, edPassword;
    private TextView txtUserLogin;
    Button btReg, btEnter, btStart,btExti;
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
    btStart = findViewById(R.id.btnStart);
    btExti = findViewById(R.id.btnExit);
    txtUserLogin = findViewById(R.id.txtUserLogin);
    mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser != null){
            Signed();
            String s = "Вы вошли как: "+cUser.getEmail();
            txtUserLogin.setText(s);
            Toast.makeText(this, "User not null ", Toast.LENGTH_SHORT).show();
        }else {
            NoSigned();
            Toast.makeText(this, "User null", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickSignUp(View view){
        if(!TextUtils.isEmpty(edLogin.getText().toString()) && !TextUtils.isEmpty(edPassword.getText().toString())) {
            mAuth.createUserWithEmailAndPassword(edLogin.getText().toString().trim(), edPassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        sendEmailVer();
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
                        Signed();
                        txtUserLogin.setText("Вы вошли как: "+edLogin.getText().toString());
                        Toast.makeText(LoginActivity.this, "User Sign in", Toast.LENGTH_SHORT).show();
                    }else{
                        NoSigned();
                        Toast.makeText(LoginActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void onClickStart(View view){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void onClickSignOut(View view){
        FirebaseAuth.getInstance().signOut();
        NoSigned();
        Toast.makeText(LoginActivity.this, "You singout like "+edLogin.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    private void NoSigned(){
        btStart.setVisibility(View.GONE);
        txtUserLogin.setVisibility(View.GONE);
        edLogin.setVisibility(View.VISIBLE);
        edPassword.setVisibility(View.VISIBLE);
        btReg.setVisibility(View.VISIBLE);
        btEnter.setVisibility(View.VISIBLE);
        btExti.setVisibility(View.GONE);
    }
    private void Signed(){
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        if(user.isEmailVerified()) {
            btStart.setVisibility(View.VISIBLE);
            txtUserLogin.setVisibility(View.VISIBLE);
            edLogin.setVisibility(View.GONE);
            edPassword.setVisibility(View.GONE);
            btReg.setVisibility(View.GONE);
            btEnter.setVisibility(View.GONE);
            btExti.setVisibility(View.VISIBLE);
        }else {
            Toast.makeText(LoginActivity.this, "Check your email ", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmailVer(){
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Check your email ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "Send email failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}