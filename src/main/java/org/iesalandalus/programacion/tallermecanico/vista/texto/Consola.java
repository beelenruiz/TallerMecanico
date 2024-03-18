package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Consola {
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Consola(){
    }

    static final void mostrarCabecera(String mensaje){
        System.out.println(mensaje);
        String subrayado = String.format(String.format("%%0%dd", mensaje.length()), 0).replace("0","_");
        System.out.println(subrayado);
    }
    static final void mostrarMenu(){
        System.out.println("Opciones del sistema:");
        for (Evento evento: Evento.values()) {
            System.out.println(evento);
        }
    }

    static final Evento elegirOpcion() {
        int numeroOpcion;
        do {
            numeroOpcion = leerEntero("Indica la opcion a realizar: ");
        } while (!Evento.esValido(numeroOpcion));
        return Evento.get(numeroOpcion);
    }

    static final int leerEntero(String mensaje){
        System.out.println(mensaje);
        int entero;
        return entero = Entrada.entero();
    }
    static final float leerReal(String mensaje){
        System.out.println(mensaje);
        float real;
        return real = Entrada.real();
    }
    static final String leerCadena(String mensaje){
        System.out.println(mensaje);
        String cadena;
        return cadena = Entrada.cadena();
    }
    static final LocalDate leerFecha(String mensaje){
        System.out.println(mensaje);
        LocalDate fecha;
        return fecha= LocalDate.parse(Entrada.cadena(), FORMATO_FECHA);
    }
}
