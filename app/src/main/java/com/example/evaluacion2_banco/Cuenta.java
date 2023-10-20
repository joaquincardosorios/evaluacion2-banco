package com.example.evaluacion2_banco;

import java.io.Serializable;


public class Cuenta implements Serializable {
    private String rut;
    private String apellidos;
    private String nombres;
    private int saldo;
    private String numeroSecreto;

    public  Cuenta(){}


    public Cuenta(String rut, String apellidos, String nombres, int saldo, String numeroSecreto) {
        this.rut = rut;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.saldo = saldo;
        this.numeroSecreto = numeroSecreto;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getNumeroSecreto() {
        return numeroSecreto;
    }

    public void setNumeroSecreto(String numeroSecreto) {
        this.numeroSecreto = numeroSecreto;
    }

    public void girar(String montoStr) throws Exception {
        int monto;
        try{
            monto = Integer.parseInt(montoStr);
        } catch (NumberFormatException e){
            monto = 0;
        }
        if(monto <= 0){
            throw new Exception("Monto debe ser mayor a cero");
        }

        if(monto > this.saldo){
            throw  new Exception("Monto no puede ser mayor al saldo");
        }
        this.saldo -= monto;
    }

    public void depositar(String montoStr) throws Exception {
        int monto;
        try{
            monto = Integer.parseInt(montoStr);
        } catch (NumberFormatException e){
            monto = 0;
        }
        if(monto <= 0){
            throw new Exception("Monto debe ser mayor a cero");
        }
        this.saldo += monto;
    }

    public  void pagar(String rut, String password, String montoStr) throws Exception {
        int monto;
        try{
            monto = Integer.parseInt(montoStr);
        } catch (NumberFormatException e){
            monto = 0;
        }
        if(!rut.equals(this.rut)){
            throw new Exception("Rut no valido");
        }
        if(!password.equals(this.numeroSecreto)){
            throw  new Exception("Numero secreto no valido");
        }
        if(monto < 0){
            throw new Exception("Monto no puede ser menor a cero");
        }
        if(monto > this.saldo){
            throw new Exception("Monto no puede ser superior al saldo");
        }
        this.saldo -= monto;
    }

    public void validarRegistro(String rutV, String nom, String aps, String sald, String pass,String passR) throws Exception{
        if(rutV.equals("") ||
                nom.equals("") ||
                aps.equals("") ||
                sald.equals("") ||
                pass.equals("") ||
                passR.equals("")){
            throw new Exception("Todos los campos son obligatorios");
        }

        if(pass.length() < 6){
            throw new Exception("La contraseña debe tener al menos 6 caracteres");
        }

        if(!pass.equals(passR)){
            throw new Exception("Las contraseñas no coinciden");
        }
        this.setRut(rutV);
        this.setNombres(nom);
        this.setApellidos(aps);
        this.setSaldo(Integer.parseInt(sald));
        this.setNumeroSecreto(pass);
    }

    public void validarLogin(String pass) throws Exception{
        if(!this.getNumeroSecreto().equals(pass)){
            throw new Exception("Contraseña Incorrecta");
        }
    }
}

