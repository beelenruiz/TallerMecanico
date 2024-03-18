package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import javax.naming.OperationNotSupportedException;

public interface IControlador {
    void comenzar() throws OperationNotSupportedException;

    void terminar();

    void actualizar(Evento evento);
}
