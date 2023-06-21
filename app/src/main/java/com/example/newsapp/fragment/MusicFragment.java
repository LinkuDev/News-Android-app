package com.example.newsapp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.newsapp.MainActivity;
import com.example.newsapp.MainActivity_main;
import com.example.newsapp.R;
import com.example.newsapp.RegisterActivity;
import com.example.newsapp.tool.Check;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MusicFragment extends Fragment {
    EditText editTextEmail,editTextPassword;
    Button btnLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    TextView btn;

    public MusicFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_music, container, false);
        btn=v.findViewById(R.id.textViewSignUp);

        editTextEmail= v.findViewById(R.id.Email);
        editTextPassword= v.findViewById(R.id.Age);
        progressBar=v.findViewById(R.id.progressBarr);
        btnLogin=v.findViewById(R.id.btnlogin);
        mAuth= FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                if (email.isEmpty()){
                    editTextEmail.setError("Không thể bỏ trống");
                    editTextEmail.requestFocus();
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTextEmail.setError("Xin hãy nhập email đúng");
                    editTextEmail.requestFocus();

                }
                if (password.isEmpty()&&password.length()<6){
                    editTextPassword.setError("Mật khẩu không thể bỏ trống và phải dài hơn 6 kí tự");
                    editTextEmail.requestFocus();
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Check_Email",email);
                            editor.commit();

                            startActivity(new Intent(getActivity(), MainActivity_main.class));
                            getActivity().getFragmentManager().popBackStack();

                        }
                        else {
                            Toast.makeText(getActivity(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RegisterActivity.class));
            }
        });
        return v;
    }



}
