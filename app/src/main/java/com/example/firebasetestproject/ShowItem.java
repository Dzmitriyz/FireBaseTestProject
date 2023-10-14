package com.example.firebasetestproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ShowItem extends AppCompatActivity {
    private TextView t1, t2, t3;
    ImageView imageDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);
        init();
        getInt();
    }

    private void init(){
        t1 = findViewById(R.id.textView);
        t2 = findViewById(R.id.textView2);
        t3 = findViewById(R.id.textView3);
        imageDB = findViewById(R.id.imageDB);
    }

    private void getInt(){
        Intent readActiv = getIntent();
        t1.setText(readActiv.getStringExtra(Constant.USER_NAME));
        t2.setText(readActiv.getStringExtra(Constant.USER_SEC_NAME));
        t3.setText(readActiv.getStringExtra(Constant.USER_EMAIL));
        Picasso.get().load(readActiv.getStringExtra(Constant.USER_IMAGE)).into(imageDB);
    }
}