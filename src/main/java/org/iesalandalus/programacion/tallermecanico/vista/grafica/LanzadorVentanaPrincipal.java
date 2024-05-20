package org.iesalandalus.programacion.tallermecanico.vista.grafica;

import javafx.application.Application;
import javafx.stage.Stage;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.controladores.VentanaPrincipal;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.grafica.utilidades.Controladores;

public class LanzadorVentanaPrincipal extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Controlador ventanaPrincipal = Controladores.get("/vistas/VentanaPrincipal.fxml", "Taller Mecánico", null);
        VistaGrafica.getInstancia().setVentanaPrincipal(ventanaPrincipal);
        VistaGrafica.getInstancia().inicializar();
        ventanaPrincipal.getEscenario().show();
    }

    public static void comenzar(){
        launch(LanzadorVentanaPrincipal.class);
    }
}
