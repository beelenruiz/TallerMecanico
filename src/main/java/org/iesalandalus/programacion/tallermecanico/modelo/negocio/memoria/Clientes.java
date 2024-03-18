package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clientes implements IClientes {
    private final List<Cliente> coleccionCliente;
    public Clientes(){
        coleccionCliente = new ArrayList<>();
    }

    @Override
    public List<Cliente> get(){
        return new ArrayList<>(coleccionCliente);
    }

    @Override
    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede insertar un cliente nulo.");
        if (!coleccionCliente.contains(cliente)){
            coleccionCliente.add(cliente);
        } else {
            throw new OperationNotSupportedException("Ya existe un cliente con ese DNI.");
        }
    }

    @Override
    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        boolean modificado = false;
        Objects.requireNonNull(cliente, "No se puede modificar un cliente nulo.");
        Cliente clienteEncontrado = buscar(cliente);
        if (clienteEncontrado == null){
            throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
        }
        if (nombre != null && !nombre.isBlank()){
            clienteEncontrado.setNombre(nombre);
            modificado = true;
        }
        if (telefono != null && !telefono.isBlank()){
            clienteEncontrado.setTelefono(telefono);
            modificado = true;
        }
        return modificado;
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(cliente, "No se puede buscar un cliente nulo.");
        int indice = coleccionCliente.indexOf(cliente);
        return (indice == -1) ? null : coleccionCliente.get(indice);
    }

    @Override
    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede borrar un cliente nulo.");
        if (coleccionCliente.contains(cliente)){
            coleccionCliente.remove(cliente);
        } else {
            throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
        }
    }
}
