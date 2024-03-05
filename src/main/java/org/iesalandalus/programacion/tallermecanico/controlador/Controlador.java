package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Controlador {
    private final Modelo modelo;
    private final Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        Objects.requireNonNull(modelo,"El modelo es nulo");
        Objects.requireNonNull(vista,"La vista es nulo");
        this.modelo = modelo;
        this.vista = vista;
        vista.setControlador(this);
    }

    public void comenzar() throws OperationNotSupportedException {
        modelo.comenzar();
        vista.comenzar();
    }
    public void terminar() {
        modelo.terminar();
        vista.terminar();
    }

    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        modelo.insertar(cliente);

    }
    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        modelo.insertar(vehiculo);
    }
    public void  insertar(Revision revision) throws OperationNotSupportedException {
        modelo.insertar(revision);
    }

    public void buscar(Cliente cliente){
        modelo.buscar(cliente);
    }
    public void buscar(Vehiculo vehiculo){
        modelo.buscar(vehiculo);
    }
    public void buscar(Revision revision) throws OperationNotSupportedException {
        modelo.borrar(revision);
    }

    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        return modelo.modificar(cliente,nombre,telefono);
    }

    public void anadirHoras(Revision revision,int horas) throws OperationNotSupportedException {
        modelo.anadirHoras(revision,horas);
    }

    public void anadirPrecioMaterial(Revision revision, float precioMaterial) throws OperationNotSupportedException {
        modelo.anadirPrecioMaterial(revision,precioMaterial);
    }

    public void cerrar(Revision revision, LocalDate fechaFin) throws OperationNotSupportedException {
        modelo.cerrar(revision, fechaFin);
    }

    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        modelo.borrar(cliente);
    }
    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        modelo.borrar(vehiculo);
    }
    public void borrar(Revision revision) throws OperationNotSupportedException {
        modelo.borrar(revision);
    }

    public List<Cliente> getClientes(){
        List<Cliente> clientes = modelo.getClientes();
        System.out.println(clientes);
        return clientes;
    }
    public List<Vehiculo> getVehiculos(){
        List<Vehiculo> vehiculos = modelo.getVehiculos();
        System.out.println(vehiculos);
        return vehiculos;
    }

    public List<Revision> getRevisiones(){
        List<Revision> revisiones = modelo.getRevisiones();
        System.out.println(revisiones);
        return  revisiones;
    }
    public List<Revision> getRevisiones(Cliente cliente){
        List<Revision> revisiones = modelo.getRevisiones(cliente);
        System.out.println("Lista de revisiones para el cliente"+ cliente + "es"+ revisiones);
        return  revisiones;
    }
    public List<Revision> getRevisiones(Vehiculo vehiculo){
        List<Revision> revisiones = modelo.getRevisiones(vehiculo);
        System.out.println("Lista de revisiones para el vehiculo"+ vehiculo + "es"+ revisiones);
        return  revisiones;
    }
}
