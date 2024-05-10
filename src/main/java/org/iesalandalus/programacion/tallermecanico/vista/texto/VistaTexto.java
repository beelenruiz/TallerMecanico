package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class VistaTexto implements Vista {
    private final GestorEventos gestorEventos = new GestorEventos(Evento.values());

    @Override
    public LocalDate leerFechaCierre() {
        return Consola.leerFecha("Dime la fecha fin de la revisión");
    }

    @Override
    public GestorEventos getGestorEventos() {
        return gestorEventos;
    }
    @Override
    public void comenzar() {
        Evento aux;
        Consola.mostrarCabecera("------Taller Mecánico------");
        do {
            Consola.mostrarMenu();
            aux = Consola.elegirOpcion();
            ejecutar(aux);
        } while (aux != Evento.SALIR);
    }

    @Override
    public void terminar() {
        System.out.println("Proceso terminado; ¡Hasta la próxima!");
    }

    private void ejecutar(Evento evento) {
        Consola.mostrarCabecera("--- Taller Mecánico---");
        gestorEventos.notificar(evento);
    }

    @Override
    public Cliente leerCliente(){
        return new Cliente(leerNuevoNombre(), Consola.leerCadena("Introduce el dni: "), leerNuevoTelefono());
    }

    @Override
    public Cliente leerClienteDni() {
        String dni = Consola.leerCadena("Introduce el dni del cliente: ");
        return Cliente.get(dni);
    }
    @Override
    public String leerNuevoNombre(){
        String nombre = Consola.leerCadena("Introduce el nuevo nombre: ");
        return nombre;
    }
    public String leerNuevoTelefono(){
        String telefono = Consola.leerCadena("Introduce el nuevo teléfono: ");
        return telefono;
    }

    @Override
    public Vehiculo leerVehiculo(){
        return new Vehiculo(Consola.leerCadena("Introduce la marca: "), Consola.leerCadena("Introduce la matrícula: "), Consola.leerCadena("Introduce el modelo: "));
    }
    @Override
    public Vehiculo leerVehiculoMatricula(){
        String matricula = Consola.leerCadena("Introduce la matrícula del vehículo: ");
        return Vehiculo.get(matricula);
    }

    @Override
    public Trabajo leerRevision(){
        return new Revision(leerCliente(), leerVehiculo(), Consola.leerFecha("Introduce la fecha de inicio: "));
    }
    @Override
    public Trabajo leerMecanico(){
        return new Mecanico(leerCliente(), leerVehiculo(), Consola.leerFecha("Introduce la fecha de inicio: "));
    }
    @Override
    public Trabajo leerTrabajoVehiculo(){
        Trabajo trabajo = Trabajo.get(leerVehiculoMatricula());
        return trabajo;
    }

    @Override
    public int leerHoras(){
        int horas = Consola.leerEntero("Introduce las horas: ");
        return horas;
    }

    @Override
    public float leerPrecioMaterial(){
        float precioMaterial = Consola.leerReal("Itroduce el precio del material: ");
        return precioMaterial;
    }

    @Override
    public LocalDate leerMes() { return Consola.leerFecha("Introduce la fecha correspondiente al mes que quieres visualizar"); }

    @Override
    public void notificarResultado(Evento evento, String texto, boolean exito) {
        if (exito) {
            System.out.println(texto);
        } else {
            System.out.println(evento + "ERROR:" + texto);
        }
    }

    @Override
    public void mostrarCliente(Cliente cliente) {
        System.out.println(cliente);
    }

    @Override
    public void mostrarVehiculo(Vehiculo vehiculo) {
        System.out.println(vehiculo);
    }

    @Override
    public void mostrarTrabajo(Trabajo trabajo) {
        System.out.println(trabajo);
    }

    @Override
    public void mostrarClientes(List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            mostrarCliente(cliente);
        }
    }

    @Override
    public void mostrarVehiculos(List<Vehiculo> vehiculos) {
        for (Vehiculo vehiculo : vehiculos) {
            mostrarVehiculo(vehiculo);
        }
    }

    @Override
    public void mostrarTrabajos(List<Trabajo> trabajos) {
        for (Trabajo trabajo : trabajos) {
            System.out.println(trabajo);
        }
    }

    @Override
    public void mostrarEstadisticasMensuales(Map<TipoTrabajo, Integer> estadisticas) {
        System.out.printf("Tipos de trabajos realizados este mes: %s%n", estadisticas);
    }
}
