package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.newsapp.DB.SavedDB;
import com.example.newsapp.DB.UsersDB;
import com.example.newsapp.OOP.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity  {

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private EditText editTextName, editTextPassword, editTextEmail, editTextAge;
    private ProgressBar progressBar;
    private Button btnRegister;

    private SavedDB savedDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        savedDB = new SavedDB(RegisterActivity.this);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();

        editTextName = findViewById(R.id.inputUsername);
        editTextEmail = findViewById(R.id.Email);
        editTextAge = findViewById(R.id.Age);
        editTextPassword = findViewById(R.id.Password);
        btnRegister = findViewById(R.id.btnRegister);

        progressBar = findViewById(R.id.progressBarr);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    RegisterUser();
            }
        });
        TextView btn=findViewById(R.id.alreadyHaveAccount);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }

    private void RegisterUser() {
        String Name = editTextName.getText().toString().trim();
        String Email =  editTextEmail.getText().toString().trim();
        String Sdt = editTextAge.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();

        if (Name.isEmpty()){
            editTextName.setError("Không thể bỏ trống");
            editTextName.requestFocus();

        }
        if (Email.isEmpty()){
            editTextEmail.setError("Không thể bỏ trống");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            editTextEmail.setError("Xin hãy nhập email đúng");
            editTextEmail.requestFocus();
            return;

        }
        if (Password.isEmpty()&&Password.length()<6){
            editTextPassword.setError("Mật khẩu không thể bỏ trống và phải dài hơn 6 kí tự");
            editTextEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.getInstance().createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            addUser();
                            Toast.makeText(RegisterActivity.this,"đăng kí thành công",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            finish();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this,"lỗi, thử lại",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    private void addUser() {
                        savedDB.AddUser(Name,Sdt,Email,Password);
                    }
                });
    }
}
