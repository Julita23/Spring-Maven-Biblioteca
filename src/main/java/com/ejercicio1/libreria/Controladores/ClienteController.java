package com.ejercicio1.libreria.Controladores;

import com.ejercicio1.libreria.Errores.ErrorServicio;
import com.ejercicio1.libreria.Servicios.ClienteServicio;
import com.ejercicio1.libreria.entidades.Cliente;
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
@RequestMapping("/cliente")

public class ClienteController {

    @Autowired
    private ClienteServicio cs;

    @GetMapping("/guardarCliente")
    public String guardarCliente() {

        return "guardarCliente.html";
    }

    @PostMapping("/guardarCliente")
    public String registroCliente(ModelMap modelo, @RequestParam Long documento, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono) {

        try {
            cs.crearCliente(documento, nombre, apellido, telefono);
            modelo.put("exito", "Registro exitoso");

            return "guardarCliente.html";
        } catch (Exception e) {

            modelo.put("error", e.getMessage());

            return "guardarCliente.html";
        }
    }
    
    @GetMapping("/modificarCliente/{id}")
    public String modificarCliente(@PathVariable String id, ModelMap modelo){
        
        modelo.put("cliente", cs.buscarPorId(id));

        return "modificarCliente.html";
    }
    
    @PostMapping("/modificarCliente/{id}")
    public String modificarCliente(@PathVariable String id, ModelMap modelo, @RequestParam String apellido, @RequestParam String nombre, @RequestParam Long documento, @RequestParam String telefono){
        
        try{
            cs.modificarCliente(id, documento, nombre, apellido, telefono);
            modelo.put("exito", "Modificacion Exitosa");
            modelo.put("cliente", cs.buscarPorId(id));
            return "redirect:/cliente/listarClientes";
                    
        }catch (Exception e){
            
            modelo.put("error", e.getMessage());
            modelo.put("cliente", cs.buscarPorId(id));
            return "modificarCliente.html";
        }
    }
    
    @GetMapping("/listarClientes")
    public String listarClientes(ModelMap modelo) throws ErrorServicio{
        
        List<Cliente> clientesLista = cs.listarClientes();
        
        modelo.addAttribute("clientes", clientesLista);
        
        return "listarClientes.html";
        
    }
    
    @GetMapping("/alta/{id}")
    public String darAlta(@PathVariable String id){
        
        try{
            
            cs.darDeAltaCliente(id);
            return "redirect:/cliente/listarClientes";
            
        }catch(Exception e){
            
            return "redirect:/";
        }
    }
    
    @GetMapping("/baja/{id}")
    public String darBaja(@PathVariable String id){
        
        try{
            
            cs.darDeBajaCliente(id);
            return "redirect:/cliente/listarClientes";
            
        }catch(Exception e){
            
            return "redirect:/";
        }
    }
    
    //revisar html para hacer referencias, crear controlador prestamo, revisar html 

}
