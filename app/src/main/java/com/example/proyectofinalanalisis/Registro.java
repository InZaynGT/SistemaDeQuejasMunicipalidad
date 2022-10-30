package com.example.proyectofinalanalisis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    EditText nombre, apellidos, correo, contrasena, dpi;
    String urlCrearQueja ="";
    Button crearUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre = findViewById(R.id.editTextTextNombreRegistro);
        apellidos = findViewById(R.id.editTextTextApellidosRegistro);
        correo = findViewById(R.id.editTextTextCorreoRegistro);
        contrasena = findViewById(R.id.editTextTextPasswordRegistro);
        dpi = findViewById(R.id.editTextTextDPI);
        crearUsuario = findViewById(R.id.buttonCrearUsuario);

        crearUsuario.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                procesarRegistro("http://10.0.2.2/ANALISIS_BackendPHP/crearUsuario.php");
            }
        });
    }

    public void procesarRegistro (String url){
        String id_jerarquia = "1";

        if(nombre.getText().toString().equals("")){
            Toast.makeText(this, "Ingrese Nombre", Toast.LENGTH_SHORT).show();
        }
        else if(apellidos.getText().toString().equals("")){
            Toast.makeText(this, "Ingrese Apellidos", Toast.LENGTH_SHORT).show();
        }
        else if(correo.getText().toString().equals("")){
            Toast.makeText(this, "Ingrese Email", Toast.LENGTH_SHORT).show();
        }
        else if(contrasena.getText().toString().equals("")){
            Toast.makeText(this, "Ingrese Contraseña", Toast.LENGTH_SHORT).show();
        }
        else if(dpi.getText().toString().equals("")){
            Toast.makeText(this, "Ingrese DPI", Toast.LENGTH_SHORT).show();
        }
        else{
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "Exito: "+response, Toast.LENGTH_SHORT).show();
                    apellidos.setText("");
                    nombre.setText("");
                    dpi.setText("");
                    correo.setText("");
                    contrasena.setText("");


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error: "+error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                //@Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("apellidos",apellidos.getText().toString());
                    parametros.put("nombres", nombre.getText().toString());
                    parametros.put("dpi", dpi.getText().toString());
                    parametros.put("correo", correo.getText().toString());
                    parametros.put("contrasena", contrasena.getText().toString());
                    parametros.put("id_jerarquia", id_jerarquia);

                    return parametros;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
}