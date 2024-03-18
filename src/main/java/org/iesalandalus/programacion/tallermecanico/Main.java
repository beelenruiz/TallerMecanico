package org.iesalandalus.programacion.tallermecanico;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.cascada.Modelo;
import org.iesalandalus.programacion.tallermecanico.vista.texto.VistaTexto;

import javax.naming.OperationNotSupportedException;

public class Main {
    public static void main(String[] args) {
        VistaTexto vistaTexto = new VistaTexto();
        Modelo modelo = new Modelo();
        Controlador controlador = new Controlador(modelo, vistaTexto);
        try {
            controlador.comenzar();
        } catch (IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }
}
