package com.example.evaluacion2_banco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Operaciones extends AppCompatActivity {
    EditText saldo;
    TextView bienvenida;
    Cuenta cuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones);

        cuenta = (Cuenta) getIntent().getExtras().getSerializable("objCuenta");
        bienvenida  = findViewById(R.id.tv_bienvenida);
        saldo = findViewById(R.id.et_SaldoOp);

        bienvenida.setText("Bienvenido " + cuenta.getNombres());
        saldo.setText("$ " + cuenta.getSaldo());
    }

    public void giros(View view){
        Intent i = new Intent(this, Giro.class);
        i.putExtra("objCuenta",cuenta);
        startActivity(i);
    }

    public void depositos(View view){
        Intent i = new Intent(this, Deposito.class);
        i.putExtra("objCuenta",cuenta);
        startActivity(i);
    }

    public void pagos(View view){
        Intent i = new Intent(this, Pago.class);
        i.putExtra("objCuenta",cuenta);
        startActivity(i);
    }

    public void cerrar(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
