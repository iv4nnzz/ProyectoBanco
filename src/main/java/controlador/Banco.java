/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import modelo.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 9spot
 */

public class Banco {
    private ArrayList<Cuenta> cuentas;
    
    public Banco() {
        this.cuentas = new ArrayList<>();
    }
    
    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }
    
    public Cuenta buscarCuenta(String numero) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getNumero().equals(numero)) {
                return cuenta;
            }
        }
        return null;
    }
    
    public boolean realizarConsignacion(String numeroCuenta, double monto) {
        Cuenta cuenta = buscarCuenta(numeroCuenta);
        if (cuenta != null && monto > 0) {
            cuenta.consignar(monto);
            return true;
        }
        return false;
    }
    
    public boolean realizarRetiro(String numeroCuenta, double monto) {
        Cuenta cuenta = buscarCuenta(numeroCuenta);
        if (cuenta != null) {
            return cuenta.retirar(monto);
        }
        return false;
    }
    
    public double consultarSaldo(String numeroCuenta) {
        Cuenta cuenta = buscarCuenta(numeroCuenta);
        if (cuenta != null) {
            return cuenta.obtenerSaldo();
        }
        return -1; 
    }
    
    public String obtenerDatosCuenta(String numeroCuenta) {
        Cuenta cuenta = buscarCuenta(numeroCuenta);
        if (cuenta != null) {
            return cuenta.imprimirDatos();
        }
        return null;
    }
    
    public String listarCuentas() {
        if (cuentas.isEmpty()) {
            return "No hay cuentas registradas";
        }
        
        StringBuilder lista = new StringBuilder("===== LISTA DE CUENTAS =====\n");
        for (Cuenta cuenta : cuentas) {
            lista.append("\n").append(cuenta.getNumero())
                 .append(" - ").append(cuenta.getNombreDueño())
                 .append(" (").append(cuenta.getTipoCuenta()).append(")\n");
        }
        return lista.toString();
    }
    
    public ArrayList<Cuenta> getCuentas() {
        return cuentas;
    }
}
