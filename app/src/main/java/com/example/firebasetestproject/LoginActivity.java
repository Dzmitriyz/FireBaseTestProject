package com.example.firebasetestproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
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
    }

    public void onClickSignUp(View view){

    }
    public void onClickSignIn(View view){

    }

}