package com.ejercicio1.libreria.Servicios;

import com.ejercicio1.libreria.Errores.ErrorServicio;
import com.ejercicio1.libreria.entidades.Cliente;
import com.ejercicio1.libreria.entidades.repositorios.ClienteRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServicio {

    @Autowired
    ClienteRepositorio cr;

    @Transactional
    public Cliente crearCliente(Long documento, String nombre, String apellido, String telefono) throws ErrorServicio {

        validarCliente(documento, nombre, apellido, telefono);
        Cliente c = cr.buscarPorDocumento(documento);

        if (c != null) {

            throw new ErrorServicio("El cliente ya existe");
        }

        Cliente c1 = new Cliente();
        c1.setDocumento(documento);
        c1.setNombre(nombre);
        c1.setApellido(apellido);
        c1.setTelefono(telefono);
        c1.setAlta(Boolean.TRUE);
        return cr.save(c1);

    }

    @Transactional
    public Cliente modificarCliente(String id, Long documento, String nombre, String apellido, String telefono) throws ErrorServicio {

        
        validarCliente(documento, nombre, apellido, telefono);
        Cliente cliente = new Cliente();

        cliente = cr.buscarPorId(id);

        if (cliente != null) {

            cliente.setId(id);
            cliente.setDocumento(documento);
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);

            return cr.save(cliente);

        } else {
            
            throw new ErrorServicio("El cliente buscado no existe");
        }

    }

    @Transactional
    public void darDeBajaCliente(String id) throws ErrorServicio {

        Cliente c1 = cr.buscarPorId(id);

        if (c1 != null) {

            c1.setAlta(Boolean.FALSE);
            cr.save(c1);
        } else {

            throw new ErrorServicio("No se encontro el cliente ingresado");
        }

    }

    @Transactional
    public void darDeAltaCliente(String id) throws ErrorServicio {

        Cliente c1 = cr.buscarPorId(id);

        if (c1 != null) {

            c1.setAlta(Boolean.TRUE);
            cr.save(c1);
        } else {

            throw new ErrorServicio("No se encontro el cliente ingresado");
        }

    }

    @Transactional(readOnly = true)
    public List<Cliente> listarClientes() throws ErrorServicio {
        return cr.findAll();

    }

    @Transactional(readOnly = true)
    public Cliente buscarPorDocumento(Long documento) {

        return cr.buscarPorDocumento(documento);
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(String id) {

        return cr.buscarPorId(id);
    }

    public void validarCliente(Long documento, String nombre, String apellido, String telefono) throws ErrorServicio {

        if (documento == null || documento <= 0) {

            throw new ErrorServicio("El documento no puede ser 0, menor a 0 o nulo");
        }

        if (nombre == null || nombre.isEmpty()) {

            throw new ErrorServicio("El nombre no puede ser nulo");
        }

        if (apellido == null || apellido.isEmpty()) {

            throw new ErrorServicio("El apellido no puede ser nulo");
        }

        if (telefono == null || telefono.isEmpty()) {

            throw new ErrorServicio("El telefono no puede ser nulo");
        }

    }

}
