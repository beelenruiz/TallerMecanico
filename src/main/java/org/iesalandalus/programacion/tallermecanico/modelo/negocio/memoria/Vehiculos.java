package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vehiculos implements IVehiculos {
    private final List<Vehiculo> coleccionVehiculo;
    public Vehiculos(){
        coleccionVehiculo = new ArrayList<>();
    }

    @Override
    public List<Vehiculo> get(){
        return new ArrayList<>(coleccionVehiculo);
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        Objects.requireNonNull(vehiculo, "No se puede insertar un vehículo nulo.");
        if (!coleccionVehiculo.contains(vehiculo)){
            coleccionVehiculo.add(vehiculo);
        } else {
            throw new OperationNotSupportedException("Ya existe un vehículo con esa matrícula.");
        }
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "No se puede buscar un vehículo nulo.");
        return (coleccionVehiculo.contains(vehiculo)) ? vehiculo : null;
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        Objects.requireNonNull(vehiculo, "No se puede borrar un vehículo nulo.");
        if (coleccionVehiculo.contains(vehiculo)){
            coleccionVehiculo.remove(vehiculo);
        } else {
            throw new OperationNotSupportedException("No existe ningún vehículo con esa matrícula.");
        }
    }
}
