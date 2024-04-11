package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public enum Evento {
    INSERTAR_CLIENTE(1, "Insertar un cliente"),
    BUSCAR_CLIENTE(2, "Buscar un cliente"),
    BORRAR_CLIENTE(3, "Borrar un cliente"),
    LISTAR_CLIENTES(4, "Listar todos los clientes"),
    MODIFICAR_CLIENTE(5, "Modificar un cliente"),
    INSERTAR_VEHICULO(6, "Insertar un vehículo"),
    BUSCAR_VEHICULO(7, "Busar un vehículo"),
    BORRAR_VEHICULO(8, "Borrar un vehículo"),
    LISTAR_VEHICULOS(9, "Listar todos los vehículos"),
    INSERTAR_REVISION(10, "Insertar una revisión"),
    INSERTAR_MECANICO(11, "Insertar un trabajo mecánico"),
    BUSCAR_TRABAJO(12, "Buscar un trabajo"),
    BORRAR_TRABAJO(13, "Borrar un trabajo"),
    LISTAR_TRABAJOS(14, "Listar todas los trabajos"),
    LISTAR_TRABAJOS_CLIENTE(15, "Listar todas los trabajos de un cliente"),
    LISTAR_TRABAJOS_VEHICULO(16, "Listar todas los trabajos de un vehículo"),
    ANADIR_HORAS_TRABAJO(17, "Añadir horas a un trabajo"),
    ANADIR_PRECIO_MATERIAL_TRABAJO(18, "Añadir precio de material a un trabajo"),
    CERRAR_TRABAJO(19, "Cerrar un trabajo"),
    SALIR(20, "Salir");

    private final int codigo;
    private final String texto;
    private static Map<Integer, Evento> eventos = new HashMap<>();
    static {
        for (Evento evento : values()) {
            eventos.put(evento.codigo, evento);
        }
    }

    private Evento(int codigo, String texto){
        this.codigo = codigo;
        this.texto = texto;
    }
    public static boolean esValido(int codigo){
        return eventos.containsKey(codigo);
    }
    public static Evento get(int codigo) {
        if (!esValido(codigo)) {
            throw new IllegalArgumentException("Opción inválida");
        }
        return eventos.get(codigo);
    }

    @Override
    public String toString() {
        return String.format("%s. %s", this.codigo, this.texto);
    }
}
