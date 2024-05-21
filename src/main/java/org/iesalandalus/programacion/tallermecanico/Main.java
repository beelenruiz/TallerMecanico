package org.iesalandalus.programacion.tallermecanico;

import javafx.util.Pair;
import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.FabricaModelo;
import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.vista.FabricaVista;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;

import javax.naming.OperationNotSupportedException;

public class  Main {
    public static void main(String[] args) throws OperationNotSupportedException {
        Pair<FabricaVista, FabricaFuenteDatos> fabricas = procesarArgumentos(args);

        Controlador controlador = new Controlador(FabricaModelo.CASCADA, fabricas.getValue(), fabricas.getKey());
        controlador.comenzar();
    }

    private static Pair<FabricaVista, FabricaFuenteDatos> procesarArgumentos(String[] args){
        FabricaVista fabricaVista = FabricaVista.GRAFICA;
        FabricaFuenteDatos fabricaFuenteDatos = FabricaFuenteDatos.MARIADB;

        for (String argumento : args){
            if (argumento.equalsIgnoreCase("vventanas")){
                fabricaVista = FabricaVista.GRAFICA;
            } else if (argumento.equalsIgnoreCase("vtexto")) {
                fabricaVista = FabricaVista.TEXTO;
            } else if (argumento.equalsIgnoreCase("fdficheros")) {
                fabricaFuenteDatos = FabricaFuenteDatos.FICHEROS;
            } else if (argumento.equalsIgnoreCase("fdmariadb")) {
                fabricaFuenteDatos = FabricaFuenteDatos.MARIADB;
            }
        }
        return new Pair<>(fabricaVista, fabricaFuenteDatos);
    }
}
