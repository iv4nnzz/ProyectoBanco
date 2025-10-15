/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author 9spot
 */
public abstract class Cuenta {
    protected String numero;
    protected String nombreDueño;
    protected double saldo;
    protected Fecha fechaApertura;
    
    public Cuenta(String numero, String nombreDueño, double saldo, Fecha fechaApertura) {
        this.numero = numero;
        this.nombreDueño = nombreDueño;
        this.saldo = saldo;
        this.fechaApertura = fechaApertura;
    }
    
    public void consignar(double monto) {
        if (monto > 0) {
            this.saldo += monto;
        }
    }
    
    public boolean retirar(double monto) {
        if (validarRetiro(monto)) {
            this.saldo -= monto;
            return true;
        }
        return false;
    }
    
    public double obtenerSaldo() {
        return this.saldo;
    }
    
    public abstract boolean validarRetiro(double monto);
    public abstract String imprimirDatos();
    public abstract String getTipoCuenta();
    
    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public String getNombreDueño() {
        return nombreDueño;
    }
    
    public void setNombreDueño(String nombreDueño) {
        this.nombreDueño = nombreDueño;
    }
    
    public double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    public Fecha getFechaApertura() {
        return fechaApertura;
    }
    
    public void setFechaApertura(Fecha fechaApertura) {
        this.fechaApertura = fechaApertura;
    }
    
    @Override
    public String toString() {
        return "Número: " + numero + "\n" +
               "Dueño: " + nombreDueño + "\n" +
               "Saldo: $" + String.format("%.2f", saldo) + "\n" +
               "Fecha Apertura: " + fechaApertura;
    }
}
