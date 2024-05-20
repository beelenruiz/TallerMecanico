package org.iesalandalus.programacion.tallermecanico.modelo.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.*;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ModeloCascada implements org.iesalandalus.programacion.tallermecanico.modelo.Modelo {
    IClientes clientes;
    IVehiculos vehiculos;
    ITrabajos trabajos;

    public ModeloCascada(FabricaFuenteDatos fabricaFuenteDatos){
        Objects.requireNonNull(fabricaFuenteDatos, "La fabrica de la fuente de datos no puede ser nula.");
        IFuenteDatos fuenteDatos = fabricaFuenteDatos.crear();
        clientes = fuenteDatos.crearClientes();
        vehiculos = fuenteDatos.crearVehiculos();
        trabajos = fuenteDatos.crearTrabajos();
    }

    @Override
    public void comenzar() {
        clientes.comenzar();
        vehiculos.comenzar();
        trabajos.comenzar();
        System.out.println("Modelo comenzado.");
    }
    @Override
    public void terminar() {
        trabajos.terminar();
        vehiculos.terminar();
        clientes.terminar();
        System.out.println("Modelo terminado.");
    }

    @Override
    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede insertar un cliente nulo.");
        clientes.insertar(new Cliente(cliente));
    }
    @Override
    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        vehiculos.insertar(vehiculo);
    }
    @Override
    public void insertar(Trabajo trabajo) throws OperationNotSupportedException {
        Cliente cliente = clientes.buscar(trabajo.getCliente());
        Vehiculo vehiculo = vehiculos.buscar(trabajo.getVehiculo());
        if (trabajo instanceof Mecanico) {
            trabajos.insertar(new Mecanico(cliente, vehiculo, trabajo.getFechaInicio()));
        } else if (trabajo instanceof Revision) {
            trabajos.insertar(new Revision(cliente, vehiculo, trabajo.getFechaInicio()));
        }
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        System.out.println("buscando modelo cascada");
        cliente = Objects.requireNonNull(clientes.buscar(cliente), "No existe un cliente igual");
        return new Cliente(cliente);
    }
    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        vehiculo = Objects.requireNonNull(vehiculos.buscar(vehiculo), "No existe un vehículo igual");
        return vehiculo;
    }
    @Override
    public Trabajo buscar(Trabajo trabajo) {
        trabajo = Objects.requireNonNull(trabajos.buscar(trabajo), "No existe una revisión igual");
        return Trabajo.copiar(trabajo);
    }

    @Override
    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        return clientes.modificar(cliente, nombre, telefono);
    }

    @Override
    public void anadirHoras(Trabajo trabajo, int horas) throws OperationNotSupportedException {
        trabajos.anadirHoras(trabajo, horas);
    }
    @Override
    public void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws OperationNotSupportedException {
        trabajos.anadirPrecioMaterial(trabajo, precioMaterial);
    }

    @Override
    public void cerrar(Trabajo trabajo, LocalDate fechaFin) throws OperationNotSupportedException {
        trabajos.cerrar(trabajo, fechaFin);
    }

    @Override
    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        List<Trabajo> trabajosCliente = trabajos.get(cliente);
        for (Trabajo trabajo : trabajosCliente) {
            trabajos.borrar(trabajo);
        }
        clientes.borrar(cliente);
    }
    @Override
    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        List<Trabajo> trabajosVehiculo = trabajos.get(vehiculo);
        for (Trabajo trabajo : trabajosVehiculo) {
            trabajos.borrar(trabajo);
        }
        vehiculos.borrar(vehiculo);
    }
    @Override
    public void borrar(Trabajo trabajo) throws OperationNotSupportedException {
        trabajos.borrar(trabajo);
    }

    @Override
    public List<Cliente> getClientes() {
        List<Cliente> listaTemporal = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            listaTemporal.add(new Cliente(cliente));
        }
        return listaTemporal;
    }
    @Override
    public List<Vehiculo> getVehiculos() {
        return vehiculos.get();
    }
    @Override
    public List<Trabajo> getTrabajos() {
        List<Trabajo> listaTemporal = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get()) {
            listaTemporal.add(Trabajo.copiar(trabajo));
        }
        return listaTemporal;
    }
    @Override
    public List<Trabajo> getTrabajos(Cliente cliente) {
        List<Trabajo> listaTemporal = new ArrayList<>();
        for (Trabajo trabajoCliente : trabajos.get(cliente)) {
            listaTemporal.add(Trabajo.copiar(trabajoCliente));
        }
        return listaTemporal;
    }
    @Override
    public List<Trabajo> getTrabajos(Vehiculo vehiculo) {
        List<Trabajo> listaTemporal = new ArrayList<>();
        for (Trabajo trabajoCliente : trabajos.get(vehiculo)) {
            listaTemporal.add(Trabajo.copiar(trabajoCliente));
        }
        return listaTemporal;
    }

    @Override
    public Map<TipoTrabajo, Integer> getEstadisticasMensuales(LocalDate mes) {
        return trabajos.getEstadisticasMensuales(mes);
    }
}
