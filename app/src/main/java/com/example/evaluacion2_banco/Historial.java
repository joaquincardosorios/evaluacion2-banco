package com.example.evaluacion2_banco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Historial extends AppCompatActivity {
    private ArrayList<String> datos;
    private ArrayAdapter<String> adaptador1;
    ListView lv_transferencia;
    TextView tv_mensaje, tv_titulo;
    String tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        Intent intent = getIntent();

        tv_mensaje = findViewById(R.id.tv_mensajeVacio);
        tv_titulo = findViewById(R.id.tv_titulo);

        datos = intent.getStringArrayListExtra("historial");
        tipo = intent.getStringExtra("tipo_transaccion");

        tv_titulo.setText(tipo);

        if (datos != null){
            ArrayList<String> datosOrdenados = new ArrayList<>(datos.size());

            for (int i = datos.size() - 1; i >= 0; i--) {
                datosOrdenados.add(datos.get(i));
            }
            adaptador1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datosOrdenados);
            lv_transferencia = findViewById(R.id.lv_transacciones);
            lv_transferencia.setAdapter(adaptador1);

            tv_mensaje.setVisibility(View.GONE);
            lv_transferencia.setVisibility(View.VISIBLE);
        } else {
            tv_mensaje.setText("El historial de"+ tipo +"está vacio");
        }
    }

    public void volverHistorial(View view) {
        finish();
    }
}