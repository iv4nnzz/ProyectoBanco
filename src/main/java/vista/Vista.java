  /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author 9spot
 */
import controlador.Banco;
import modelo.*;
import javax.swing.JOptionPane;
import java.util.Calendar;

public class Vista {
    private Banco banco;
    
    public Vista() {
        this.banco = new Banco();
    }
    
    public void mostrarMenuPrincipal() {
        boolean continuar = true;
        
        while (continuar) {
            String[] opciones = {"Crear Cuenta", "Operaciones", "Listar Cuentas", "Salir"};
            int seleccion = JOptionPane.showOptionDialog(
                null,
                "SISTEMA BANCARIO\nSeleccione una opción:",
                "Menú Principal",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]
            );
            
            switch (seleccion) {
                case 0:
                    crearCuenta();
                    break;
                case 1:
                    menuOperaciones();
                    break;
                case 2:
                    mostrarMensaje(banco.listarCuentas());
                    break;
                case 3:
                case -1: 
                    continuar = false;
                    mostrarMensaje("Gracias por usar el Sistema Bancario");
                    break;
            }
        }
    }
    
    private void crearCuenta() {
        String[] tipos = {"Cuenta de Ahorros", "Cuenta Corriente"};
        int tipoCuenta = JOptionPane.showOptionDialog(
            null,
            "Seleccione el tipo de cuenta:",
            "Crear Cuenta",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            tipos,
            tipos[0]
        );
        
        if (tipoCuenta == -1) return; 
        
        String numero = solicitarDato("Ingrese el número de cuenta:");
        if (numero == null || numero.trim().isEmpty()) return;
        
        if (banco.buscarCuenta(numero) != null) {
            mostrarError("Ya existe una cuenta con ese número");
            return;
        }
        
        String nombre = solicitarDato("Ingrese el nombre del dueño:");
        if (nombre == null || nombre.trim().isEmpty()) return;
        
        double saldoInicial = solicitarNumero("Ingrese el saldo inicial:");
        if (saldoInicial < 0) return;
        
        Calendar cal = Calendar.getInstance();
        Fecha fechaApertura = new Fecha(
            cal.get(Calendar.DAY_OF_MONTH),
            cal.get(Calendar.MONTH) + 1,
            cal.get(Calendar.YEAR)
        );
        
        Cuenta nuevaCuenta = null;
        
        if (tipoCuenta == 0) { 
            nuevaCuenta = new CuentaAhorros(numero, nombre, saldoInicial, fechaApertura);
        } else { 
            double cupoSobregiro = solicitarNumero("Ingrese el cupo de sobregiro:");
            if (cupoSobregiro < 0) return;
            nuevaCuenta = new CuentaCorriente(numero, nombre, saldoInicial, fechaApertura, cupoSobregiro);
        }
        
        banco.agregarCuenta(nuevaCuenta);
        mostrarMensaje("Cuenta creada exitosamente!\n\n" + nuevaCuenta.imprimirDatos());
    }
    
    private void menuOperaciones() {
        String numeroCuenta = solicitarDato("Ingrese el número de cuenta:");
        if (numeroCuenta == null || numeroCuenta.trim().isEmpty()) return;
        
        Cuenta cuenta = banco.buscarCuenta(numeroCuenta);
        if (cuenta == null) {
            mostrarError("No se encontró la cuenta con número: " + numeroCuenta);
            return;
        }
        
        boolean continuar = true;
        while (continuar) {
            String[] opciones = {"Consignar", "Retirar", "Consultar Saldo", 
                                "Ver Información", "Volver"};
            int seleccion = JOptionPane.showOptionDialog(
                null,
                "Cuenta: " + numeroCuenta + "\nSeleccione una operación:",
                "Operaciones Bancarias",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]
            );
            
            switch (seleccion) {
                case 0: 
                    realizarConsignacion(numeroCuenta);
                    break;
                case 1: 
                    realizarRetiro(numeroCuenta);
                    break;
                case 2: 
                    consultarSaldo(numeroCuenta);
                    break;
                case 3: 
                    verInformacion(numeroCuenta);
                    break;
                case 4: 
                case -1:
                    continuar = false;
                    break;
            }
        }
    }
    
    private void realizarConsignacion(String numeroCuenta) {
        double monto = solicitarNumero("Ingrese el monto a consignar:");
        if (monto <= 0) {
            mostrarError("El monto debe ser mayor a cero");
            return;
        }
        
        if (banco.realizarConsignacion(numeroCuenta, monto)) {
            mostrarMensaje("Consignación exitosa!\nNuevo saldo: $" + 
                         String.format("%.2f", banco.consultarSaldo(numeroCuenta)));
        } else {
            mostrarError("Error al realizar la consignación");
        }
    }
    
    private void realizarRetiro(String numeroCuenta) {
        double monto = solicitarNumero("Ingrese el monto a retirar:");
        if (monto <= 0) {
            mostrarError("El monto debe ser mayor a cero");
            return;
        }
        
        if (banco.realizarRetiro(numeroCuenta, monto)) {
            mostrarMensaje("Retiro exitoso!\nNuevo saldo: $" + 
                         String.format("%.2f", banco.consultarSaldo(numeroCuenta)));
        } else {
            mostrarError("Fondos insuficientes para realizar el retiro");
        }
    }
    
    private void consultarSaldo(String numeroCuenta) {
        double saldo = banco.consultarSaldo(numeroCuenta);
        mostrarMensaje("Saldo actual: $" + String.format("%.2f", saldo));
    }
    
    private void verInformacion(String numeroCuenta) {
        String info = banco.obtenerDatosCuenta(numeroCuenta);
        mostrarMensaje(info);
    }
    
    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Información", 
                                     JOptionPane.INFORMATION_MESSAGE);
    }
    
    private String solicitarDato(String mensaje) {
        return JOptionPane.showInputDialog(null, mensaje, "Entrada de Datos", 
                                           JOptionPane.QUESTION_MESSAGE);
    }
    
    private double solicitarNumero(String mensaje) {
        String entrada = solicitarDato(mensaje);
        if (entrada == null) return -1;
        
        try {
            return Double.parseDouble(entrada);
        } catch (NumberFormatException e) {
            mostrarError("Debe ingresar un número válido");
            return -1;
        }
    }
    
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", 
                                     JOptionPane.ERROR_MESSAGE);
    }
}