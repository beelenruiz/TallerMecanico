package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;

public class Mecanico extends Trabajo{
    private static final float FACTOR_HORA = 30f;
    private static final float FACTOR_PRECIO_MATERIAL = 1.5f;
    private float precioMaterial;

    public Mecanico(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio){
        super(cliente, vehiculo, fechaInicio);
    }
    public Mecanico(Mecanico mecanico){
        super(mecanico);
        this.horas = mecanico.getHoras();
        this.fechaFin = mecanico.getFechaFin();
        this.precioMaterial = mecanico.getPrecioMaterial();
    }

    public float getPrecioMaterial(){
        return precioMaterial;
    }
    public void anadirPrecioMaterial(float precioMaterial) throws OperationNotSupportedException {
        if (precioMaterial <= 0){
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        } else if (estaCerrado()) {
            throw new OperationNotSupportedException("No se puede añadir precio del material, ya que el trabajo mecánico está cerrado.");
        }
        this.precioMaterial += precioMaterial;
    }

    public float getPrecioEspecifico(){
        return (getHoras() * FACTOR_HORA) + (getPrecioMaterial() * FACTOR_PRECIO_MATERIAL);
    }

    @Override
    public String toString() {
        String cadena;
        String fechaF = (this.fechaFin != null) ? this.fechaFin.format(FORMATO_FECHA) : "";
        if (!estaCerrado()){
            cadena = String.format("Mecánico -> %s - %s (%s - %s): %s horas, %.2f € en material", this.cliente, this.vehiculo, this.fechaInicio.format(FORMATO_FECHA), fechaF, this.horas, this.precioMaterial);
        } else {
            cadena = String.format("Mecánico -> %s - %s (%s - %s): %s horas, %.2f € en material, %.2f € total", this.cliente, this.vehiculo, this.fechaInicio.format(FORMATO_FECHA), fechaF, this.horas, this.precioMaterial, getPrecio());
        }
        return cadena;
    }
}
