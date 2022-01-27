package com.ejercicio1.libreria.Servicios;

import com.ejercicio1.libreria.Errores.ErrorServicio;
import com.ejercicio1.libreria.entidades.Cliente;
import com.ejercicio1.libreria.entidades.Libro;
import com.ejercicio1.libreria.entidades.Prestamo;
import com.ejercicio1.libreria.entidades.repositorios.PrestamoRepositorio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoServicio {

    @Autowired
    PrestamoRepositorio pr;

    @Autowired
    ClienteServicio cs;

    @Autowired
    LibroServicio ls;

    @Transactional
    public Prestamo crearPrestamo(Long documento, String titulo, Date fechaDevolucion) throws ErrorServicio {

        validar(documento, titulo);

        Cliente c = cs.buscarPorDocumento(documento);

        if (c != null) {

            Libro libro = ls.buscarLibroPorTitulo(titulo);

            if (libro != null) {
                Prestamo p = new Prestamo();

                p.setFechaPrestamo(new Date());
                p.setCliente(c);
                p.setLibro(libro);
                p.setAlta(Boolean.TRUE);
                p.setFechaDevolucion(fechaDevolucion);

                libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() + 1);
                libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() - 1);

                return pr.save(p);
            }

            throw new ErrorServicio("No existe el cliente ingresado");
        }

        throw new ErrorServicio("No existe el libro ingresado");
    }

    @Transactional(readOnly = true)
    public List<Prestamo> cargarListaPrestamo() throws ErrorServicio {

        return pr.findAll();

    }

    @Transactional
    public Prestamo modificarPrestamo(String id, Date fechaPrestamo, Date fechaDevolucion, String libro, String cliente) throws ErrorServicio {

        validarPrestamo(libro, cliente);

        Prestamo p = pr.buscarPorId(id);

        if (p != null) {

            p.setFechaPrestamo(fechaPrestamo);
            p.setFechaDevolucion(fechaDevolucion);

            Libro l = ls.buscarLibroPorId(libro);

            if (l != null) {

                p.setLibro(l);

            }

            Cliente c = cs.buscarPorId(id);

            if (c != null) {

                p.setCliente(c);
            }

            return pr.save(p);
        }

        throw new ErrorServicio("El prestamo no existe");
    }

    @Transactional
    public void darBaja(String id) throws ErrorServicio {

        Prestamo p = pr.buscarPorId(id);

        if (p != null) {

            p.setAlta(Boolean.FALSE);

            pr.save(p);
        } else {

            throw new ErrorServicio("No existe ese prestamo");
        }

    }

    @Transactional
    public void darAlta(String id) throws ErrorServicio {

        Prestamo p = pr.buscarPorId(id);

        if (p != null) {

            p.setAlta(Boolean.TRUE);

            pr.save(p);
        } else {

            throw new ErrorServicio("No existe ese prestamo");
        }

    }

    @Transactional(readOnly = true)
    public Prestamo buscarPorId(String id) {

        return pr.buscarPorId(id);
    }
    

    public void validar(Long documento, String titulo) throws ErrorServicio {

        if (documento == null || documento <= 0) {

            throw new ErrorServicio("El documento no puede ser nulo");

        }

        if (titulo == null || titulo.isEmpty()) {

            throw new ErrorServicio("El titulo no puede ser nulo");

        }
    }

    public void validarPrestamo(String libro, String cliente) throws ErrorServicio {

        if (libro == null || libro.isEmpty()) {

            throw new ErrorServicio("El libro no puede ser Nulo");
        }

        if (cliente == null || cliente.isEmpty()) {

            throw new ErrorServicio("El cliente no puede ser Nulo");
        }

    }

}
