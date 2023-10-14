package com.example.firebasetestproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    private EditText edName,secName,eMail;
    private DatabaseReference mDataBase;
    private String USERS_KEY = "User";
    private ImageView imageView;
    private  StorageReference storageRef;
    private Uri upLoadUri;
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
        imageView = findViewById(R.id.imageAvatar);
        mDataBase= FirebaseDatabase.getInstance().getReference(USERS_KEY);
        storageRef = FirebaseStorage.getInstance().getReference("ImageDB");
    }

    private void saveUser(){
        String id = mDataBase.push().getKey();
        String name = edName.getText().toString();
        String sec_name = secName.getText().toString();
        String email = eMail.getText().toString();

        Users newUser = new Users(id, name, sec_name, email,upLoadUri.toString());
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(sec_name) && !TextUtils.isEmpty(email)) {
           if(id!=null)mDataBase.child(id).setValue(newUser);
            Toast.makeText(this, "Пользователь добавлен", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Заполни пустое поле", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickSave(View view) {
        uploadURI();
    }


    public void onClickRead(View view){
        Intent intent = new Intent(this,ReadActivity.class);
        startActivity(intent);
    }

    public void onClickChooseImage(View view){
        getImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resutlCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resutlCode,data);
        if(requestCode == 1 && data != null && data.getData() != null){
            if(resutlCode == RESULT_OK){
                Log.d("MyLog", "omage URI"+data.getData());
                imageView.setImageURI(data.getData());

            }
        }
    }

    private void uploadURI(){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] byteArray = baos.toByteArray();
        final StorageReference mRef = storageRef.child(System.currentTimeMillis()+ " my_image");
        UploadTask uploadTask = mRef.putBytes(byteArray);
        Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return mRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                upLoadUri = task.getResult();
                saveUser();
            }

        });
        Toast.makeText(this, "Картинка загружена", Toast.LENGTH_SHORT).show();
    }

    private void getImage(){
        Intent intentChoose = new Intent();
        intentChoose.setType("image/*");
        intentChoose.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChoose, 1);
    }
}