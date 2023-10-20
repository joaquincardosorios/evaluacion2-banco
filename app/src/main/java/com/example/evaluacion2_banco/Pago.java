package com.example.evaluacion2_banco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Pago extends AppCompatActivity {

    EditText montoPago, rut, password;
    Cuenta cuenta;
    Helper helper = new Helper();
    private ArrayList<String> pagos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);

        cuenta = (Cuenta) getIntent().getExtras().getSerializable("objCuenta");
        pagos = helper.cargarShared(this,"pagos",cuenta.getRut());
        montoPago = findViewById(R.id.et_montoPago);
        rut = findViewById(R.id.et_rutPago);
        password = findViewById(R.id.et_passwordPago);

    }

    public void pago(View view){
        try {
            String monto = montoPago.getText().toString();
            cuenta.pagar(
                    rut.getText().toString(),
                    password.getText().toString(),
                    monto
            );
            pagos.add(helper.getNowString() +" : "+ monto);
            String pagosStr = helper.ArrayListToString(pagos);
            helper.guardarShared(this,pagosStr,"pagos", cuenta.getRut());
            helper.guardarShared(this,cuenta.getSaldo(),"saldo",cuenta.getRut());

            Toast.makeText(this, "Pago realizado con exito", Toast.LENGTH_LONG).show();
            montoPago.setText("");
            rut.setText("");
            password.setText("");
        } catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void volverPago(View view){
        Intent i = new Intent(this, Operaciones.class);
        i.putExtra("objCuenta",cuenta);
        startActivity(i);
    }

    public void verHistorialPagos(View view){
        Intent i = new Intent(this, Historial.class);
        i.putStringArrayListExtra("historial", pagos);
        i.putExtra("tipo_transaccion","Pagos");
        startActivity(i);

    }
}