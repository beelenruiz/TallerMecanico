package org.iesalandalus.programacion.tallermecanico.vista.grafica;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.TipoTrabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores.LeerDni;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores.ListarClientes;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores.MostrarCliente;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores.NombreYTelefono;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores.*;

public class VistaGrafica implements Vista {
    private final GestorEventos gestorEventos = new GestorEventos(Evento.values());
    private Controlador ventanaPrincipal;
    private static VistaGrafica instancia;
    private List<Vehiculo> vehiculos;

    public static VistaGrafica getInstancia(){
        if (instancia == null){
            instancia = new VistaGrafica();
        }
        return instancia;
    }

    private VistaGrafica(){}

    public List<Vehiculo> getVehiculos(){
        return vehiculos;
    }

    @Override
    public GestorEventos getGestorEventos() {
        return gestorEventos;
    }

    void setVentanaPrincipal(Controlador ventanaPrincipal){
        this.ventanaPrincipal = ventanaPrincipal;
    }
    public Controlador getVentanaPrincipal(){
        return ventanaPrincipal;
    }

    @Override
    public void comenzar() {
        LanzadorVentanaPrincipal.comenzar();
    }

    public MostrarCliente mostrarCliente;
    public LeerDni leerDni;
    public NombreYTelefono nombreYTelefono;
    public ListarClientes listarClientes;
    public void inicializar(){
        leerDni = (LeerDni) get("/vistas/LeerDni.fxml", "Leer dni", null);
        mostrarCliente = (MostrarCliente) get("/vistas/MostrarCliente.fxml", "Cliente", null);
        nombreYTelefono = (NombreYTelefono) get("/vistas/NombreYTelefono.fxml", "Modificar cliente", null);
        listarClientes = (ListarClientes) get("/vistas/ListarClientes.fxml", "Lista de Clientes", null);
    }

    @Override
    public void terminar() {

    }

    @Override
    public Cliente leerCliente() {
        return Cliente.get(leerDni.getDni());
    }

    @Override
    public Cliente leerClienteDni() {
        return Cliente.get(leerDni.getDni());
    }

    @Override
    public String leerNuevoNombre() {
        return nombreYTelefono.getNombre();
    }

    @Override
    public String leerNuevoTelefono() {
        return nombreYTelefono.getTelefono();
    }

    @Override
    public Vehiculo leerVehiculo() {
        return null;
    }

    @Override
    public Vehiculo leerVehiculoMatricula() {
        return null;
    }

    @Override
    public Trabajo leerRevision() {
        return null;
    }

    @Override
    public Trabajo leerMecanico() {
        return null;
    }

    @Override
    public Trabajo leerTrabajoVehiculo() {
        return null;
    }

    @Override
    public int leerHoras() {
        return 0;
    }

    @Override
    public float leerPrecioMaterial() {
        return 0;
    }

    @Override
    public LocalDate leerFechaCierre() {
        return null;
    }

    @Override
    public LocalDate leerMes() {
        return null;
    }

    @Override
    public void notificarResultado(Evento evento, String texto, boolean exito) {

    }

    @Override
    public void mostrarCliente(Cliente cliente) {
        mostrarCliente.actualizar(cliente);
        mostrarCliente.getEscenario().showAndWait();
    }

    @Override
    public void mostrarVehiculo(Vehiculo vehiculo) {

    }

    @Override
    public void mostrarTrabajo(Trabajo trabajo) {

    }

    @Override
    public void mostrarClientes(List<Cliente> clientes) {
        ObservableList<Cliente> CLIENTES = FXCollections.observableArrayList(clientes);
        listarClientes.renenar(CLIENTES);
        listarClientes.getEscenario().showAndWait();
    }

    @Override
    public void mostrarVehiculos(List<Vehiculo> vehiculos) {

    }

    @Override
    public void mostrarTrabajos(List<Trabajo> trabajos) {

    }

    @Override
    public void mostrarEstadisticasMensuales(Map<TipoTrabajo, Integer> estadisticas) {

    }
}
