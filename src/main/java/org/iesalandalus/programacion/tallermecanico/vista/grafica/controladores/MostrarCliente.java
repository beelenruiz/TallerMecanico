package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Dialogos;

public class MostrarCliente extends Controlador {
    @FXML
    private TextField tfDni;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfTelefono;

    @FXML
    void cancelar() {
        getEscenario().close();
    }

    Cliente getCliente(){
        String nombre = tfNombre.getText();
        String dni = tfDni.getText();
        String telefono = tfTelefono.getText();

        return new Cliente(nombre, dni, telefono);
    }
    @FXML
    void borrar() {
        if (Dialogos.mostrarDialogoConfirmacion("BORRADO", String.format("¿Seguro que quieres borrar el cliente %s?", getCliente()), null)){
            VistaGrafica.getInstancia().getGestorEventos().notificar(Evento.BORRAR_CLIENTE);
        }
    }

    @FXML
    void modificar() {
        NombreYTelefono nombreYTelefono = (NombreYTelefono) Controladores.get("/vistas/NombreYTelefono.fxml", "Modificar cliente", null);
        nombreYTelefono.limpiar();
        nombreYTelefono.actualizar(tfNombre.getText(), tfTelefono.getText());
        nombreYTelefono.getEscenario().showAndWait();
    }

    public void actualizar(Cliente cliente){
        tfNombre.setText(cliente.getNombre());
        tfDni.setText(cliente.getDni());
        tfTelefono.setText(cliente.getTelefono());
    }

}
