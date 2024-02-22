package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.Objects;

public class Vista {
    private Controlador controlador;
    Consola consola;
    public void setControlador(Controlador controlador){
        if (controlador != null){
            this.controlador = controlador;
        }
    }

    public  void comenzar() throws OperationNotSupportedException {
        consola.mostrarCabecera("--- Taller mecánico ---");
        Opcion opcion;
        do {
            consola.mostrarMenu();
            opcion = consola.elegirOpcion();
            ejecutar(opcion);
        }while (opcion != Opcion.SALIR);

    }
    public  void terminar(){
        System.out.println("Fin");
    }

    private void ejecutar(Opcion opcion) throws OperationNotSupportedException {
        switch (opcion){
            case BORRAR_CLIENTE -> borrarCliente();
            case SALIR -> salir();
            case BUSCAR_CLIENTE -> buscarCliente();
            case BORRAR_REVISION -> borrarRevision();
            case BORRAR_VEHICULO -> borrarVehiculo();
            case BUSCAR_REVISION -> buscarRevision();
            case BUSCAR_VEHICULO -> buscarVehiculo();
            case CERRAR_REVISION -> cerrarRevision();
            case LISTAR_CLIENTES -> listarClientes();
            case INSERTAR_CLIENTE -> insertarCliente();
            case LISTAR_VEHICULOS -> listarVehiculo();
            case INSERTAR_REVISION -> insertarRevision();
            case INSERTAR_VEHICULO -> insertarVehiculo();
            case LISTAR_REVISIONES -> listarRevision();
            case MODIFICAR_CLIENTE -> modificarCliente();
            case ANADIR_HORAS_REVISION -> anadirHoras();
            case LISTAR_REVISIONES_CLIENTE -> listarRevisionesCliente();
            case LISTAR_REVISIONES_VEHICULO -> listarRevisionesVehiculo();
            case ANADIR_PRECIO_MATERIAL_REVISION -> anadirPrecioMaterial();
        }
    }

    private void cerrarRevision() throws OperationNotSupportedException {
        Revision revision = consola.leerRevision();
        LocalDate localDate = consola.leerFechaCierre();
        controlador.cerrar(revision,localDate);
        System.out.println("La revision" + revision + "ha sido cerrada a las " + localDate);
    }
    private void salir(){
        controlador.terminar();
    }

    private void insertarCliente() throws OperationNotSupportedException {
        consola.mostrarCabecera("Insertar cliente ");
        Cliente cliente = consola.leerCliente();
        controlador.insertar(cliente);
        System.out.println("El cliente:" + cliente + "ha sido añadido a la lista .");
    }
    private void insertarVehiculo() throws OperationNotSupportedException {
        consola.mostrarCabecera("Insertar vehiculo ");
        Vehiculo vehiculo = consola.leerVehiculo();
        controlador.insertar(vehiculo);
        System.out.println("El vehiculo:" + vehiculo + "ha sido añadido a la lista.");

    }
    private void insertarRevision() throws OperationNotSupportedException {
        consola.mostrarCabecera("Insertar revision ");
        Revision revision = consola.leerRevision();
        controlador.insertar(revision);
        System.out.println("La revision: " + revision + "ha sido añadida a la lista.");

    }

    private void borrarCliente() throws OperationNotSupportedException {
        consola.mostrarCabecera("Borrar cliente ");
        Cliente cliente = consola.leerCliente();
        controlador.borrar(cliente);
        System.out.println("El cliente : " + cliente + "ha sido borrado.");
    }
    private void borrarVehiculo() throws OperationNotSupportedException {
        consola.mostrarCabecera("Borrar vehiculo ");
        Vehiculo vehiculo = consola.leerVehiculo();
        controlador.borrar(vehiculo);
        System.out.println("El vehiculo : " + vehiculo + "ha sido borrado");
    }
    private void borrarRevision() throws OperationNotSupportedException {
        consola.mostrarCabecera("Borrar revision ");
        Revision revision = consola.leerRevision();
        controlador.borrar(revision);
        System.out.println("La revision "+ revision + "ha sido borrada.");
    }

    private void buscarCliente(){
        consola.mostrarCabecera("Buscar cliente ");
        Cliente cliente = consola.leerCliente();
        controlador.buscar(cliente);
        System.out.println("El cliente : " + cliente + "ha sido encontrado.");

    }
    private void buscarVehiculo(){
        consola.mostrarCabecera("Buscar vehiculo ");
        Vehiculo vehiculo = consola.leerVehiculo();
        controlador.buscar(vehiculo);
        System.out.println("El vehiculo : " + vehiculo + "ha sido encontrado");
    }
    private void buscarRevision() throws OperationNotSupportedException {
        consola.mostrarCabecera("Buscar revision ");
        Revision revision = consola.leerRevision();
        controlador.buscar(revision);
        System.out.println("La revision "+ revision + "ha sido encontrado.");

    }

    private void listarClientes(){
        consola.mostrarCabecera("Listar cliente ");
        controlador.getClientes();
    }
    private void listarVehiculo(){
        consola.mostrarCabecera("Listar vehiculo ");
        controlador.getVehiculos();
    }

    private void listarRevision(){
        consola.mostrarCabecera("Listar revision ");
        controlador.getRevisiones();
    }

    private void  modificarCliente() throws OperationNotSupportedException {
        consola.mostrarCabecera("Modificar cliente ");
        controlador.modificar(consola.leerClienteDni(),consola.leerNuevoNombre(),consola.leerNuevoTelefono());

    }

    private void anadirHoras() throws OperationNotSupportedException {
        consola.mostrarCabecera("Añadir horas ");
        Revision revision = consola.leerRevision();
        int horas = consola.leerHoras();
        controlador.anadirHoras(revision,horas);
        System.out.println("Se han añadido" + horas + "a la revision" + revision);
    }

    private void anadirPrecioMaterial() throws OperationNotSupportedException {
        consola.mostrarCabecera("Añadir precio material ");
        Revision revision = consola.leerRevision();
        float precioMaterial = consola.leerPrecioMaterial();
        controlador.anadirPrecioMaterial(revision, precioMaterial);
        System.out.println("Se han añadido" + precioMaterial + "a la revision" + revision);
    }

    public void listarRevisionesCliente(){
        consola.mostrarCabecera("Listar revisiones cliente ");
        controlador.getRevisiones(consola.leerCliente());
    }
    public void listarRevisionesVehiculo(){
        consola.mostrarCabecera("Listar revisiones vehiculo ");
        controlador.getRevisiones(consola.leerVehiculo());
    }
}
