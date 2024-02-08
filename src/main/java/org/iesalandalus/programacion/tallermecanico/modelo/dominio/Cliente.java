package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cliente {
    private static final String ER_NOMBRE = "[A-ZÁÉÍÓÚ][a-záéíóú]+( [A-ZÁÉÍÓÚ][a-záéíóú]+)?";
    private static final String ER_DNI = "(//d{8})([A-Z])";
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
    public Cliente(Cliente cliente){

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

    private void setDni(String dni) {
        patron = Pattern.compile(ER_DNI);
        comparador = patron.matcher(dni);
        if (!comprobarLetraDni(dni) || !comparador.find()){
            throw new IllegalArgumentException();
        }
        this.dni = dni;
    }
    private boolean comprobarLetraDni(String dni){
        String letras_dni = new String("TRWAGMYFPDXBNJZSQVHLCKE");
        int resto = dni.indexOf(0, 7) % 23;
        return letras_dni.charAt(resto) == dni.charAt(8);
    }
    public String getDni() {
        return dni;
    }

    public void setTelefono(String telefono) {
        patron = Pattern.compile(ER_TELEFONO);
        comparador = patron.matcher(telefono);
        if (!comparador.find()){
            throw new IllegalArgumentException();
        }
        this.telefono = telefono;
    }
    public String getTelefono() {
        return telefono;
    }

    public static Cliente get(String dni){
        
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(nombre, cliente.nombre) && Objects.equals(dni, cliente.dni) && Objects.equals(telefono, cliente.telefono) && Objects.equals(comparador, cliente.comparador) && Objects.equals(patron, cliente.patron);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, dni, telefono, comparador, patron);
    }

    @Override
    public String toString() {
        return String.format("Cliente[nombre=%s, dni=%s, telefono=%s, comparador=%s, patron=%s]", this.nombre, this.dni, this.telefono, this.comparador, this.patron);
    }
}
