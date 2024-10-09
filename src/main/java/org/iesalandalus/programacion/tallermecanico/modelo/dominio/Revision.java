package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;
import java.util.Objects;

public class Revision extends Trabajo {
    private static final float FACTOR_HORA = 30f;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio){
        super(cliente, vehiculo, fechaInicio);
    }
    public Revision(Revision revision){
        super(revision);
        Objects.requireNonNull(revision, "La revisión no puede ser nula.");
        cliente = new Cliente(revision.cliente);
        vehiculo = revision.vehiculo;
        fechaInicio = revision.fechaInicio;
        fechaFin = revision.fechaFin;
        horas = revision.horas;
    }

    @Override
    float getPrecioEspecifico() {
        return FACTOR_HORA * getHoras();
    }

    @Override
    public String toString() {
        String cadena;
        String fechaF = (this.fechaFin != null) ? this.fechaFin.format(FORMATO_FECHA) : "";
        if (!estaCerrado()){
            cadena = String.format("Revisión -> %s - %s (%s - %s): %s horas", this.cliente, this.vehiculo, this.fechaInicio.format(FORMATO_FECHA), fechaF, this.horas);
        } else {
            cadena = String.format("Revisión -> %s - %s (%s - %s): %s horas, %.2f € total", this.cliente, this.vehiculo, this.fechaInicio.format(FORMATO_FECHA), fechaF, this.horas, getPrecio());
        }
        return cadena;
    }
}