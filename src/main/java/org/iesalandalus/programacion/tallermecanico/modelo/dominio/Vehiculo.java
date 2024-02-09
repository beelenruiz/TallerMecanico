package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;

public class Vehiculo {
    private static final String ER_MARCA = "[A-Z]+[a-z]*([ -]?[A-Z][a-z]+)*";
    private static final String ER_MATRICULA = "\\d{4}[^AEIOU]{3}";
    String marca;
    String modelo;
    String matricula;

    public Vehiculo(String marca, String modelo, String matricula){
        validarMarca(marca);
        validarModelo(modelo);
        validarMatricula(matricula);
    }

    public Vehiculo(Vehiculo vehiculo){
        Objects.requireNonNull(vehiculo, "No es posible copiar un vehículo nulo.");
        marca = vehiculo.marca();
        modelo = vehiculo.modelo();
        matricula = vehiculo.matricula();
    }

    public String matricula() {
        return matricula;
    }
    public String modelo() {
        return modelo;
    }
    public String marca() {
        return marca;
    }

    private void validarMarca(String marca) {
        Objects.requireNonNull(marca, "La marca no puede ser nula.");
        if (!marca.matches(ER_MARCA)){
            throw new IllegalArgumentException("La marca no tiene un formato válido.");
        }
        this.marca = marca;
    }

    private void validarModelo(String modelo) {
        Objects.requireNonNull(modelo, "El modelo no puede ser nulo.");
        if (modelo.isBlank()){
            throw new IllegalArgumentException("El modelo no puede estar en blanco.");
        }
        this.modelo = modelo;
    }

    private void validarMatricula(String matricula) {
        Objects.requireNonNull(matricula, "La matrícula no puede ser nula.");
        if (!matricula.matches(ER_MATRICULA)){
            throw new IllegalArgumentException("La matrícula no tiene un formato válido.");
        }
        this.matricula = matricula;
    }

    public static Vehiculo get(String matricula){
        return new Vehiculo("Renault", "Megane", matricula);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return Objects.equals(marca, vehiculo.marca) && Objects.equals(modelo, vehiculo.modelo) && Objects.equals(matricula, vehiculo.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marca, modelo, matricula);
    }

    @Override
    public String toString() {
        return String.format("%s %s - %s", this.marca, this.modelo, this.matricula);
    }
}
