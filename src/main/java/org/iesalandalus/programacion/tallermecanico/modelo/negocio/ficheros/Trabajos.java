package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Trabajos implements ITrabajos {
    private static final String FICHERO_TRABAJOS = "trabajos.xml";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String RAIZ = "datos";
    private static final String TRABAJO = "trabajo";
    private static final String CLIENTE = "cliente";
    private static final String VEHICULO = "vehiculo";
    private static final String FECHA_INICIO = "fechaInicio";
    private static final String FECHA_FIN = "fechaFin";
    private static final String HORAS = "horas";
    private static final String PRECIO_MATERIAL = "precioMaterial";
    private static final String TIPO = "tipo";
    private static final String REVISION = "revision";
    private static final String MECANICO = "mecanico";

    private static Trabajos instancia;

    List<Trabajo> coleccionTrabajo;
    private Trabajos(){
        coleccionTrabajo = new ArrayList<>();
    }

    static Trabajos getInstancia(){
        if(instancia == null){
            instancia = new Trabajos();
        }
        return  instancia;
    }

    public void Comenzar(){
        procesarDocumentoXml(UtilidadesXml.leerDocumentoXml(String.format("%s%s%s", RAIZ, File.separator, FICHERO_TRABAJOS)));
    }
    private void procesarDocumentoXml(Document documentoXml) {
        NodeList trabajos = documentoXml.getElementsByTagName("trabajos");
        for(int i=0; i < trabajos.getLength(); i++){
            Node trabajo = trabajos.item(i);
            if(trabajo.getNodeType() == Node.ELEMENT_NODE){
                coleccionTrabajo.add(getTrabajo((Element) trabajo));
            }
        }
    }
    private Trabajo getTrabajo(Element elemento){
        Objects.requireNonNull(elemento, "El elemento no puede ser nulo.");
        Trabajo trabajo = null;
        Cliente cliente = Cliente.get(elemento.getAttribute(CLIENTE));
        Vehiculo vehiculo = Vehiculo.get(elemento.getAttribute(VEHICULO));
        LocalDate fechaInicio = LocalDate.parse(elemento.getAttribute(FECHA_INICIO), FORMATO_FECHA);
        if (elemento.hasAttribute(FECHA_FIN)) {
            LocalDate fechaFin = LocalDate.parse(elemento.getAttribute(FECHA_FIN), FORMATO_FECHA);
        }
        if (elemento.hasAttribute(HORAS)){
            float horas = Float.parseFloat(elemento.getAttribute(HORAS));
        }
        if (elemento.getAttribute(TIPO).equals(MECANICO)){
            float precioMaterial = Float.parseFloat(elemento.getAttribute(PRECIO_MATERIAL));
            trabajo = new Mecanico(cliente, vehiculo, fechaInicio);
        } else {
            trabajo = new Revision(cliente, vehiculo, fechaInicio);
        }
        return trabajo;
    }

    public void terminar(){
        UtilidadesXml.escribirDocumentoXml(crearDocumentoXml(),String.format("%s%s%s", RAIZ, File.separator, FICHERO_TRABAJOS));
    }
    private Document crearDocumentoXml() {
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXml = null;
        if (constructor != null) {
            documentoXml = constructor.newDocument();
            documentoXml.appendChild(documentoXml.createElement("trabajos"));
            for(Trabajo trabajo: coleccionTrabajo){
                Element elementoTrabajo = getElemento(documentoXml, trabajo);
                documentoXml.getDocumentElement().appendChild(elementoTrabajo);
            }
        }
        return documentoXml;
    }
    private Element getElemento(Document documentoXml, Trabajo trabajo){
        Objects.requireNonNull(documentoXml, "El documento no puede ser nulo.");
        Objects.requireNonNull(trabajo, "El trabajo  no puede ser nulo.");
        Element elementoTrabajo = documentoXml.createElement(TRABAJO);
        elementoTrabajo.setAttribute(CLIENTE, String.valueOf(trabajo.getCliente()));
        elementoTrabajo.setAttribute(VEHICULO, String.valueOf(trabajo.getVehiculo()));
        elementoTrabajo.setAttribute(FECHA_INICIO, String.valueOf(trabajo.getFechaInicio()));
        if (trabajo.estaCerrado()){
            elementoTrabajo.setAttribute(FECHA_FIN, String.valueOf(trabajo.getFechaFin()));
        }
        elementoTrabajo.setAttribute(HORAS, String.valueOf(trabajo.getHoras()));
        if (trabajo instanceof Mecanico){
            elementoTrabajo.setAttribute(PRECIO_MATERIAL, String.valueOf(((Mecanico)trabajo).getPrecioMaterial()));
        }
        return elementoTrabajo;
    }

    @Override
    public List<Trabajo> get(){
        return new ArrayList<>(coleccionTrabajo);
    }
    @Override
    public List<Trabajo> get(Cliente cliente){
        List<Trabajo> trabajosCliente = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajo){
            if ((trabajo.getCliente()).equals(cliente)){
                trabajosCliente.add(trabajo);
            }
        }
        return trabajosCliente;
    }
    @Override
    public List<Trabajo> get(Vehiculo vehiculo){
        List<Trabajo> trabajosVehiculo = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajo){
            if ((trabajo.getVehiculo()).equals(vehiculo)){
                trabajosVehiculo.add(trabajo);
            }
        }
        return trabajosVehiculo;
    }



    @Override
    public void insertar(Trabajo trabajo) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No se puede insertar un trabajo nulo.");
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajo.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) throws OperationNotSupportedException {
        for (Trabajo trabajo : coleccionTrabajo) {
            if ((trabajo.getCliente()).equals(cliente)){
                if (!trabajo.estaCerrado()){
                    throw new OperationNotSupportedException("El cliente tiene otro trabajo en curso.");
                } else {
                    if (!fechaInicio.isAfter(trabajo.getFechaFin())){
                        throw new OperationNotSupportedException("El cliente tiene otro trabajo posterior.");
                    }
                }
            }
            if ((trabajo.getVehiculo()).equals(vehiculo)){
                if (!trabajo.estaCerrado()){
                    throw new OperationNotSupportedException("El vehículo está actualmente en el taller.");
                } else {
                    if (!fechaInicio.isAfter(trabajo.getFechaFin())){
                        throw new OperationNotSupportedException("El vehículo tiene otro trabajo posterior.");
                    }
                }
            }
        }
    }

    @Override
    public void anadirHoras(Trabajo trabajo, int horas) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No puedo añadir horas a un trabajo nulo.");
        if (!coleccionTrabajo.contains(trabajo)){
            throw new OperationNotSupportedException("No existe ningún trabajo abierto para dicho vehículo.");
        }
        int indice = coleccionTrabajo.indexOf(trabajo);
        Trabajo trabajoEncontrado = coleccionTrabajo.get(indice);
        if (indice != -1){
            trabajoEncontrado.anadirHoras(horas);
        }
    }
    @Override
    public void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No puedo añadir precio del material a un trabajo nulo.");
        if (!coleccionTrabajo.contains(trabajo)){
            throw new OperationNotSupportedException("No existe ningún trabajo abierto para dicho vehículo.");
        }
        if (trabajo instanceof Mecanico mecanico){
            mecanico.anadirPrecioMaterial(precioMaterial);
        } else {
            throw new OperationNotSupportedException("No se puede añadir precio al material para este tipo de trabajos.");
        }

    }

    private Trabajo getTrabajoAbierto(Vehiculo vehiculo){
        return null;
    }

    @Override
    public void cerrar(Trabajo trabajo, LocalDate fechaFin) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No puedo cerrar un trabajo nulo.");
        if (!coleccionTrabajo.contains(trabajo)) {
            throw new OperationNotSupportedException("No existe ningún trabajo abierto para dicho vehículo.");
        }
        trabajo.cerrar(fechaFin);
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede buscar un trabajo nulo.");
        int indice = coleccionTrabajo.indexOf(trabajo);
        Trabajo trabajoEncontrado;
        if (indice != -1){
            trabajoEncontrado = coleccionTrabajo.get(indice);
        } else {
            trabajoEncontrado = null;
        }
        return trabajoEncontrado;
    }

    @Override
    public void borrar(Trabajo trabajo) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No se puede borrar un trabajo nulo.");
        if (!coleccionTrabajo.contains(trabajo)){
            throw new OperationNotSupportedException("No existe ningún trabajo igual.");
        }
        coleccionTrabajo.remove(trabajo);
    }


}
