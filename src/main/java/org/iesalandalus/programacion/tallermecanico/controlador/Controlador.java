package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
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
    public void actualizar(Evento evento) throws OperationNotSupportedException {
        switch (evento){
            case BORRAR_CLIENTE -> modelo.borrar(vista.leerCliente());
            case SALIR -> terminar();
            case BUSCAR_CLIENTE -> modelo.buscar(vista.leerCliente());
            case BORRAR_TRABAJO -> modelo.borrar(vista.leerMecanico());
            case BORRAR_VEHICULO -> modelo.borrar(vista.leerVehiculo());
            case BUSCAR_TRABAJO -> modelo.buscar(vista.leerTrabajoVehiculo());
            case BUSCAR_VEHICULO -> modelo.buscar(vista.leerVehiculo());
            case CERRAR_TRABAJO -> modelo.cerrar(vista.leerTrabajoVehiculo(), vista.leerFechaCierre());
            case LISTAR_CLIENTES -> modelo.getClientes();
            case INSERTAR_CLIENTE -> modelo.insertar(vista.leerCliente());
            case LISTAR_VEHICULOS -> modelo.getVehiculos();
            case INSERTAR_REVISION -> modelo.insertar(vista.leerRevision());
            case INSERTAR_MECANICO -> modelo.insertar(vista.leerMecanico());
            case INSERTAR_VEHICULO -> modelo.insertar(vista.leerVehiculo());
            case LISTAR_TRABAJOS -> modelo.getTrabajos();
            case MODIFICAR_CLIENTE -> modelo.modificar(vista.leerClienteDni(), vista.leerNuevoNombre(), vista.leerNuevoTelefono());
            case ANADIR_HORAS_TRABAJO -> modelo.anadirHoras(vista.leerTrabajoVehiculo(), vista.leerHoras());
            case LISTAR_TRABAJOS_CLIENTE -> modelo.getTrabajos(vista.leerCliente());
            case LISTAR_TRABAJOS_VEHICULO -> modelo.getTrabajos(vista.leerVehiculo());
            case ANADIR_PRECIO_MATERIAL_TRABAJO -> modelo.anadirPrecioMaterial(vista.leerTrabajoVehiculo(), vista.leerPrecioMaterial());
        }
    }
}