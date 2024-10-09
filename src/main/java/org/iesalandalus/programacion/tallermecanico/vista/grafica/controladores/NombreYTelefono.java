package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Dialogos;

public class NombreYTelefono extends Controlador {
    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfTelefono;

    @FXML
    void aceptar() {
        if (Dialogos.mostrarDialogoConfirmacion("MODIFICACION", String.format("¿Seguro que quiere modificar el cliente con nombre %s y telefono %s", tfNombre.getText(), tfTelefono.getText()), null)) {
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.MODIFICAR_CLIENTE);
            getEscenario().close();
        }
    }

    @FXML
    void cancelar() {
        getEscenario().close();
    }

    @FXML
    void limpiar() {
        tfNombre.clear();
        tfTelefono.clear();
    }

    public String getNombre() {
        return tfNombre.getText();
    }
    public String getTelefono() {
        return tfTelefono.getText();
    }

    @FXML
    void initialize() {
        tfNombre.textProperty().addListener(observable -> Controles.validarCampoTexto(Cliente.ER_NOMBRE, tfNombre));
        tfTelefono.textProperty().addListener(observable -> Controles.validarCampoTexto(Cliente.ER_TELEFONO, tfTelefono));
    }

    void actualizar(String nombre, String telefono){
        tfNombre.setText(nombre);
        tfTelefono.setText(telefono);
    }
}
