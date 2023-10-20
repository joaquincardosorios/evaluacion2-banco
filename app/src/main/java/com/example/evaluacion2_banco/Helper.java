package com.example.evaluacion2_banco;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class Helper {
    public String getNowString(){
        Date dateNow = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
        String formatedNow = format.format(dateNow);
        return formatedNow;
    }

    public void guardarShared(Context context, Object param, String tipo, String key){
        SharedPreferences prefe = context.getSharedPreferences(tipo, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=prefe.edit();
        if (param instanceof Integer) {
            int paramN = (Integer) param;
            editor.putInt(key, paramN);
        } else  {
            String paramN = (String) param;
            editor.putString(key, paramN);
        }
        editor.commit();
    }

    public String cargarSharedStr(Context context, String tipo, String key){
        SharedPreferences prefe = context.getSharedPreferences(tipo, Context.MODE_PRIVATE);
        String dato = prefe.getString(key, "");
        return dato;
    }

    public int cargarSharedInt(Context context, String tipo, String key){
        SharedPreferences prefe = context.getSharedPreferences(tipo, Context.MODE_PRIVATE);
        int dato = prefe.getInt(key, 0);
        return dato;
    }


    public ArrayList<String> cargarShared(Context context, String tipo, String key){
        ArrayList<String> transaccion = new ArrayList<String>();
        String datosGuardados = cargarSharedStr(context, tipo, key);
        if(!datosGuardados.equals("")){
            String[] girosSeparados = datosGuardados.split("#");
            for(String giro : girosSeparados){
                transaccion.add(giro);
            }
        }
        return transaccion;
    }

    public String ArrayListToString(ArrayList<String> lista){
        StringBuilder builder = new StringBuilder();
        for (String elemento : lista){
            builder.append(elemento);
            builder.append('#');
        }
        if (builder.length() > 0) {
            builder.setLength(builder.length() - 1);
        }
        String listString = builder.toString();
        return listString;
    }
}
