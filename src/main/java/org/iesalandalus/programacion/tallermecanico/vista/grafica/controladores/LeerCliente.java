package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Dialogos;

public class LeerCliente extends Controlador {

    @FXML
    private TextField tfDni;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfTelefono;

    VentanaPrincipal ventanaPrincipal;
    boolean cancelado = true;

    public boolean isCancelado(){
        return cancelado;
    }

    public Cliente getCliente(){
        return cancelado ? null : new Cliente(tfNombre.getText(), tfDni.getText(), tfTelefono.getText());
    }

    @FXML
    public void limpiar() {
        tfNombre.clear();
        tfDni.clear();
        tfTelefono.clear();
    }

    @FXML
    void cancelar() {
        cancelado = true;
        getEscenario().close();
    }

    @FXML
    void aceptar() {
        cancelado = false;
        try{
            ventanaPrincipal.insertarCliente();
            Dialogos.mostrarDialogoInformacion("Cliente insertado", String.format("El cliente %s ha sido insertado", getCliente()), getEscenario());
            getEscenario().close();
        }catch (Exception e){
            Dialogos.mostrarDialogoError("Error de inserción", String.format("ERROR: %s", e.getMessage()), getEscenario());
        }
    }
}
