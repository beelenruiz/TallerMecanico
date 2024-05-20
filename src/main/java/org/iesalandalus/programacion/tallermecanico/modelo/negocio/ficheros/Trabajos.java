package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
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
    private static final String FICHERO_TRABAJOS = String.format("%s%s%s", "datos", File.separator, "trabajos.xml");
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String RAIZ = "trabajos";
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

    @Override
    public void comenzar() {
        Document documentoXml = UtilidadesXml.leerDocumentoXml(FICHERO_TRABAJOS);
        if (documentoXml != null) {
            procesarDocumentoXml(documentoXml);
            System.out.printf("Fichero %s leído correctamente.%n", FICHERO_TRABAJOS);
        }
    }
    private void procesarDocumentoXml(Document documentoXml) {
        NodeList trabajos = documentoXml.getElementsByTagName("trabajos");
        for(int i=0; i < trabajos.getLength(); i++){
            Node trabajo = trabajos.item(i);
            try {
                if (trabajo.getNodeType() == Node.ELEMENT_NODE) {
                    insertar(getTrabajo((Element) trabajo));
                }
            } catch (OperationNotSupportedException|IllegalArgumentException|NullPointerException e) {
                System.out.printf("Error al leer el trabajo %d. --> %s%n", i, e.getMessage());
            }
        }
    }
    private Trabajo getTrabajo(Element elemento) throws OperationNotSupportedException {
        Cliente cliente = Cliente.get(elemento.getAttribute(CLIENTE));
        cliente = Clientes.getInstancia().buscar(cliente);
        Vehiculo vehiculo = Vehiculo.get(elemento.getAttribute(VEHICULO));
        vehiculo = Vehiculos.getInstancia().buscar(vehiculo);
        LocalDate fechaInicio = LocalDate.parse(elemento.getAttribute(FECHA_INICIO), FORMATO_FECHA);
        String tipo = elemento.getAttribute(TIPO);
        Trabajo trabajo = null;
        if (tipo.equals(REVISION)) {
            trabajo = new Revision(cliente, vehiculo, fechaInicio);
        } else if (tipo.equals(MECANICO)) {
            trabajo = new Mecanico(cliente, vehiculo, fechaInicio);
            if (elemento.hasAttribute(PRECIO_MATERIAL)) {
                ((Mecanico) trabajo).anadirPrecioMaterial(Float.parseFloat(elemento.getAttribute(PRECIO_MATERIAL)));
            }
        }
        if (elemento.hasAttribute(HORAS) && trabajo != null) {
            int horas = Integer.parseInt(elemento.getAttribute(HORAS));
            trabajo.anadirHoras(horas);
        }
        if (elemento.hasAttribute(FECHA_FIN) && trabajo != null) {
            LocalDate fechaFin = LocalDate.parse(elemento.getAttribute(FECHA_FIN), FORMATO_FECHA);
            trabajo.cerrar(fechaFin);
        }
        return trabajo;
    }

    @Override
    public void terminar() {
        Document documentoXml = crearDocumentoXml();
        UtilidadesXml.escribirDocumentoXml(documentoXml, FICHERO_TRABAJOS);
    }
    private Document crearDocumentoXml() {
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXml = null;
        if (constructor != null) {
            documentoXml = constructor.newDocument();
            documentoXml.appendChild(documentoXml.createElement(RAIZ));
            for(Trabajo trabajo: coleccionTrabajo){
                Element elementoTrabajo = getElemento(documentoXml, trabajo);
                if (elementoTrabajo.getNodeType() == Node.ELEMENT_NODE) {
                    documentoXml.getDocumentElement().appendChild(elementoTrabajo);
                }
            }
        }
        return documentoXml;
    }
    private Element getElemento(Document documentoXml, Trabajo trabajo){
        Element elemento = documentoXml.createElement(TRABAJO);
        elemento.setAttribute(CLIENTE, trabajo.getCliente().getDni());
        elemento.setAttribute(VEHICULO, trabajo.getVehiculo().matricula());
        elemento.setAttribute(FECHA_INICIO, trabajo.getFechaInicio().format(FORMATO_FECHA));
        if (trabajo.getFechaFin() != null) {
            elemento.setAttribute(FECHA_FIN, trabajo.getFechaFin().format(FORMATO_FECHA));
        }
        if (trabajo.getHoras() != 0) {
            elemento.setAttribute(HORAS, String.format("%d", trabajo.getHoras()));
        }
        if (trabajo instanceof Revision) {
            elemento.setAttribute(TIPO, REVISION);
        } else if (trabajo instanceof Mecanico mecanico) {
            elemento.setAttribute(TIPO, MECANICO);
            if (mecanico.getPrecioMaterial() != 0) {
                elemento.setAttribute(PRECIO_MATERIAL, String.format(Locale.US, "%f", mecanico.getPrecioMaterial()));
            }
        }
        return elemento;
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
    public Map<TipoTrabajo, Integer> getEstadisticasMensuales(LocalDate mes){
        Objects.requireNonNull(mes, "El mes no puede ser nulo.");
        Map<TipoTrabajo, Integer> estadisticas = inicializarEstadisticas();
        for (Trabajo trabajo : coleccionTrabajo) {
            LocalDate fecha = trabajo.getFechaInicio();
            if (fecha.getMonthValue() == mes.getMonthValue() && fecha.getYear() == mes.getYear()) {
                TipoTrabajo tipoTrabajo = TipoTrabajo.get(trabajo);
                estadisticas.put(tipoTrabajo, estadisticas.get(tipoTrabajo) + 1);
            }
        }
        return estadisticas;
    }
    @Override
    public Map<TipoTrabajo, Integer> inicializarEstadisticas(){
        Map<TipoTrabajo, Integer> estadisticas = new EnumMap<>(TipoTrabajo.class);
        for (TipoTrabajo tipoTrabajo : TipoTrabajo.values()) {
            estadisticas.put(tipoTrabajo, 0);
        }
        return estadisticas;
    }

    @Override
    public void insertar(Trabajo trabajo) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No se puede insertar un trabajo nulo.");
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajo.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) throws OperationNotSupportedException {
        for (Trabajo trabajo : coleccionTrabajo) {
            if (!trabajo.estaCerrado()) {
                if (trabajo.getCliente().equals(cliente)) {
                    throw new OperationNotSupportedException("El cliente tiene otro trabajo en curso.");
                } else if (trabajo.getVehiculo().equals(vehiculo)) {
                    throw new OperationNotSupportedException("El vehículo está actualmente en el taller.");
                }
            } else {
                if (trabajo.getCliente().equals(cliente) && !fechaInicio.isAfter(trabajo.getFechaFin())) {
                    throw new OperationNotSupportedException("El cliente tiene otro trabajo posterior.");
                } else if (trabajo.getVehiculo().equals(vehiculo) && !fechaInicio.isAfter(trabajo.getFechaFin())) {
                    throw new OperationNotSupportedException("El vehículo tiene otro trabajo posterior.");
                }
            }
        }
    }

    @Override
    public void anadirHoras(Trabajo trabajo, int horas) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No puedo añadir horas a un trabajo nulo.");
        Trabajo trabajoEncontrado = getTrabajoAbierto(trabajo.getVehiculo());
        trabajoEncontrado.anadirHoras(horas);
    }
    @Override
    public void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No puedo añadir precio del material a un trabajo nulo.");
        Trabajo trabajoEncontrado = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajoEncontrado instanceof Mecanico mecanico) {
            mecanico.anadirPrecioMaterial(precioMaterial);
        } else {
            throw new OperationNotSupportedException("No se puede añadir precio al material para este tipo de trabajos.");
        }

    }

    private Trabajo getTrabajoAbierto(Vehiculo vehiculo) throws OperationNotSupportedException {
        Objects.requireNonNull(vehiculo, "No puedo operar sobre un vehículo nulo.");
        Trabajo trabajoEncontrado = null;
        Iterator<Trabajo> iteradorTrabajos = coleccionTrabajo.iterator();
        while (iteradorTrabajos.hasNext() && trabajoEncontrado == null) {
            Trabajo trabajo = iteradorTrabajos.next();
            if (trabajo.getVehiculo().equals(vehiculo) && !trabajo.estaCerrado()) {
                trabajoEncontrado = trabajo;
            }
        }
        if (trabajoEncontrado == null) {
            throw new OperationNotSupportedException("No existe ningún trabajo abierto para dicho vehículo.");
        }
        return trabajoEncontrado;
    }

    @Override
    public void cerrar(Trabajo trabajo, LocalDate fechaFin) throws OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No puedo cerrar un trabajo nulo.");
        Trabajo trabajoEncontrado = getTrabajoAbierto(trabajo.getVehiculo());
        trabajoEncontrado.cerrar(fechaFin);
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede buscar un trabajo nulo.");
        int indice = coleccionTrabajo.indexOf(trabajo);
        return (indice == -1) ? null : coleccionTrabajo.get(indice);
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
