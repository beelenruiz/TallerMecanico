package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;

public class MostrarCliente extends Controlador {
    @FXML
    private Button btAceptar;

    @FXML
    private Button btCancelar;

    @FXML
    private Button btCancelar1;

    @FXML
    private TextField tfDni;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfTelefono;

    @FXML
    void aceptar() {

    }

    @FXML
    void cancelar() {

    }

    @FXML
    void limpiar() {

    }

    public void actualizar(Cliente cliente){
        tfNombre.setPromptText(cliente.getNombre());
        tfDni.setPromptText(cliente.getDni());
        tfTelefono.setPromptText(cliente.getTelefono());
    }

}
