package com.example.firebasetestproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.R.layout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private List<Users> listUser;
    private DatabaseReference mDataBase;
    private String USERS_KEY = "User";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        init();
        setOnClickItem();
        getDataFromDB();
    }

    private void init(){
        listView = findViewById(R.id.listView);
        listData = new ArrayList<>();
        listUser = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
        mDataBase= FirebaseDatabase.getInstance().getReference(USERS_KEY);
    }
    private void getDataFromDB(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listData.size()>0)listData.clear();
                if(listUser.size()>0)listUser.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    Users users = ds.getValue(Users.class);
                    assert users != null;
                    listData.add(users.name);
                    listUser.add(users);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        mDataBase.addValueEventListener(vListener);
    }

    private void setOnClickItem(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Users users = listUser.get(position);
            Intent i = new Intent(ReadActivity.this, ShowItem.class);
            i.putExtra(Constant.USER_NAME, users.name);
            i.putExtra(Constant.USER_SEC_NAME, users.secondName);
            i.putExtra(Constant.USER_EMAIL, users.email);
            i.putExtra(Constant.USER_IMAGE, users.imageId);
            startActivity(i);
            }
        });
    }
}