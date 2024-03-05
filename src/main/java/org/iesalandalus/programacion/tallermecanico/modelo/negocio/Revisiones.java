package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Revisiones {
    private final List<Revision> coleccionRevision;

    public Revisiones(){
        coleccionRevision = new ArrayList<>();
    }

    public List<Revision> get(){
        return new ArrayList<>(coleccionRevision);
    }
    public List<Revision> get(Cliente cliente){
        List<Revision> revisionesCliente = new ArrayList<>();
        for (Revision revision : coleccionRevision){
            if ((revision.getCliente()).equals(cliente)){
                revisionesCliente.add(revision);
            }
        }
        return revisionesCliente;
    }
    public List<Revision> get(Vehiculo vehiculo){
        List<Revision> revisionesVehiculo = new ArrayList<>();
        for (Revision revision : coleccionRevision){
            if ((revision.getVehiculo()).equals(vehiculo)){
                revisionesVehiculo.add(revision);
            }
        }
        return revisionesVehiculo;
    }

    public void insertar(Revision revision) throws OperationNotSupportedException {
        Objects.requireNonNull(revision, "No se puede insertar una revisión nula.");
        comprobarRevision(revision.getCliente(), revision.getVehiculo(), revision.getFechaFin());
        coleccionRevision.add(revision);
    }

    private void comprobarRevision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaRevision) throws OperationNotSupportedException {
        for (Revision revision : coleccionRevision) {
            if ((revision.getCliente()).equals(cliente)){
                if (!revision.estaCerrada()){
                    throw new OperationNotSupportedException("El cliente tiene otra revisión en curso.");
                } else {
                    if (!fechaRevision.isAfter(revision.getFechaFin())){
                        throw new OperationNotSupportedException("El cliente tiene una revisión posterior.");
                    }
                }
            }
            if ((revision.getVehiculo()).equals(vehiculo)){
                if (!revision.estaCerrada()){
                    throw new OperationNotSupportedException("El vehículo está actualmente en revisión.");
                } else {
                    if (!fechaRevision.isAfter(revision.getFechaFin())){
                        throw new OperationNotSupportedException("El vehículo tiene una revisión posterior.");
                    }
                }
            }
        }
    }

    private Revision getRevisiones(Revision revision) throws OperationNotSupportedException {
        Objects.requireNonNull(revision, "No puedo operar sobre una revisión nula.");
        Revision revisionEncontrada = buscar(revision);
        if (revisionEncontrada == null){
            throw new OperationNotSupportedException("No existe ninguna revisión igual.");
        }
        return revisionEncontrada;
    }
    public void anadirHoras(Revision revision, int horas) throws OperationNotSupportedException {
        Revision revisionEncontrada = getRevisiones(revision);
        revisionEncontrada.anadirHoras(horas);
    }
    public void anadirPrecioMaterial(Revision revision, float precioMaterial) throws OperationNotSupportedException {
        Revision revisionEncontrada = getRevisiones(revision);
        revisionEncontrada.anadirPrecioMaterial(precioMaterial);
    }

    public void cerrar(Revision revision, LocalDate fechaRevision) throws OperationNotSupportedException {
        Revision revisionEncontrada = getRevisiones(revision);
        revisionEncontrada.cerrar(fechaRevision);
    }

    public Revision buscar(Revision revision) {
        Objects.requireNonNull(revision, "No se puede buscar una revisión nula.");
        return (coleccionRevision.contains(revision)) ? revision : null;
    }

    public void borrar(Revision revision) throws OperationNotSupportedException {
        Objects.requireNonNull(revision, "No se puede borrar una revisión nula.");
        if (coleccionRevision.contains(revision)){
            coleccionRevision.remove(revision);
        } else {
            throw new OperationNotSupportedException("No existe ninguna revisión igual.");
        }
    }
}
