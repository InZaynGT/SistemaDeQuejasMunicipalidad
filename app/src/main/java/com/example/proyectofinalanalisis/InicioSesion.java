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

    private static InicioSesion _instance = null;

    EditText correo, contrasena;

    String str_email,str_password, respuesta;
    String urlCrearUsuario = "",
            urlIniciarSesion = "http://192.168.1.7/ANALISIS_BackendPHP/iniciarSesion.php",
            urlAtenderQueja = "",
            ulrListarQuejas = "";
    Button btnLogin;
    int comparador=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion);

        InicioSesion._instance = this;

        correo = findViewById(R.id.editTextTextUsuario);
        contrasena = findViewById(R.id.editTextTextPasswordUsuario);
        btnLogin = (Button) findViewById(R.id.buttonIniciarSesion);
        Button btnRegistro = findViewById(R.id.textViewRegistrarse);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Login(v);
            }
        });

        btnRegistro.setOnClickListener(view -> {
            Intent intent = new Intent(this, Registro.class);
            startActivity(intent);
        });

    }

    public static InicioSesion getInstance() {
        return _instance;
    }

    public void Login(View v) {

        if(correo.getText().toString().equals("")){
            Toast.makeText(this, "Ingrese Email", Toast.LENGTH_SHORT).show();
        }
        else if(contrasena.getText().toString().equals("")){
            Toast.makeText(this, "Ingrese Contraseña", Toast.LENGTH_SHORT).show();
        }
        else{


            //final ProgressDialog progressDialog = new ProgressDialog(this);
            //progressDialog.setMessage("Por favor espera...");

            //progressDialog.show();

            str_email = correo.getText().toString().trim();
            str_password = contrasena.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, urlIniciarSesion, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //progressDialog.dismiss();
                    respuesta = response;
                    /*
                    if(!respuesta.equals("exitoso")){
                        Toast.makeText(InicioSesion.this, "NoFuncionaIF "+response+comparador, Toast.LENGTH_SHORT).show();
                    }
                    */
                    if(!respuesta.equals("fallido")){
                        comparador=1;
                        Toast.makeText(InicioSesion.this, "Ingreso "+response, Toast.LENGTH_SHORT).show();
                    }



                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    //progressDialog.dismiss();
                    Toast.makeText(InicioSesion.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("correo",str_email);
                    params.put("contrasena",str_password);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(InicioSesion.this);
            requestQueue.add(request);

        }
        if(comparador==1){
            Intent i = new Intent(new Intent(getInstance(),QuejasUsuario.class));
            startActivityForResult(i, 0);
            correo.setText("");
            contrasena.setText("");
            InicioSesion.this.recreate();
        } else{
            Toast.makeText(InicioSesion.this, "Ingreso"+respuesta, Toast.LENGTH_SHORT).show();
        }
    }

//    public void irARegistro(View v){
//        TextView info = findViewById(R.id.textViewRegistrarse);
//        info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent (v.getContext(), Registro.class);
//                startActivityForResult(intent, 0);
//            }
//        });
//    }
}