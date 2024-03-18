package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.texto.Consola;
import org.iesalandalus.programacion.tallermecanico.vista.texto.VistaTexto;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Controlador implements IControlador {
    private final Modelo modelo;
    private final Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        Objects.requireNonNull(modelo, "El modelo es nulo");
        Objects.requireNonNull(vista, "La vista es nulo");
        this.modelo = modelo;
        this.vista = vista;
        vista.setControlador(this);
    }

    @Override
    public void comenzar() throws OperationNotSupportedException {
        modelo.comenzar();
        vista.comenzar();
    }

    @Override
    public void terminar() {
        modelo.terminar();
        vista.terminar();
    }

    @Override
    public void actualizar(Evento evento) {
        switch (evento){
            case BORRAR_CLIENTE -> modelo.borrar(vista.);
            case SALIR -> salir();
            case BUSCAR_CLIENTE -> buscarCliente();
            case BORRAR_REVISION -> borrarRevision();
            case BORRAR_VEHICULO -> borrarVehiculo();
            case BUSCAR_REVISION -> buscarRevision();
            case BUSCAR_VEHICULO -> buscarVehiculo();
            case CERRAR_REVISION -> cerrarRevision();
            case LISTAR_CLIENTES -> listarClientes();
            case INSERTAR_CLIENTE -> insertarCliente();
            case LISTAR_VEHICULOS -> listarVehiculo();
            case INSERTAR_REVISION -> insertarRevision();
            case INSERTAR_VEHICULO -> insertarVehiculo();
            case LISTAR_REVISIONES -> listarRevision();
            case MODIFICAR_CLIENTE -> modificarCliente();
            case ANADIR_HORAS_REVISION -> anadirHoras();
            case LISTAR_REVISIONES_CLIENTE -> listarRevisionesCliente();
            case LISTAR_REVISIONES_VEHICULO -> listarRevisionesVehiculo();
            case ANADIR_PRECIO_MATERIAL_REVISION -> anadirPrecioMaterial();
        }
    }
}