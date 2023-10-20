package com.example.evaluacion2_banco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class Giro extends AppCompatActivity {

    EditText montoGiro;
    Cuenta cuenta;
    Helper helper = new Helper();
    private ArrayList<String> giros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giro);
        // Obtener cuenta desde Intent
        cuenta = (Cuenta) getIntent().getExtras().getSerializable("objCuenta");

        // Carga Lista giros desde SharedContent
        giros = helper.cargarShared(this,"giros", cuenta.getRut());

        // Establece el monto
        montoGiro = findViewById(R.id.et_montoGiro);
        montoGiro.setText("");
    }

    // Accion giro
    public void giro(View view){
        try {
            // Se obtiene monto desde activity
            String monto = montoGiro.getText().toString();
            // Se realiza giro, si no cumple requisitos, lanza excepcion
            cuenta.girar(monto);
            // Ingresa monto girado al List
            giros.add(helper.getNowString() +" : "+ monto);
            // Convertir list en String
            String girosStr = helper.ArrayListToString(giros);

            // Guarda historial de giros y nuevo monto
            helper.guardarShared(this,girosStr,"giros", cuenta.getRut());
            helper.guardarShared(this,cuenta.getSaldo(),"saldo",cuenta.getRut());
            Toast.makeText(this, "Giro realizado con exito", Toast.LENGTH_LONG).show();
            montoGiro.setText("");
        } catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void volver(View view){
        Intent i = new Intent(this, Operaciones.class);
        i.putExtra("objCuenta",cuenta);
        startActivity(i);
    }

    public void verHistorialGiros(View view){
        Intent i = new Intent(this, Historial.class);
        i.putStringArrayListExtra("historial", giros);
        i.putExtra("tipo_transaccion","Giros");
        startActivity(i);

    }

}