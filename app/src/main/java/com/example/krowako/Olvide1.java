package com.example.krowako;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.krowako.databinding.ActivityLoginBinding;
import com.example.krowako.databinding.ActivityOlvide1Binding;

public class Olvide1 extends AppCompatActivity {



    ActivityOlvide1Binding binding;
    DatabaseHelper databaseHelper;

    public static String Clave() {

        Random rnd = new Random();
        int number = rnd.nextInt(999999);


        return String.format("%06d", number);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOlvide1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());





        databaseHelper = new DatabaseHelper(this);
        binding.codigoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.olvEmail.getText().toString();


                if (email.equals(""))
                    Toast.makeText(Olvide1.this, "Introduzca su correo", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkCredentials = databaseHelper.checkEmail(email);

                    if (checkCredentials == true){
                        Toast.makeText(Olvide1.this, "Codigo Enviado" + Clave(), Toast.LENGTH_SHORT).show();









                        Intent intent = new Intent(getApplicationContext(), Olvide2.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(Olvide1.this, "No tiene una cuenta con este correo", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        binding.loginOlvRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Olvide1.this, Login.class);
                startActivity(intent);
            }
        });
    }




}

