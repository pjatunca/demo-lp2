package com.ciberfarma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ciberfarma.model.Usuario;
import com.ciberfarma.repository.IUsuarioRepository;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	private IUsuarioRepository repoUsu;
	
	
	@GetMapping("/login")
	public String abrirLogin() {
		return "login";
	}
	
	
	@PostMapping("/login")
	public String validarAcceso(@RequestParam("txtUsuario") String usuario, 
			@RequestParam("txtClave") String clave, Model model) {
		// obtener un Usuario según el usuario y clave, validando si existe
		Usuario u = repoUsu.findByCorreoAndClave(usuario, clave);
		if (u != null) { // Existe
			return "principal";
		} else {
			model.addAttribute("mensaje", "usuario o clave incorrecto");
			model.addAttribute("cssmensaje", "alert alert-danger");
			return "login";
		}
		
	}
	
	
}
