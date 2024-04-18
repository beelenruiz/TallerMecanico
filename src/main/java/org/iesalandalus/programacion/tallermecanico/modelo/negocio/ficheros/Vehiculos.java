package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vehiculos implements IVehiculos {
    private static final String FICHERO_VEHICULOS = "vehiculos.xml";
    private static final String RAIZ = "datos";
    private static final String VEHICULO = "vehiculo";
    private static final String MARCA = "marca";
    private static final String MODELO = "modelo";
    private static final String MATRICULA = "matricula";

    private static Vehiculos instancia;

    private final List<Vehiculo> coleccionVehiculo;
    public Vehiculos(){
        coleccionVehiculo = new ArrayList<>();
    }

    static Vehiculos getInstancia(){
        if (instancia == null){
            instancia = new Vehiculos();
        }
        return instancia;
    }

    public void comenzar(){
        procesarDocumentoXml(UtilidadesXml.leerDocumentoXml(String.format("%s%s%s", RAIZ, File.separator, FICHERO_VEHICULOS)));
    }
    private void procesarDocumentoXml(Document documentoXml) {
        NodeList vehiculos = documentoXml.getElementsByTagName("vehiculos");
        for (int i = 0; i < vehiculos.getLength(); i++){
            Node vehiculo = vehiculos.item(i);
            if (vehiculo.getNodeType() == Node.ELEMENT_NODE){
                coleccionVehiculo.add(getVehiculo((Element) vehiculo));
            }
        }
    }
    private Vehiculo getVehiculo(Element elemento){
        Objects.requireNonNull(elemento, "El elemento no puede ser nulo.");
        String matricula = elemento.getAttribute(MATRICULA);
        String modelo = elemento.getAttribute(MODELO);
        String marca = elemento.getAttribute(MARCA);
        return new Vehiculo(marca, modelo, matricula);
    }

    public void terminar(){
        UtilidadesXml.escribirDocumentoXml(crearDocumentoXml(), String.format("%s%s%s", RAIZ, File.separator, FICHERO_VEHICULOS));
    }
    private Document crearDocumentoXml() {
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXml = null;
        if (constructor != null) {
            documentoXml = constructor.newDocument();
            documentoXml.appendChild(documentoXml.createElement("clientes"));
            for(Vehiculo vehiculo: coleccionVehiculo){
                Element elementovehiculo = getElemento(documentoXml, vehiculo);
                documentoXml.getDocumentElement().appendChild(elementovehiculo);
            }
        }
        return documentoXml;
    }
    private Element getElemento(Document documentoXml, Vehiculo vehiculo) {
        Objects.requireNonNull(documentoXml, "El documento no puede ser nulo.");
        Objects.requireNonNull(vehiculo, "El vehiculo no puede ser nulo.");
        Element elementoVehiculo = documentoXml.createElement(VEHICULO);
        elementoVehiculo.setAttribute(MATRICULA, vehiculo.matricula());
        elementoVehiculo.setAttribute(MARCA, vehiculo.marca());
        elementoVehiculo.setAttribute(MODELO, vehiculo.modelo());
        return elementoVehiculo;
    }

    @Override
    public List<Vehiculo> get(){
        return new ArrayList<>(coleccionVehiculo);
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        Objects.requireNonNull(vehiculo, "No se puede insertar un vehículo nulo.");
        if (!coleccionVehiculo.contains(vehiculo)){
            coleccionVehiculo.add(vehiculo);
        } else {
            throw new OperationNotSupportedException("Ya existe un vehículo con esa matrícula.");
        }
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "No se puede buscar un vehículo nulo.");
        return (coleccionVehiculo.contains(vehiculo)) ? vehiculo : null;
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        Objects.requireNonNull(vehiculo, "No se puede borrar un vehículo nulo.");
        if (coleccionVehiculo.contains(vehiculo)){
            coleccionVehiculo.remove(vehiculo);
        } else {
            throw new OperationNotSupportedException("No existe ningún vehículo con esa matrícula.");
        }
    }
}
