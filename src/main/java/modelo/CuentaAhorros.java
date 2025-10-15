/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author 9spot
 */
public class CuentaAhorros extends Cuenta {
    private static final double SALDO_MINIMO = 0;
    
    public CuentaAhorros(String numero, String nombreDueño, double saldo, Fecha fechaApertura) {
        super(numero, nombreDueño, saldo, fechaApertura);
    }
    
    @Override
    public boolean validarRetiro(double monto) {
        return monto > 0 && (this.saldo - monto) >= SALDO_MINIMO;
    }
    
    @Override
    public String imprimirDatos() {
        return "===== CUENTA DE AHORROS =====\n" +
               toString() + "\n" +
               "Tipo: Ahorros\n" +
               "Saldo Mínimo: $" + SALDO_MINIMO;
    }
    
    @Override
    public String getTipoCuenta() {
        return "Ahorros";
    }
}