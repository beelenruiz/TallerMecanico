package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Dialogos;

public class VentanaPrincipal extends Controlador {

    public void inicializar(){
        VistaGrafica.getInstancia().inicializar();
        //getEscenario().setOnCloseRequest(this::salir);
    }

    @FXML
    void insertarCliente() {
        LeerCliente leerCliente = (LeerCliente) Controladores.get("/vistas/LeerCliente.fxml", "Insertar cliente", getEscenario());
        leerCliente.limpiar();
        leerCliente.getEscenario().showAndWait();
        if (!leerCliente.isCancelado()){
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.INSERTAR_CLIENTE);
        }
    }

    @FXML
    void insertarTrabajo(ActionEvent event) {

    }

    @FXML
    void insertarVehiculo(ActionEvent event) {

    }

    @FXML
    void listarClientes() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
    }

    @FXML
    void listarTrabajos(ActionEvent event) {

    }

    @FXML
    void listarVehiculos(ActionEvent event) {

    }

    @FXML
    void buscarCliente() {
        VistaGrafica.getInstancia().leerDni.limpiar();
        VistaGrafica.getInstancia().leerDni.getEscenario().showAndWait();
    }

    @FXML
    void buscarTrabajo(ActionEvent event) {

    }

    @FXML
    void buscarVehiculo(ActionEvent event) {

    }

    void salir(){

    }
}
