package com.example.evaluacion2_banco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText et_rut, et_nombres, et_apellidos, et_password, et_passwordR ,et_saldo;
    Button btn_login, btn_registro;
    Helper helper = new Helper();
    Cuenta cuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Relacion de variables con elementos de la vista
        et_rut = findViewById(R.id.et_rut);
        et_nombres = findViewById(R.id.et_nombres);
        et_apellidos = findViewById(R.id.et_apellidos);
        et_password = findViewById(R.id.et_password);
        et_passwordR = findViewById(R.id.et_passwordRep);
        et_saldo = findViewById(R.id.et_saldo);
        btn_login = findViewById(R.id.btn_login);
        btn_registro = findViewById(R.id.btn_registrar);

        // Funcion que se ejecuta al salir del EditText de rut
        et_rut.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String rutIngresado = et_rut.getText().toString();

                    // Trae todos los rut guardados en SharedPreferences y los guarda en un arreglo de strings
                    SharedPreferences preferencias = getSharedPreferences("ruts", Context.MODE_PRIVATE);
                    String valorGuardado = preferencias.getString("rut", "");
                    String[] rutsGuardados = valorGuardado.split(",");

                    // Verifica si existe coincidencia entre el rut ingresado y alguno de los que estan guardados
                    boolean rutCoincide = false;
                    for (String rut : rutsGuardados) {
                        if (rutIngresado.equals(rut)) {
                            rutCoincide = true;
                            break;
                        }
                    }

                    // Si existe coincidencia, rellena los campos con los datos del usuario, se habilita el boton de Login y se desaparece el campo de repetir contraseña
                     if (rutCoincide) {
                         //este metodo crea la instancia con los datos obtenidos del Storage
                        obtenerDatos(rutIngresado);
                        et_nombres.setText(cuenta.getNombres());
                        et_nombres.setEnabled(false);
                        et_apellidos.setText(cuenta.getApellidos());
                        et_apellidos.setEnabled(false);
                        et_saldo.setText(String.valueOf(cuenta.getSaldo()));
                        et_saldo.setEnabled(false);
                        et_passwordR.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                        btn_registro.setVisibility(View.GONE);
                        et_rut.setError(null);
                        et_nombres.setError(null);
                        et_apellidos.setError(null);
                        et_saldo.setError(null);
                        et_password.setError(null);
                        et_passwordR.setError(null);
                    } else{
                         // en caso de no existir coincidencias, se limpian los campos y se deja en modo registro
                         et_nombres.setText("");
                         et_apellidos.setText("");
                         et_saldo.setText("");
                         et_passwordR.setVisibility(View.VISIBLE);
                         btn_login.setVisibility(View.GONE);
                         btn_registro.setVisibility(View.VISIBLE);
                     }
                }
            }
        });
    }
    // Accion del boton registrar
    public void registrar(View view){
        try {
            // Trae los datos de los campos
            String rut = et_rut.getText().toString();
            String nom = et_nombres.getText().toString();
            String aps = et_apellidos.getText().toString();
            String sald = et_saldo.getText().toString();
            String pass = et_password.getText().toString();
            String passR = et_passwordR.getText().toString();

            // LLamada a metodo para validar los campos
            boolean validador = validarCampos(rut, nom, aps, sald, pass, passR);
            if(!validador){
                throw new Exception("Hubo un error");
            }

            // Se crea una instancia
            cuenta = new Cuenta();

            // Se genera validacion interna de los datos ingresados, se ser valido settea los valores en la instancia
            cuenta.validarRegistro(rut, nom, aps, sald, pass, passR);

            // guarda contenido del usuario en en Storage
            guardarDatos(rut, aps, nom, Integer.parseInt(sald), pass);

            // Aviso de que todito salio bien
            Toast.makeText(this, "Registro Existoso", Toast.LENGTH_LONG).show();

            // Ir a Operaciones
            Intent i = new Intent(this, Operaciones.class);
            i.putExtra("objCuenta",cuenta);
            startActivity(i);
        } catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    // Accion del boton Login
    public void login(View view){
        try {
            // Verifica que la contraseña no este vacia
            String pass = et_password.getText().toString();
            if(pass.isEmpty() || pass.equals(null)){
                et_rut.setError("Rut es Oblidatorio");
            }

            // Validacion de login por parte de la clase
            cuenta.validarLogin(pass);

            // Aviso y envio a Operaciones
            Toast.makeText(this, "Contectado", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, Operaciones.class);
            i.putExtra("objCuenta",cuenta);
            startActivity(i);

        } catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void obtenerDatos(String rutIngresado){
        // Carga de datos del Storage
        String noms = helper.cargarSharedStr(this, "nombres",rutIngresado );
        String aps = helper.cargarSharedStr(this, "apellidos",rutIngresado );
        int sald = helper.cargarSharedInt(this, "saldo",rutIngresado );
        String pass = helper.cargarSharedStr(this, "password",rutIngresado );

        // Se crea la instancia
        cuenta = new Cuenta(rutIngresado,noms,aps,sald,pass );
    }


    private void guardarDatos(String rut, String aps, String nom, int sald, String pass){

        //Guarda los datos en el Storage
        helper.guardarShared(this,rut,'ruts', 'rut');
        helper.guardarShared(this,nom,'nombres',rut);
        helper.guardarShared(this,aps,'apellidos',rut);
        helper.guardarShared(this,sald,'saldo',rut);
        helper.guardarShared(this,pass,"password",rut);

        // borrar el contenido de los campos de texto
        et_nombres.setText("");
        et_apellidos.setText("");
        et_rut.setText("");
        et_saldo.setText("");
        et_password.setText("");
        et_passwordR.setText("");
    }
    //Valida campos y genera error en vista
    private boolean validarCampos(String rut, String nom, String aps, String sald, String pass, String passR){
        boolean valid = true;

        if (rut.isEmpty()) {
            et_rut.setError("Rut es Obligatorio");
            valid = false;
        }

        if (nom.isEmpty()) {
            et_nombres.setError("Nombres son Obligatorios");
            valid = false;
        }

        if (aps.isEmpty()) {
            et_apellidos.setError("Apellidos son Obligatorios");
            valid = false;
        }

        if (sald.isEmpty()) {
            et_saldo.setError("Saldo es Obligatorio");
            valid = false;
        }

        if (pass.isEmpty()) {
            et_password.setError("Password es Obligatorio");
            valid = false;
        }

        if (passR.isEmpty()) {
            et_passwordR.setError("Se requiere repetir el password");
            valid = false;
        }

        if (pass.length() < 6) {
            et_password.setError("Password no puede ser inferior a 6 caracteres");
            valid = false;
        }

        if (!pass.equals(passR)) {
            et_password.setError("Las contraseñas no coinciden");
            et_passwordR.setError("Las contraseñas no coinciden");
            valid = false;
        }
        return valid;
    }
}

