package com.ejercicio1.libreria.Servicios;

import com.ejercicio1.libreria.Errores.ErrorServicio;
import com.ejercicio1.libreria.entidades.Autor;
import com.ejercicio1.libreria.entidades.Editorial;
import com.ejercicio1.libreria.entidades.Libro;
import com.ejercicio1.libreria.entidades.repositorios.LibroRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {
    
    @Autowired
    LibroRepositorio lr;
    
    @Autowired
    AutorServicio as;
    
    @Autowired
    EditorialServicio es;
    
    @Transactional
    public Libro crearLibro(String titulo, Long isbn, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String autor, String editorial) throws ErrorServicio {
        
        Libro libro = new Libro();
        
        validarLibro(titulo, anio, ejemplares);
        
        libro.setTitulo(titulo);
        libro.setIsbn(isbn);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);
        
        Autor a = as.buscarAutorPorNombre(autor);
        
        if (a == null) {
            a = as.crearAutor(autor);
            libro.setAutor(a);
        } else {
            libro.setAutor(a);
        }
        
        Editorial e = es.buscarEditorialPorNombre(editorial);
        
        if (e == null) {
            e = es.crearEditorial(editorial);
            libro.setEditorial(e);
        } else {
            libro.setEditorial(e);
        }
        
        libro.setAlta(Boolean.TRUE);
        return lr.save(libro);
        
    }
    
    @Transactional(readOnly = true)
    public Libro buscarLibroPorId(String id) {
        
        return lr.buscarLibroPorId(id);
    }
    
    @Transactional
    public void darBajaLibro(String id) throws ErrorServicio {
        
        Libro l = buscarLibroPorId(id);
        
        if (l != null) {
            
            l.setAlta(Boolean.FALSE);
            lr.save(l);
            
        } else {
            throw new ErrorServicio("El libro ingresado no fue encontrado");
        }
    }
    
    @Transactional
    public void darAltaLibro(String id) throws ErrorServicio {
        Libro l = buscarLibroPorId(id);
        
        if (l != null) {
            
            l.setAlta(Boolean.TRUE);
            lr.save(l);
            
        } else {
            throw new ErrorServicio("El libro ingresado no fue encontrado");
        }
        
    }
    
    @Transactional
    public Libro modificarLibro(String id, String titulo, Long isbn, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String autor, String editorial) throws ErrorServicio {
       
        Libro l = buscarLibroPorId(id);
        
        if (l != null) {
            
            validarLibro(titulo, anio, ejemplares);
            l.setTitulo(titulo);
            l.setIsbn(isbn);
            l.setAnio(anio);
            l.setEjemplares(ejemplares);
            l.setEjemplaresPrestados(ejemplaresPrestados);
            l.setEjemplaresRestantes(ejemplaresRestantes);
            
            Autor a = as.buscarAutorPorNombre(autor);
            
            if (a == null) {
                a = as.crearAutor(autor);
                l.setAutor(a);
            } else {
                l.setAutor(a);
            }
            
            Editorial e = es.buscarEditorialPorNombre(editorial);
            
            if (e == null) {
                e = es.crearEditorial(editorial);
                l.setEditorial(e);
            } else {
                l.setEditorial(e);
            }
            l.setAlta(Boolean.TRUE);
            return lr.save(l);
        } else {
            throw new ErrorServicio("El libro ingresado no ha sido encontrado");
        }
    }
    
    @Transactional(readOnly = true)
    public List<Libro> cargarListaLibros() throws ErrorServicio {
        return lr.findAll();
        
    }
    
    @Transactional(readOnly = true)
    public Libro buscarLibroPorTitulo(String titulo) throws ErrorServicio{
        
        return lr.buscarLibroPorTitulo(titulo);
    }
    
    @Transactional(readOnly = true)
    public List<Libro> buscarLibrosPorAutor(String autor) throws ErrorServicio {
        
        return lr.buscarLibrosPorAutor(autor);
    }
    
    @Transactional(readOnly = true)
    public List<Libro> buscarLibrosPorEditorial(String editorial) throws ErrorServicio {
        
        return lr.buscarLibrosPorAutor(editorial);
    }
    
    public void validarLibro(String titulo, Integer anio, Integer ejemplares) throws ErrorServicio {
        
        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El titulo del libro no puede ser nulo");
        }
        
        if (anio == null || anio < 0) {
            throw new ErrorServicio("El anio no puede ser nulo o menor que 0");
        }
        
        if (ejemplares == null || ejemplares <= 0) {
            throw new ErrorServicio("Tiene que existir al menos 1 ejemplar, intente nuevamente");
        }
    }
    
}
