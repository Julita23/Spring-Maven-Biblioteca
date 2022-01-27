package com.ejercicio1.libreria.Controladores;

import com.ejercicio1.libreria.Errores.ErrorServicio;
import com.ejercicio1.libreria.Servicios.AutorServicio;
import com.ejercicio1.libreria.entidades.Autor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/guardarAutor")
    public String guardarAutor() {

        return "guardarAutor.html";

    }
    
    @PostMapping("/guardarAutor")
    public String registro(ModelMap modelo, @RequestParam(required=false) String nombre){
        
        try{
             autorServicio.crearAutor(nombre);
             modelo.put("exito", "Registro Exitoso");
             return "guardarAutor.html";
             
        }catch(Exception e){
            
            modelo.put("error", e.getMessage());
            return "guardarAutor.html";
        }
        
        }
        
    @GetMapping("/listarAutores")
    public String listarAutores(ModelMap modelo) throws ErrorServicio{
        
        List<Autor>autores= autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);
        
        return "listarAutores.html";
        
    }
        
        
    

}
