package com.example.proyectofinalanalisis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class QuejasUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quejas_usuario);
    }

    public void nuevaQueja(View v){
        TextView info = findViewById(R.id.buttonNuevaQueja);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), creacionqueja.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    public void Salir(View v){
        TextView info = findViewById(R.id.ButtonSalir);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), InicioSesion.class);
                startActivityForResult(intent, 0);
            }
        });
    }

}