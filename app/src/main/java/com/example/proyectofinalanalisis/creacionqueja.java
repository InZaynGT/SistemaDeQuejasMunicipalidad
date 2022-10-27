package com.example.proyectofinalanalisis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class creacionqueja extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacionqueja);
    }

    public void regresarQuejas(View v){
        TextView info = findViewById(R.id.ButtonRegresar);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), QuejasUsuario.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}