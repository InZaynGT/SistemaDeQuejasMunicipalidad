package com.example.proyectofinalanalisis;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.Output;
import android.os.Bundle;
import android.os.Environment;
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
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.mifmif.common.regex.Generex;
//import com.itextpdf.text.pdf.*;


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
        spinCategoria = (Spinner) findViewById(R.id.spinCategoria);
        btnCrearQueja = (Button) findViewById(R.id.btnCrearQueja);

        btnCrearQueja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarServicio("http://10.0.2.2/ANALISIS_BackendPHP/crearQueja.php");

                String descripcionPdf = edTextDescripcion.getText().toString();
                String imagenPdf = edTextUrl.getText().toString();
                Generex generex = new Generex("[6-9]\\d{1,5}");
                String idTicket = generex.random();



                try {
                    crearPdf(descripcionPdf, imagenPdf);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

    }




    public void crearPdf(String descripcion, String imagenUrl) throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "ticketQueja.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        pdfDocument.setDefaultPageSize(PageSize.A6);
        document.setMargins(0, 0, 0, 0);
        Paragraph reporteQueja = new Paragraph("Reporte de Queja").setBold().setFontSize(24).setTextAlignment(TextAlignment.CENTER);
        Paragraph subtituloQueja = new Paragraph("POWER REPORT\n" +
                "Municipalidad de Huehuetenango").setTextAlignment(TextAlignment.CENTER).setFontSize(12);

        float[] width = {100f, 100f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(new Cell().add(new Paragraph("Descripción")));
        table.addCell(new Cell().add(new Paragraph(descripcion)));

        table.addCell(new Cell().add(new Paragraph("URL")));
        table.addCell(new Cell().add(new Paragraph(imagenUrl)));

//        table.addCell(new Cell().add(new Paragraph("Ticket")));
//        table.addCell(new Cell().add(new Paragraph(idTicket)));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        table.addCell(new Cell().add(new Paragraph("Fecha")));
        table.addCell(new Cell().add(new Paragraph(LocalDate.now().format(dateTimeFormatter).toString())));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss a");
        table.addCell(new Cell().add(new Paragraph("Hora")));
        table.addCell(new Cell().add(new Paragraph(LocalTime.now().format(timeFormatter).toString())));

        BarcodeQRCode qrCode = new BarcodeQRCode(descripcion + "\n" + imagenUrl + LocalDate.now().format(dateTimeFormatter) + "\n"
                + LocalTime.now().format(timeFormatter));
        PdfFormXObject qrCodeObject = qrCode.createFormXObject(ColorConstants.BLACK, pdfDocument);
        Image qrCodeImage = new Image(qrCodeObject).setWidth(80).setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add(reporteQueja);
        document.add(subtituloQueja);
        document.add(table);
        document.add(qrCodeImage);



        document.close();
        Toast.makeText(this, "Ticket Creado", Toast.LENGTH_SHORT).show();

    }


    String textSpinner = "";
    String estado = "EN PROCESO";
    String fecha = "now()";
    String id_usuario = "1";

    ///Botón de regreso a Inicio
    public void regresarQuejas(View v) {
        TextView info = findViewById(R.id.ButtonRegresar);
        info.setOnClickListener(new View.OnClickListener() {
            //Limpiar entradas
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuejasUsuario.class);
                startActivityForResult(intent, 0);
            }
        });
    }


    //Envio de datos
    private void ejecutarServicio(String URL) {
        textSpinner = spinCategoria.getSelectedItem().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Exito: " + response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            //@Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("descripcion", edTextDescripcion.getText().toString());
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