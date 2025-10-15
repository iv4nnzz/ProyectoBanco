/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author 9spot
 */
public class CuentaCorriente extends Cuenta {
    private double cupoSobregiro;
    
    public CuentaCorriente(String numero, String nombreDueño, double saldo, 
                          Fecha fechaApertura, double cupoSobregiro) {
        super(numero, nombreDueño, saldo, fechaApertura);
        this.cupoSobregiro = cupoSobregiro;
    }
    
    public CuentaCorriente(String numero, String nombreDueño, double saldo, Fecha fechaApertura) {
        this(numero, nombreDueño, saldo, fechaApertura, 1000000); 
    }
    
    @Override
    public boolean validarRetiro(double monto) {
        return monto > 0 && (this.saldo + this.cupoSobregiro - monto) >= 0;
    }
    
    @Override
    public String imprimirDatos() {
        double cupoDisponible = this.cupoSobregiro + this.saldo;
        return "===== CUENTA CORRIENTE =====\n" +
               toString() + "\n" +
               "Tipo: Corriente\n" +
               "Cupo Sobregiro: $" + String.format("%.2f", cupoSobregiro) + "\n" +
               "Cupo Disponible: $" + String.format("%.2f", cupoDisponible);
    }
    
    @Override
    public String getTipoCuenta() {
        return "Corriente";
    }
    
    public double getCupoSobregiro() {
        return cupoSobregiro;
    }
    
    public void setCupoSobregiro(double cupoSobregiro) {
        this.cupoSobregiro = cupoSobregiro;
    }
}