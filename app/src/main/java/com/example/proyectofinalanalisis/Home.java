package com.example.proyectofinalanalisis;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button button = findViewById(R.id.buttonCrearUsuario);

        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, InicioSesion.class);
            startActivity(intent);
        });
        Button button2 = findViewById(R.id.buttonCrearUsuario2);

        button2.setOnClickListener(view -> {
            Intent intent = new Intent(this, QuejasAdmin.class);
            startActivity(intent);
        });

    }
}
