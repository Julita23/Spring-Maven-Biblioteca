package com.ejercicio1.libreria.Controladores;

import com.ejercicio1.libreria.Errores.ErrorServicio;
import com.ejercicio1.libreria.Servicios.PrestamoServicio;
import com.ejercicio1.libreria.entidades.Prestamo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoServicio prestamoServicio;

    @GetMapping("/guardarPrestamo")
    public String guardarPrestamo() {

        return "guardarPrestamo.html";

    }

    @PostMapping("/guardarPrestamo")
    public String registrarPrestamo(ModelMap modelo, @RequestParam Long documento, @RequestParam String titulo, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDevolucion) {

        try {

            prestamoServicio.crearPrestamo(documento, titulo, fechaDevolucion);
            modelo.put("exito", "Registro Exitoso");

            return "guardarPrestamo.html";

        } catch (Exception e) {

            modelo.put("error", e.getMessage());
            modelo.put("documento",documento);
            modelo.put("titulo",titulo);
            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
            modelo.put("fechaDevolucion",sfd.format(fechaDevolucion));
            return "guardarPrestamo";
        }
    }

    @GetMapping("/modificarPrestamo/{id}")
    public String modificarPrestamo(@PathVariable String id, ModelMap modelo) {

        modelo.put("prestamo", prestamoServicio.buscarPorId(id));

        return "modificarPrestamo.html";
    }

    @PostMapping("/modificarPrestamo/{id}")
    public String modificarPrestamo(@PathVariable String id, ModelMap modelo, @RequestParam Date fechaPrestamo, @RequestParam Date fechaDevolucion, @RequestParam String Libro, @RequestParam String cliente) {

        try {

            prestamoServicio.modificarPrestamo(id, fechaPrestamo, fechaDevolucion, Libro, cliente);
            modelo.put("Exito", "Modificacion Exitosa");
            modelo.put("prestamo", prestamoServicio.buscarPorId(id));
            return "modificarPrestamo.html";

        } catch (Exception e) {

            modelo.put("Error", e.getMessage());
            modelo.put("prestamo", prestamoServicio.buscarPorId(id));
            return "modificarPrestamo.html";

        }
    }
    
    @GetMapping ("/listarPrestamos")
    
    public String listarPrestamos(ModelMap modelo) throws ErrorServicio{
        
        List<Prestamo> prestamosLista = prestamoServicio.cargarListaPrestamo();
        
        modelo.addAttribute("prestamos", prestamosLista);
        
        return "listarPrestamos.html";
        
    }
    
    @GetMapping ("/alta/{id}")
    public String darAltaPrestamo(@PathVariable String id){
        
        try{
            prestamoServicio.darAlta(id);
            return "redirect:/prestamo/listarPrestamos";
            
        }catch(Exception e){
            
            return "redirect:/";
        }
    }
    
    @GetMapping ("/baja/{id}")
    public String darBajaPrestamo(@PathVariable String id){
        
        try{
            prestamoServicio.darBaja(id);
            return "redirect:/prestamo/listarPrestamos";
            
        }catch(Exception e){
            
            return "redirect:/";
        }
    }
           

}
