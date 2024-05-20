package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;

public class LeerDni extends Controlador {
    boolean cancelado = true;
    @FXML
    private Button btAceptar;

    @FXML
    private Button btCancelar;

    @FXML
    private TextField tfDni;

    @FXML
    void aceptar() {
        VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BUSCAR_CLIENTE);
    }

    @FXML
    void cancelar() {
        cancelado = true;
        getEscenario().close();
    }

    public boolean isCancelado(){
        return cancelado;
    }

    void limpiar(){
        tfDni.clear();
    }

    public String getDni(){
        return tfDni.getText();
    }
}
