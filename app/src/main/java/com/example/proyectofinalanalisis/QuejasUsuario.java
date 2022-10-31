package com.example.proyectofinalanalisis;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuejasUsuario extends AppCompatActivity {
    private static final String URL ="http://192.168.1.7/ANALISIS_BackendPHP/listarQuejas.php";
    Button button;
    List<Quejas> listaQuejas;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quejas_usuario);
        Button button = findViewById(R.id.buttonNuevaQueja);
        Button btnSalir = findViewById(R.id.ButtonSalir);



        recyclerView=(RecyclerView)findViewById(R.id.Recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaQuejas = new ArrayList<>();

        loadquejas();

        btnSalir.setOnClickListener(view -> {
            Intent intent = new Intent(this, InicioSesion.class);
            startActivity(intent);
        });

        button.setOnClickListener(view -> {
         Intent intent = new Intent(this, creacionqueja.class);
         startActivity(intent);
        });

//        actualizar.setOnClickListener(view -> {
//            loadquejas();
//        });

    }

    private void loadquejas(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject player = array.getJSONObject(i);

                        listaQuejas.add(new Quejas(
                                player.getString("descripcion"),
                                player.getString("imagen"),
                                player.getString("estado"),
                                player.getString("fecha"),
                                player.getInt("id_categoria")
                        ));
                    }
                    Adapter adapter = new Adapter(QuejasUsuario.this, listaQuejas);
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(), "Exito: ", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Escucho o/"+e+response, Toast.LENGTH_SHORT).show();
                    Log.e ( "response", "" + response );
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: "+error+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }














//
//    public void nuevaQueja(View v){
//        TextView info = findViewById(R.id.buttonNuevaQueja);
//        info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent (v.getContext(), creacionqueja.class);
////                startActivityForResult(intent, 0);
//                Intent intent = new Intent(getApplicationContext(), creacionqueja.class);
//                startActivity(intent);
//            }
//        });
//    }

//    public void Salir(View v){
//        TextView info = findViewById(R.id.ButtonSalir);
//        info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent (v.getContext(), InicioSesion.class);
//                startActivityForResult(intent, 0);
//            }
//        });
//    }

}