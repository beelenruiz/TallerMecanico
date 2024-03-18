package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trabajos implements ITrabajos {
    List<Trabajo> coleccionTrabajo;
    public Trabajos(){
        coleccionTrabajo = new ArrayList<>();
    }

    @Override
    public List<Trabajo> get(){
        return new ArrayList<>(coleccionTrabajo);
    }
    @Override
    public List<Trabajo> get(Cliente cliente){
        List<Trabajo> trabajosCliente = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajo){
            if ((trabajo.getCliente()).equals(cliente)){
                trabajosCliente.add(trabajo);
            }
        }
        return trabajosCliente;
    }
    @Override
    public List<Trabajo> get(Vehiculo vehiculo){
        List<Trabajo> trabajosVehiculo = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajo){
            if ((trabajo.getVehiculo()).equals(vehiculo)){
                trabajosVehiculo.add(trabajo);
            }
        }
        return trabajosVehiculo;
    }

    @Override
    public void insertar(Trabajo trabajo) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No se puede insertar un trabajo nulo.");
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajo.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) throws OperationNotSupportedException {
        for (Trabajo trabajo : coleccionTrabajo) {
            if ((trabajo.getCliente()).equals(cliente)){
                if (!trabajo.estaCerrado()){
                    throw new OperationNotSupportedException("El cliente tiene otro trabajo en curso.");
                } else {
                    if (!fechaInicio.isAfter(trabajo.getFechaFin())){
                        throw new OperationNotSupportedException("El cliente tiene otro trabajo posterior.");
                    }
                }
            }
            if ((trabajo.getVehiculo()).equals(vehiculo)){
                if (!trabajo.estaCerrado()){
                    throw new OperationNotSupportedException("El vehículo está actualmente en el taller.");
                } else {
                    if (!fechaInicio.isAfter(trabajo.getFechaFin())){
                        throw new OperationNotSupportedException("El vehículo tiene otro trabajo posterior.");
                    }
                }
            }
        }
    }

    @Override
    public void anadirHoras(Trabajo trabajo, int horas) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No puedo añadir horas a un trabajo nulo.");
        if (!coleccionTrabajo.contains(trabajo)){
            throw new OperationNotSupportedException("No existe ningún trabajo abierto para dicho vehículo.");
        }
        int indice = coleccionTrabajo.indexOf(trabajo);
        Trabajo trabajoEncontrado = coleccionTrabajo.get(indice);
        if (indice != -1){
            trabajoEncontrado.anadirHoras(horas);
        }
    }
    @Override
    public void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No puedo añadir precio del material a un trabajo nulo.");
        if (!coleccionTrabajo.contains(trabajo)){
            throw new OperationNotSupportedException("No existe ningún trabajo abierto para dicho vehículo.");
        }
        if (trabajo instanceof Mecanico mecanico){
            mecanico.anadirPrecioMaterial(precioMaterial);
        } else {
            throw new OperationNotSupportedException("No se puede añadir precio al material para este tipo de trabajos.");
        }

    }

    private Trabajo getTrabajoAbierto(Vehiculo vehiculo){
        return null;
    }

    @Override
    public void cerrar(Trabajo trabajo, LocalDate fechaFin) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No puedo cerrar un trabajo nulo.");
        if (!coleccionTrabajo.contains(trabajo)) {
            throw new OperationNotSupportedException("No existe ningún trabajo abierto para dicho vehículo.");
        }
        trabajo.cerrar(fechaFin);
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede buscar un trabajo nulo.");
        int indice = coleccionTrabajo.indexOf(trabajo);
        Trabajo trabajoEncontrado;
        if (indice != -1){
            trabajoEncontrado = coleccionTrabajo.get(indice);
        } else {
            trabajoEncontrado = null;
        }
        return trabajoEncontrado;
    }

    @Override
    public void borrar(Trabajo trabajo) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No se puede borrar un trabajo nulo.");
        if (!coleccionTrabajo.contains(trabajo)){
            throw new OperationNotSupportedException("No existe ningún trabajo igual.");
        }
        coleccionTrabajo.remove(trabajo);
    }
}
