package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.FabricaModelo;
import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.vista.FabricaVista;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import java.util.Objects;

public class Controlador implements IControlador {
    private final FabricaModelo fabricaModelo;
    private Modelo modelo;
    private Vista vista;
    private final FabricaVista fabricaVista;
    private final FabricaFuenteDatos fabricaFuenteDatos;

    public Controlador(FabricaModelo fabricaModelo, FabricaFuenteDatos fabricaFuenteDatos, FabricaVista fabricaVista) {
        Objects.requireNonNull(fabricaModelo, "El modelo no puede ser nulo.");
        Objects.requireNonNull(fabricaVista, "La fabrica de la vista no puede ser nula.");
        this.fabricaModelo = fabricaModelo;
        this.fabricaVista = fabricaVista;
        this.fabricaFuenteDatos = fabricaFuenteDatos;
        this.modelo = fabricaModelo.crear(fabricaFuenteDatos);
        this.vista = fabricaVista.crear();
        this.vista.getGestorEventos().suscribir(this, Evento.values());
    }

    @Override
    public void comenzar() {
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
        try {
            String resultado = "";
            switch (evento) {
                case BORRAR_CLIENTE -> {modelo.borrar(vista.leerClienteDni());
                    resultado = "El cliente ha sido borrado.";}
                case SALIR -> terminar();
                case BUSCAR_CLIENTE -> vista.mostrarCliente(modelo.buscar(vista.leerClienteDni()));
                case BORRAR_TRABAJO -> {modelo.borrar(vista.leerRevision());
                    resultado = "El trabajo ha sido borrado.";}
                case BORRAR_VEHICULO -> {modelo.borrar(vista.leerVehiculoMatricula());
                    resultado = "El vehículo ha sido borrado.";}
                case BUSCAR_TRABAJO -> vista.mostrarTrabajo(modelo.buscar(vista.leerTrabajoVehiculo()));
                case BUSCAR_VEHICULO -> vista.mostrarVehiculo(modelo.buscar(vista.leerVehiculoMatricula()));
                case CERRAR_TRABAJO -> {modelo.cerrar(vista.leerTrabajoVehiculo(), vista.leerFechaCierre());
                    resultado = "El trabajo ha sido cerrado.";}
                case LISTAR_CLIENTES -> modelo.getClientes();
                case INSERTAR_CLIENTE -> {modelo.insertar(vista.leerCliente());
                    resultado = "El cliente ha sido insertado.";}
                case LISTAR_VEHICULOS -> vista.mostrarVehiculos(modelo.getVehiculos());
                case INSERTAR_REVISION -> {modelo.insertar(vista.leerRevision());
                    resultado = "El trabajo de revisión ha sido insertada.";}
                case INSERTAR_MECANICO -> {modelo.insertar(vista.leerMecanico());
                    resultado = "El trabajo mecánica ha sido insertada.";}
                case INSERTAR_VEHICULO -> {modelo.insertar(vista.leerVehiculo());
                    resultado = "El vehículo ha sido insertado.";}
                case LISTAR_TRABAJOS -> modelo.getTrabajos();
                case MODIFICAR_CLIENTE -> resultado  = modelo.modificar(vista.leerClienteDni(), vista.leerNuevoNombre(), vista.leerNuevoTelefono()) ?
                    "El cliente ha sido modificado." : "El cliente no se ha modificado.";
                case ANADIR_HORAS_TRABAJO -> {modelo.anadirHoras(vista.leerTrabajoVehiculo(), vista.leerHoras());
                    resultado = "Horas añadidas correctamente.";}
                case LISTAR_TRABAJOS_CLIENTE -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerClienteDni()));
                case LISTAR_TRABAJOS_VEHICULO -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerVehiculoMatricula()));
                case ANADIR_PRECIO_MATERIAL_TRABAJO -> {modelo.anadirPrecioMaterial(vista.leerTrabajoVehiculo(), vista.leerPrecioMaterial());
                    resultado = "Precio del material añadido correctamente";}
                case MOSTRAR_ESTADISTICAS_MENSUALES -> vista.mostrarEstadisticasMensuales(modelo.getEstadisticasMensuales(vista.leerMes()));
            }
            if (!resultado.isBlank()){
                vista.notificarResultado(evento, resultado, true);
            }
        } catch (Exception e){
            vista.notificarResultado(evento, e.getMessage(), false);
        }
    }
}