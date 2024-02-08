package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cliente {
    private static final String ER_NOMBRE = "[A-ZÁÉÍÓÚ][a-záéíóú]+";
    private static final String ER_DNI = "//d{8}[A-Z]";
    private static final String ER_TELEFONO = "6//d{8}";
    String nombre;
    String dni;
    String telefono;
    Matcher comparador;
    Pattern patron;

    public Cliente(String nombre, String dni, String telefono){
        setNombre(nombre);
        setDni(dni);
        setTelefono(telefono);
    }

    public void setNombre(String nombre) {
        patron = Pattern.compile(ER_NOMBRE);
        comparador = patron.matcher(nombre);
        if (!comparador.find()){
            throw new IllegalArgumentException();
        }
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDni() {
        return dni;
    }

    public void setTelefono(String telefono) {

        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }


}
