package com.example.evaluacion2_banco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Deposito extends AppCompatActivity {

    EditText montoDeposito;
    Cuenta cuenta;
    Helper helper = new Helper();
    private ArrayList<String> depositos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito);

        cuenta = (Cuenta) getIntent().getExtras().getSerializable("objCuenta");
        depositos = helper.cargarShared(this, "depositos", cuenta.getRut());
        montoDeposito = findViewById(R.id.et_montoDeposito);
        montoDeposito.setText("");
    }

    public void deposito(View view){
        try {
            String monto = montoDeposito.getText().toString();
            cuenta.depositar(monto);
            depositos.add(helper.getNowString() +" : "+ monto);
            String depositosStr = helper.ArrayListToString(depositos);
            helper.guardarShared(this,depositosStr,"depositos", cuenta.getRut());
            helper.guardarShared(this,cuenta.getSaldo(),"saldo",cuenta.getRut());
            Toast.makeText(this, "Giro deposito con exito", Toast.LENGTH_LONG).show();
            montoDeposito.setText("");
        } catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void volverDeposito(View view){
        Intent i = new Intent(this, Operaciones.class);
        i.putExtra("objCuenta",cuenta);
        startActivity(i);
    }

    public void verHistorialDepositos(View view){
        Intent i = new Intent(this, Historial.class);
        i.putStringArrayListExtra("historial", depositos);
        i.putExtra("tipo_transaccion","Depósito");
        startActivity(i);

    }
}