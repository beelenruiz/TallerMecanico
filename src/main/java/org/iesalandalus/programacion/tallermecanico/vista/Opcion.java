package org.iesalandalus.programacion.tallermecanico.vista;

import java.util.Map;
import java.util.TreeMap;

public enum Opcion{
    INSERTAR_CLIENTE(1, "Insertar un cliente"),
    BUSCAR_CLIENTE(2, "Buscar un cliente"),
    BORRAR_CLIENTE(3, "Borar un cliente"),
    LISTAR_CLIENTES(4, "Listar todos los clientes"),
    MODIFICAR_CLIENTE(5, "Modificar un cliente"),
    INSERTAR_VEHICULO(6, "Insertar un vehículo"),
    BUSCAR_VEHICULO(7, "Busar un vehículo"),
    BORRAR_VEHICULO(8, "Borrar un vehículo"),
    LISTAR_VEHICULOS(9, "Listar todos los vehículos"),
    INSERTAR_REVISION(10, "Insertar una revisión"),
    BUSCAR_REVISION(11, "Buscar una revisión"),
    BORRAR_REVISION(12, "Borrar una revvisión"),
    LISTAR_REVISIONES(13, "Listar todas las revisiones"),
    LISTAR_REVISIONES_CLIENTE(14, "Listar todas las revisiones de un cliente"),
    LISTAR_REVISIONES_VEHICULO(15, "Listar todas las revisiones de un vehículo"),
    ANADIR_HORAS_REVISION(16, "Añadir horas a una revisión"),
    ANADIR_PRECIO_MATERIAL_REVISION(17, "Añadir precio de material a una revisión"),
    CERRAR_REVISION(18, "Cerrar una revisión"),
    SALIR(19, "Salir");

    private int numeroOpcion;
    private String mensaje;
    private static Map<Integer, String> opciones = new TreeMap<>();
    static {
        for (Opcion opcion : Opcion.values()) {
            opciones.put(opcion.numeroOpcion, opcion.mensaje);
        }
    }

    private Opcion(int numeroOpcion, String mensaje){
        this.numeroOpcion = numeroOpcion;
        this.mensaje = mensaje;
    }
    public static boolean esValida(int numeroOpcion){
        return opciones.containsKey(numeroOpcion);
    }
    public static Opcion get(int numeroOpcion){
        Opcion opcion;
        if (esValida(numeroOpcion)) {
            opcion = Opcion.valueOf(opciones.get(numeroOpcion));
        } else {
            throw new IllegalArgumentException("Número de opción no válido");
        }
        return opcion;
    }

    @Override
    public String toString() {
        return String.format("%s. %s", this.numeroOpcion, this.mensaje);
    }
}
