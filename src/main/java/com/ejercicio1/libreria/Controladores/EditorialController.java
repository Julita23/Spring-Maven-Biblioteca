package com.ejercicio1.libreria.Controladores;

import com.ejercicio1.libreria.Errores.ErrorServicio;
import com.ejercicio1.libreria.Servicios.EditorialServicio;
import com.ejercicio1.libreria.entidades.Editorial;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialController {

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/guardarEditorial")
    public String guardarEditorial() {

        return "guardarEditorial.html";

    }

    @PostMapping("/guardarEditorial")
    public String registro(ModelMap modelo, @RequestParam(required = false) String nombre) {

        try {
            editorialServicio.buscarEditorialPorNombre(nombre);
            modelo.put("exito", "Registro Exitoso");
            return "guardarEditorial.html";

        } catch (Exception e) {

            modelo.put("error", e.getMessage());
            return "guardarEditorial.html";
        }

    }

    @GetMapping("/listarEditoriales")
    public String listarAutores(ModelMap modelo) throws ErrorServicio {

        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("Editoriales", "editoriales");

        return "listarEditoriales.html";

    }

}
