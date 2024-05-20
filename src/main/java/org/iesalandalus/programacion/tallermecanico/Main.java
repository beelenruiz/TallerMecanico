package org.iesalandalus.programacion.tallermecanico;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.FabricaModelo;
import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.vista.FabricaVista;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;

import javax.naming.OperationNotSupportedException;

public class  Main {
    public static void main(String[] args) throws OperationNotSupportedException {
        Modelo modelo = FabricaModelo.FICHEROS.crear(FabricaFuenteDatos.MEMORIA);

        Vista vista = FabricaVista.GRAFICA.crear();

        Controlador controlador = new Controlador(modelo, vista);
        controlador.comenzar();
    }
}
