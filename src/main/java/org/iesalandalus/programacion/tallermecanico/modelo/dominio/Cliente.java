package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;

import static java.lang.Integer.parseInt;

public class Cliente {
    private static final String ER_NOMBRE = "[A-ZÁÉÍÓÚ][a-záéíóú]+( [A-ZÁÉÍÓÚ][a-záéíóú]+)*";
    private static final String ER_DNI = "\\d{8}[A-Za-z]";
    private static final String ER_TELEFONO = "\\d{9}";
    String nombre;
    String dni;
    String telefono;

    public Cliente(String nombre, String dni, String telefono){
        setNombre(nombre);
        setDni(dni);
        setTelefono(telefono);
    }
    public Cliente(Cliente cliente){
        Objects.requireNonNull(cliente, "No es posible copiar un cliente nulo.");
        setNombre(cliente.nombre);
        setDni(cliente.dni);
        setTelefono(cliente.telefono);
    }

    public void setNombre(String nombre) {
        Objects.requireNonNull(nombre, "El nombre no puede ser nulo.");
        if (!nombre.matches(ER_NOMBRE)){
            throw new IllegalArgumentException("El nombre no tiene un formato válido.");
        }
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }

    private void setDni(String dni) {
        Objects.requireNonNull(dni, "El DNI no puede ser nulo.");
        if (!dni.matches(ER_DNI)){
            throw new IllegalArgumentException("El DNI no tiene un formato válido.");
        } else if (!comprobarLetraDni(dni)){
            throw new IllegalArgumentException("La letra del DNI no es correcta.");
        }
        this.dni = dni;
    }
    private boolean comprobarLetraDni(String dni){
        String letras_dni = new String("TRWAGMYFPDXBNJZSQVHLCKE");
        int numerosDni = parseInt(dni.substring(0, 8));
        int resto = numerosDni % 23;
        return letras_dni.charAt(resto) == Character.toUpperCase(dni.charAt(8));
    }
    public String getDni() {
        return dni;
    }

    public void setTelefono(String telefono) {
        Objects.requireNonNull(telefono, "El teléfono no puede ser nulo.");
        if (!telefono.matches(ER_TELEFONO)){
            throw new IllegalArgumentException("El teléfono no tiene un formato válido.");
        }
        this.telefono = telefono;
    }
    public String getTelefono() {
        return telefono;
    }

    public static Cliente get(String dni){
        return new Cliente("Patricio Estrella", dni, "950111111");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(nombre, cliente.nombre) && Objects.equals(dni, cliente.dni) && Objects.equals(telefono, cliente.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, dni, telefono);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", this.nombre, this.dni, this.telefono);
    }
}
