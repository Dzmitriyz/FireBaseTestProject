package com.example.firebasetestproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText edName,secName,eMail;
    private DatabaseReference mDataBase;
    private String USERS_KEY = "User";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init(){
        edName = findViewById(R.id.edName);
        secName = findViewById(R.id.edSecName);
        eMail = findViewById(R.id.edEMail);
        mDataBase= FirebaseDatabase.getInstance().getReference(USERS_KEY);
    }

    public void onClickSave(View view) {
        String id = mDataBase.getKey();
        String name = edName.getText().toString();
        String sec_name = secName.getText().toString();
        String email = eMail.getText().toString();

        Users newUser = new Users(id, name, sec_name, email);
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(sec_name) && !TextUtils.isEmpty(email)) {
            mDataBase.push().setValue(newUser);
            Toast.makeText(this, "Пользователь добавлен", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Заполни пустое поле", Toast.LENGTH_SHORT).show();
        }
    }


    public void onClickRead(View view){
        Intent intent = new Intent(this,ReadActivity.class);
        startActivity(intent);
    }
}