package com.ejercicio1.libreria.Servicios;

import com.ejercicio1.libreria.Errores.ErrorServicio;
import com.ejercicio1.libreria.entidades.Editorial;
import com.ejercicio1.libreria.entidades.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {

    @Autowired
    EditorialRepositorio er;

    @Transactional
    public Editorial crearEditorial(String nombre) throws ErrorServicio {

        validarEditorial(nombre);
        Editorial editorial = er.buscarEditorialPorNombre(nombre);
        
        if(editorial != null){
            
            throw new ErrorServicio("La editorial ya existe");
        }
        
        Editorial e = new Editorial();
        e.setNombre(nombre);
        e.setAlta(Boolean.TRUE);
        return er.save(e);

    }

    @Transactional(readOnly = true)
    public Editorial buscarEditorialPorNombre(String nombre) {
        return er.buscarEditorialPorNombre(nombre);

    }

    @Transactional
    public Editorial modificarEditorial(String id, String nombre) throws ErrorServicio {

        validarEditorial(nombre);
        Editorial editorial = buscarEditorialPorNombre(nombre);
        editorial.setNombre(nombre);
        return er.save(editorial);

    }

    @Transactional
    public void darBajaEditorial(String id) throws ErrorServicio {

        if (id == null || id.isEmpty()) {

            throw new ErrorServicio("El id no puede ser nulo");
        } else {
            Optional<Editorial> respuesta = er.findById(id);

            if (respuesta.isPresent()) {

                Editorial editorial = respuesta.get();

                if (editorial.getId().equals(id)) {
                    editorial.setAlta(Boolean.FALSE);
                }
            } else {
                throw new ErrorServicio("No existe la editorial ingresada");
            }
        }
    }

    @Transactional
    public void darAltaEditorial(String id) throws ErrorServicio {

        if (id == null || id.isEmpty()) {

            throw new ErrorServicio("El id no puede ser nulo");
        } else {
            Optional<Editorial> respuesta = er.findById(id);

            if (respuesta.isPresent()) {

                Editorial editorial = respuesta.get();

                if (editorial.getId().equals(id)) {
                    editorial.setAlta(Boolean.TRUE);
                }
            } else {
                throw new ErrorServicio("No existe la editorial ingresada");
            }
        }
    }
    
    @Transactional(readOnly = true)
    public List<Editorial> listarEditoriales() throws ErrorServicio{
        
        return er.findAll();
    }

    public void validarEditorial(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la editorial no puede ser nulo");
        }
    }

}
