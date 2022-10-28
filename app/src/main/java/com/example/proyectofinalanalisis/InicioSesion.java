package com.example.proyectofinalanalisis;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class InicioSesion extends AppCompatActivity {

    EditText correo, contrasena;

    String str_email,str_password;
    String urlCrearUsuario = "",
           urlIniciarSesion = "127.0.0.1:3036/ProyectoAnalisis/iniciarSesion.php",
           ulrCrearQueja ="",
           urlAtenderQueja = "",
           ulrListarQuejas = "";
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion);

        correo = findViewById(R.id.editTextTextUsuario);
        contrasena = findViewById(R.id.editTextTextPasswordUsuario);
        btnLogin = (Button) findViewById(R.id.buttonIniciarSesion);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Login(urlIniciarSesion);
            }
        });
    }


    public void Login(String url) {

        if(correo.getText().toString().equals("")){
            Toast.makeText(this, "Ingrese Email", Toast.LENGTH_SHORT).show();
        }
        else if(contrasena.getText().toString().equals("")){
            Toast.makeText(this, "Ingrese Contraseña", Toast.LENGTH_SHORT).show();
        }
        else{


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Por favor espera...");

            progressDialog.show();

            str_email = correo.getText().toString().trim();
            str_password = contrasena.getText().toString().trim();


            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if(response.equalsIgnoreCase("Ingreso correctamente")){

                        correo.setText("");
                        contrasena.setText("");
                        startActivity(new Intent(getApplicationContext(),creacionqueja.class));
                        Toast.makeText(InicioSesion.this, response, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(InicioSesion.this, response, Toast.LENGTH_SHORT).show();
                    }

                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(InicioSesion.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("email",str_email);
                    params.put("password",str_password);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(InicioSesion.this);
            requestQueue.add(request);




        }
    }

    public void irARegistro(View v){
        TextView info = findViewById(R.id.textViewRegistrarse);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Registro.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}