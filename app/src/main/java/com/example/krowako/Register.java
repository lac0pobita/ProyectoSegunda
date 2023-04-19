package com.example.krowako;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.krowako.databinding.ActivityRegisterBinding;

public class Register extends AppCompatActivity {

    ActivityRegisterBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.singupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupConfirm.getText().toString();
                String fName = binding.signupFname.getText().toString();
                String lName = binding.signupLname.getText().toString();

                if (email.equals("") || password.equals("") || confirmPassword.equals(""))
                Toast.makeText(Register.this, "Todos los campos son obligatorios ", Toast.LENGTH_SHORT).show();
                else {

                    if(password.equals(confirmPassword)){

                        Boolean checkUserEmail = databaseHelper.checkEmail(email);

                        if(checkUserEmail == false){
                            Boolean insert = databaseHelper.insertData(email, password, fName , lName);

                            if(insert == true){
                                Toast.makeText(Register.this, "Se ha registrado con exito!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(Register.this, "Error en el proceso ", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(Register.this, "Ya existe una cuenta ", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(Register.this, "Contrseña incorrecta", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }
}