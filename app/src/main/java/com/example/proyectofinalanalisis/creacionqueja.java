package com.example.proyectofinalanalisis;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class creacionqueja extends AppCompatActivity {


    EditText edTextTitulo, edTextDescripcion, edTextUrl;
    Spinner spinCategoria;
    Button btnCrearQueja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacionqueja);
        //edTextTitulo = (EditText) findViewById(R.id.edTextTitulo);
        edTextDescripcion = (EditText) findViewById(R.id.edTextDescripcion);
        edTextUrl = (EditText) findViewById(R.id.edTextUrl);
        spinCategoria = (Spinner)  findViewById(R.id.spinCategoria);
        btnCrearQueja = (Button) findViewById(R.id.btnCrearQueja);

        btnCrearQueja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarServicio("http://192.168.1.7/ANALISIS_BackendPHP/crearQueja.php");
            }
        });

    }
    String textSpinner = "";
    String estado = "EN PROCESO";
    String fecha = "now()";
    String id_usuario = "1";

    ///Botón de regreso a Inicio
    public void regresarQuejas(View v){
        TextView info = findViewById(R.id.ButtonRegresar);
        info.setOnClickListener(new View.OnClickListener() {
            //Limpiar entradas
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), QuejasUsuario.class);
                startActivityForResult(intent, 0);
            }
        });
    }


    //Envio de datos
    private void ejecutarServicio(String URL){
        textSpinner = spinCategoria.getSelectedItem().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Exito: "+response, Toast.LENGTH_SHORT).show();

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
               parametros.put("descripcion",edTextDescripcion.getText().toString());
               parametros.put("imagen", edTextUrl.getText().toString());
               //parametros.put("estado", estado);
               //parametros.put("fecha", fecha);
               parametros.put("id_categoria", textSpinner);
               parametros.put("id_usuario", id_usuario);

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}