package com.ejercicio1.libreria.Servicios;

import com.ejercicio1.libreria.Errores.ErrorServicio;
import com.ejercicio1.libreria.entidades.Autor;
import com.ejercicio1.libreria.entidades.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {

    @Autowired
    AutorRepositorio ar;

    @Transactional
    public Autor crearAutor(String nombre) throws ErrorServicio {

        validarAutor(nombre);
        Autor autor = ar.buscarAutorPorNombre(nombre);
        
        if(autor != null){
            
            throw new ErrorServicio("El autor ya existe");
        }
        
        Autor a = new Autor();
        a.setNombre(nombre);
        a.setAlta(Boolean.TRUE);
        return ar.save(a);

    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {
        return ar.findAll();

    }

    @Transactional
    public Autor modificarAutor(String id, String nombre) throws ErrorServicio {

        validarAutor(nombre);
        Autor autor = buscarAutorPorNombre(nombre);
        autor.setNombre(nombre);

        return ar.save(autor);

    }

    @Transactional
    public void darDeBajaAutor(String id) throws ErrorServicio {

        if (id == null || id.isEmpty()) {
            throw new ErrorServicio("El id no puede ser nulo");
        } else {
            Optional<Autor> respuesta = ar.findById(id);

            if (respuesta.isPresent()) {

                Autor autor = respuesta.get();

                if (autor.getId().equals(id)) {

                    autor.setAlta(Boolean.FALSE);
                }
            } else {
                throw new ErrorServicio("No existe el autor solicitado");
            }

        }

    }

    @Transactional
    public void darAltaAutor(String id) throws ErrorServicio {

        if (id == null || id.isEmpty()) {
            throw new ErrorServicio("El id no puede ser nulo");
        } else {
            Optional<Autor> respuesta = ar.findById(id);

            if (respuesta.isPresent()) {

                Autor autor = respuesta.get();

                if (autor.getId().equals(id)) {

                    autor.setAlta(Boolean.TRUE);
                }
            } else {
                throw new ErrorServicio("No existe el autor solicitado");
            }

        }

    }
    

    @Transactional(readOnly = true)
    public Autor buscarAutorPorNombre(String nombre) {
        return ar.buscarAutorPorNombre(nombre);

    }

    public void validarAutor(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("Elnombre del autor no puede ser nulo");
        }
    }
}
