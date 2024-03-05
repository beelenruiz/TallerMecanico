package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Clientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Revisiones;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Vehiculos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Modelo {
    Clientes clientes;
    Vehiculos vehiculos;
    Revisiones revisiones;
    public Modelo(){
        comenzar();
    }
    public void comenzar(){
        clientes = new Clientes();
        vehiculos = new Vehiculos();
        revisiones = new Revisiones();
    }
    public void terminar(){
        System.out.println("El modelo ha terminado");
    }

    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede insertar un cliente nulo.");
        clientes.insertar(new Cliente(cliente));
    }
    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        vehiculos.insertar(vehiculo);
    }
    public void insertar(Revision revision) throws OperationNotSupportedException {
        Cliente cliente = clientes.buscar(revision.getCliente());
        Vehiculo vehiculo = vehiculos.buscar(revision.getVehiculo());
        revisiones.insertar(new Revision(cliente, vehiculo, revision.getFechaInicio()));
    }

    public Cliente buscar(Cliente cliente){
        cliente = Objects.requireNonNull(clientes.buscar(cliente), "No existe un cliente igual");
        return new Cliente(cliente);
    }
    public Vehiculo buscar(Vehiculo vehiculo){
        vehiculo = Objects.requireNonNull(vehiculos.buscar(vehiculo), "No existe un vehículo igual");
        return vehiculo;
    }
    public Revision buscar(Revision revision){
        revision = Objects.requireNonNull(revisiones.buscar(revision), "No existe una revisión igual");
        return new Revision(revision);
    }

    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        return clientes.modificar(cliente, nombre, telefono);
    }

    public void anadirHoras(Revision revision, int horas) throws OperationNotSupportedException {
        revisiones.anadirHoras(revision, horas);
    }
    public void anadirPrecioMaterial(Revision revision, float precioMaterial) throws OperationNotSupportedException {
        revisiones.anadirPrecioMaterial(revision, precioMaterial);
    }

    public void cerrar(Revision revision, LocalDate fechaFin) throws OperationNotSupportedException {
        revisiones.cerrar(revision, fechaFin);
    }

    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        List<Revision> revisionesCliente = revisiones.get(cliente);
        for (Revision revision : revisionesCliente) {
            revisiones.borrar(revision);
        }
        clientes.borrar(cliente);
    }
    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        List<Revision> revisionesVehiculo = revisiones.get(vehiculo);
        for (Revision revision : revisionesVehiculo) {
            revisiones.borrar(revision);
        }
        vehiculos.borrar(vehiculo);
    }
    public void borrar(Revision revision) throws OperationNotSupportedException {
        revisiones.borrar(revision);
    }

    public List<Cliente> getClientes(){
        List<Cliente> listaTemporal = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            listaTemporal.add(new Cliente(cliente));
        }
        return listaTemporal;
    }
    public List<Vehiculo> getVehiculos(){
        return vehiculos.get();
    }
    public List<Revision> getRevisiones(){
        List<Revision> listaTemporal = new ArrayList<>();
        for (Revision revision : revisiones.get()) {
            listaTemporal.add(new Revision(revision));
        }
        return listaTemporal;
    }
    public List<Revision> getRevisiones(Cliente cliente){
        List<Revision> listaTemporal = new ArrayList<>();
        for (Revision revisionCliente : revisiones.get(cliente)) {
            listaTemporal.add(new Revision(revisionCliente));
        }
        return listaTemporal;
    }
    public List<Revision> getRevisiones(Vehiculo vehiculo){
        List<Revision> listaTemporal = new ArrayList<>();
        for (Revision revisionCliente : revisiones.get(vehiculo)) {
            listaTemporal.add(new Revision(revisionCliente));
        }
        return listaTemporal;
    }
}
