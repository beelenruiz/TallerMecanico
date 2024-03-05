package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Consola {
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Consola(){
    }

    public void mostrarCabecera(String mensaje){
        System.out.println(mensaje);
        String subrayado = String.format(String.format("%%0%dd", mensaje.length()), 0).replace("0","_");
        System.out.println(subrayado);
    }
    public void mostrarMenu(){
        System.out.println("Opciones del sistema:");
        for (Opcion opcion: Opcion.values()) {
            System.out.println(opcion);
        }
    }

    public Opcion elegirOpcion() {
        int numeroOpcion;
        do {
            numeroOpcion = leerEntero("Indica la opcion a realizar: ");
        } while (!Opcion.esValida(numeroOpcion));
        return Opcion.get(numeroOpcion);
    }

    private int leerEntero(String mensaje){
        System.out.println(mensaje);
        int entero;
        return entero = Entrada.entero();
    }
    private float leerReal(String mensaje){
        System.out.println(mensaje);
        float real;
        return real = Entrada.real();
    }
    private String leerCadena(String mensaje){
        System.out.println(mensaje);
        String cadena;
        return cadena = Entrada.cadena();
    }
    private LocalDate leerFecha(String mensaje){
        System.out.println(mensaje);
        LocalDate fecha;
        return fecha= LocalDate.parse(Entrada.cadena(), FORMATO_FECHA);
    }

    public Cliente leerCliente(){
        return new Cliente(leerCadena("Introduce el nombre: "), leerCadena("Itroduce el dni: "), leerCadena("Introduce el teléfono: "));
    }

    public Cliente leerClienteDni() {
        String dni = leerCadena("Introduce el dni del cliente: ");
        return Cliente.get(dni);
    }
    public String leerNuevoNombre(){
        String nombre = leerCadena("Introduce el nuevo nombre: ");
        return nombre;
    }
    public String leerNuevoTelefono(){
        String telefono = leerCadena("Introduce el nuevo teléfono: ");
        return telefono;
    }

    public Vehiculo leerVehiculo(){
        return new Vehiculo(leerCadena("Introduce la marca: "), leerCadena("Introduce la matrícula: "), leerCadena("Introduce el modelo: "));
    }
    public Vehiculo leerVehiculoMatricula(){
        String matricula = leerCadena("Introduce la matrícula del vehículo: ");
        return Vehiculo.get(matricula);
    }

    public Revision leerRevision(){
        return new Revision(leerCliente(), leerVehiculo(), leerFecha("Introduce la fecha de inicio: "));
    }

    public int leerHoras(){
        int horas = leerEntero("Introduce las horas: ");
        return horas;
    }

    public float leerPrecioMaterial(){
        float precioMaterial = leerReal("Itroduce el precio del material: ");
        return precioMaterial;
    }

    public LocalDate leerFechaCierre(){
        LocalDate fecha = leerFecha("Introduce la fecha de cierre: ");
        return fecha;
    }
}
