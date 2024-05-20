package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
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

public class Clientes implements IClientes {
    private static final String FICHERO_CLIENTES = String.format("%s%s%s", "datos", File.separator, "clientes.xml");
    private static final String RAIZ = "clientes";
    private static final String CLIENTE = "cliente";
    private static final String NOMBRE = "nombre";
    private static final String DNI = "dni";
    private static final String TELEFONO = "telefono";

    private static Clientes instancia;
    private final List<Cliente> coleccionCliente;
    private Clientes(){
        coleccionCliente = new ArrayList<>();
    }

    static Clientes getInstancia(){
        if(instancia == null){
            instancia = new Clientes();
        }
        return instancia;
    }

    @Override
    public void comenzar(){
        Document documentoXml = UtilidadesXml.leerDocumentoXml(FICHERO_CLIENTES);
        if (documentoXml != null) {
            procesarDocumentoXml(documentoXml);
            System.out.printf("Fichero %s leído correctamente.%n", FICHERO_CLIENTES);
        }
    }
    private void procesarDocumentoXml(Document documentoXml){
        NodeList clientes = documentoXml.getElementsByTagName("clientes");
        for(int i = 0; i < clientes.getLength(); i++){
            Node cliente = clientes.item(i);
            try {
                if (cliente.getNodeType() == Node.ELEMENT_NODE) {
                    insertar(getCliente((Element) cliente));
                }
            }catch (OperationNotSupportedException | IllegalArgumentException|NullPointerException e) {
                System.out.printf("Error al leer el cliente %d. --> %s%n", i, e.getMessage());
            }
        }
    }
    private Cliente getCliente(Element elemento){
        Objects.requireNonNull(elemento, "El elemento no puede ser nulo.");
        String nombre = elemento.getAttribute(NOMBRE);
        String dni = elemento.getAttribute(DNI);
        String telefono = elemento.getAttribute(TELEFONO);
        return new Cliente(nombre, dni, telefono);
    }

    @Override
    public void terminar(){
        Document documentoXml = crearDocumentoXml();
        UtilidadesXml.escribirDocumentoXml(documentoXml, FICHERO_CLIENTES);
    }
    private Document crearDocumentoXml(){
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXml = null;
        if (constructor != null) {
            documentoXml = constructor.newDocument();
            documentoXml.appendChild(documentoXml.createElement(RAIZ));
            for(Cliente cliente: coleccionCliente){
               Element elementoCliente = getElemento(documentoXml, cliente);
                if (elementoCliente.getNodeType() == Node.ELEMENT_NODE) {
                    documentoXml.getDocumentElement().appendChild(elementoCliente);
                }
            }
        }
        return documentoXml;
    }
    private Element getElemento(Document documentoXml, Cliente cliente){
        Objects.requireNonNull(documentoXml, "El documento no puede ser nulo.");
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Element elementoCliente = documentoXml.createElement(CLIENTE);
        elementoCliente.setAttribute(NOMBRE, cliente.getNombre());
        elementoCliente.setAttribute(DNI, cliente.getDni());
        elementoCliente.setAttribute(TELEFONO, cliente.getTelefono());
        return elementoCliente;
    }

    @Override
    public List<Cliente> get(){
        return new ArrayList<>(coleccionCliente);
    }

    @Override
    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede insertar un cliente nulo.");
        if (!coleccionCliente.contains(cliente)){
            coleccionCliente.add(cliente);
        } else {
            throw new OperationNotSupportedException("Ya existe un cliente con ese DNI.");
        }
    }

    @Override
    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        boolean modificado = false;
        Objects.requireNonNull(cliente, "No se puede modificar un cliente nulo.");
        Cliente clienteEncontrado = buscar(cliente);
        if (clienteEncontrado == null){
            throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
        }
        if (nombre != null && !nombre.isBlank()){
            clienteEncontrado.setNombre(nombre);
            modificado = true;
        }
        if (telefono != null && !telefono.isBlank()){
            clienteEncontrado.setTelefono(telefono);
            modificado = true;
        }
        return modificado;
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        System.out.println("buscando");
        Objects.requireNonNull(cliente, "No se puede buscar un cliente nulo.");
        int indice = coleccionCliente.indexOf(cliente);
        return (indice == -1) ? null : coleccionCliente.get(indice);
    }

    @Override
    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede borrar un cliente nulo.");
        if (coleccionCliente.contains(cliente)){
            coleccionCliente.remove(cliente);
        } else {
            throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
        }
    }
}
