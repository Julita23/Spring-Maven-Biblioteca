package com.ejercicio1.libreria.Controladores;

import com.ejercicio1.libreria.Errores.ErrorServicio;
import com.ejercicio1.libreria.Servicios.LibroServicio;
import com.ejercicio1.libreria.entidades.Libro;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroServicio LibroServicio;

    @GetMapping("/guardarLibro")
    public String guardarLibro() {

        return "guardarLibro.html";
    }

    @PostMapping("/guardarLibro")
    public String registro(ModelMap modelo, @RequestParam(required = false) String titulo, @RequestParam(required = false) Long isbn, @RequestParam(required = false) Integer anio, @RequestParam(required = false) Integer ejemplares, @RequestParam(required = false) Integer ejemplaresPrestados, @RequestParam(required = false) Integer ejemplaresRestantes, @RequestParam(required = false) String Autor, @RequestParam(required = false) String Editorial) {

        try {
            LibroServicio.crearLibro(titulo, isbn, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, Autor, Editorial);
            modelo.put("exito", "Registro Exitoso");
            return "guardarLibro";

        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "guardarLibro.html";
        }

    }

    @GetMapping("/modificarLibro/{id}")
    public String modificarLibro(@PathVariable String id, ModelMap modelo) {

        modelo.put("libro", LibroServicio.buscarLibroPorId(id));
        return "modificarLibro.html";
    }

    @PostMapping("/modificarLibro/{id}")
    public String modificarLibro(@PathVariable String id, ModelMap modelo, @RequestParam String titulo, @RequestParam Long isbn, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam Integer ejemplaresRestantes, @RequestParam String Autor, @RequestParam String Editorial) {
        try {
            
            LibroServicio.modificarLibro(id, titulo, isbn, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, Autor, Editorial);
            modelo.put("exito", "Modificación Exitosa");
            modelo.put("libro", LibroServicio.buscarLibroPorId(id));
            return "modificarLibro.html";
            
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            modelo.put("libro", LibroServicio.buscarLibroPorId(id));
            return "modificarLibro.html";
        }

    }

    @GetMapping("/buscarLibro")
    public String buscarLibro(ModelMap modelo) throws ErrorServicio {

        List<Libro> listaLibros = LibroServicio.cargarListaLibros();
        modelo.addAttribute("libros", listaLibros);
        return "buscarLibro.html";
    }

    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) {
        try {
            LibroServicio.darAltaLibro(id);
            return "redirect:/libro/buscarLibro";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {
        try {
            LibroServicio.darBajaLibro(id);
            return "redirect:/libro/buscarLibro";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

}
