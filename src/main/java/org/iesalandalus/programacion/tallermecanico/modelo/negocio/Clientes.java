package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clientes {
    private final List<Cliente> coleccionCLiente;
    public Clientes(){
        coleccionCLiente  = new ArrayList<>();

    }

    public List<Cliente> get(){
        return new ArrayList<>(coleccionCLiente);
    }

    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede insertar un cliente nulo.");
        if (!coleccionCLiente.contains(cliente)){
            coleccionCLiente.add(cliente);
        } else {
            throw new OperationNotSupportedException("Ya existe un cliente con ese DNI.");
        }
    }

    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        boolean modificado = false;
        Objects.requireNonNull(cliente, "No se puede modificar un cliente nulo.");
        if (!coleccionCLiente.contains(cliente)){
            throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
        }
        if (nombre != null && !nombre.isBlank()){
            cliente.setNombre(nombre);
            modificado = true;
        }
        if (telefono != null && !telefono.isBlank()){
            cliente.setTelefono(telefono);
            modificado = true;
        }
        return modificado;
    }

    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(cliente, "No se puede buscar un cliente nulo.");
        return (coleccionCLiente.contains(cliente)) ? cliente : null;
    }

    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede borrar un cliente nulo.");
        if (coleccionCLiente.contains(cliente)){
            coleccionCLiente.remove(cliente);
        } else {
            throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
        }
    }
}
