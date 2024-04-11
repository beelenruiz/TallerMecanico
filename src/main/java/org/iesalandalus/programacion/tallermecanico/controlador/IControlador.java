package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.ReceptorEventos;

import javax.naming.OperationNotSupportedException;

public interface IControlador extends ReceptorEventos {
    void comenzar() throws OperationNotSupportedException;

    void terminar();

    void actualizar(Evento evento);
}
