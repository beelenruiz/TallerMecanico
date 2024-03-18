package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;

public class VistaTexto {
    private GestorEventos gestorEventos;
    private Controlador controlador;
    Consola consola;
    public GestorEventos getGestorEventos(){
        
    }

    public  void comenzar() throws OperationNotSupportedException {
        consola.mostrarCabecera("--- Taller mecánico ---");
        Evento evento;
        do {
            consola.mostrarMenu();
            evento = consola.elegirOpcion();
            ejecutar(evento);
        }while (evento != Evento.SALIR);

    }
    public  void terminar(){
        System.out.println("Fin");
    }

    private void ejecutar(Evento evento) throws OperationNotSupportedException {
        consola.mostrarCabecera("---- TALLER MECÁNICO ----");
        gestorEventos.notificar();
    }


    public Cliente leerCliente(){
        return new Cliente(consola.leerCadena("Introduce el nombre: "), consola.leerCadena("Itroduce el dni: "), consola.leerCadena("Introduce el teléfono: "));
    }

    public Cliente leerClienteDni() {
        String dni = consola.leerCadena("Introduce el dni del cliente: ");
        return Cliente.get(dni);
    }
    public String leerNuevoNombre(){
        String nombre = consola.leerCadena("Introduce el nuevo nombre: ");
        return nombre;
    }
    public String leerNuevoTelefono(){
        String telefono = consola.leerCadena("Introduce el nuevo teléfono: ");
        return telefono;
    }

    public Vehiculo leerVehiculo(){
        return new Vehiculo(consola.leerCadena("Introduce la marca: "), consola.leerCadena("Introduce la matrícula: "), consola.leerCadena("Introduce el modelo: "));
    }
    public Vehiculo leerVehiculoMatricula(){
        String matricula = consola.leerCadena("Introduce la matrícula del vehículo: ");
        return Vehiculo.get(matricula);
    }

    public Trabajo leerRevision(){
        return new Revision(leerCliente(), leerVehiculo(), consola.leerFecha("Introduce la fecha de inicio: "));
    }
    public Trabajo leerMecanico(){
        return new Mecanico(leerCliente(), leerVehiculo(), consola.leerFecha("Introduce la fecha de inicio: "));
    }
    public Trabajo leerTrabajoVehiculo(){
        Vehiculo vehiculo = leerVehiculo();
        Trabajo trabajo = null;
        if (vehiculo instanceof Mecanico mecanico){
            trabajo = leerMecanico();
        } else if (vehiculo instanceof Revision revision) {
            trabajo = leerRevision();
        }
        return trabajo;
    }

    public int leerHoras(){
        int horas = consola.leerEntero("Introduce las horas: ");
        return horas;
    }

    public float leerPrecioMaterial(){
        float precioMaterial = consola.leerReal("Itroduce el precio del material: ");
        return precioMaterial;
    }

    public LocalDate leerFechaCierre(){
        LocalDate fecha = consola.leerFecha("Introduce la fecha de cierre: ");
        return fecha;
    }

    
}
