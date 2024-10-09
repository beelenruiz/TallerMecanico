package org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.mariadb.Clientes;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;

import java.util.List;

public class ListarClientes extends Controlador {

    @FXML
    private TableColumn<Cliente, String> tcDni;

    @FXML
    private TableColumn<Cliente, String> tcNombre;

    @FXML
    private TableColumn<Cliente, String> tcTelefono;

    @FXML
    private TableView<Cliente> tvClientes;

    @FXML
    void aceptar() {

    }

    @FXML
    void cancelar() {
        getEscenario().close();
    }

    public void renenar(ObservableList<Cliente> clientes) {
        tvClientes.setItems(clientes);
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    }
}
