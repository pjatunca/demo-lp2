package com.ciberfarma.controller;

import java.io.OutputStream;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ciberfarma.model.Producto;
import com.ciberfarma.repository.ICategoriaRepository;
import com.ciberfarma.repository.IProductoRepository;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("/productos") // ruta
public class ProductoController {
	// llama al repositorio
	@Autowired
	private ICategoriaRepository repoCat; 
	
	@Autowired
	private IProductoRepository repoProd;
	
	@GetMapping("/pagreportes")
	public String cargarPagReportes(Model model) {
		model.addAttribute("producto", new Producto());
		model.addAttribute("lstCategorias", repoCat.findAll());
		return "reportes";
	}
	
	
	// abrir la página crud
	@GetMapping("/cargar") // endpoint
	public String cargarPag(Model model) {
		// enviar el listado de categoría atributo("nombre_atrib",valor)
		model.addAttribute("lstCategorias", repoCat.findAll());
		// enviar un listado de los Productos > Tabla
		model.addAttribute("lstProductos", repoProd.findAll());
		// envia un Producto vacío al formulario
		model.addAttribute("producto", new Producto());
		return "crudproductos";  // nombre del recurso 
	}
	
	// Seleccionar el Producto a Editar, mediante un href
	@GetMapping("/editar/{id_prod}")
	public String editarProducto(@PathVariable String id_prod, 
			Model model) {
		// obtener un Producto según el id
		Producto p = repoProd.findById(id_prod).get();
		// enviar el Producto al formulario (como atributo)
		model.addAttribute("producto", p);
		model.addAttribute("lstCategorias", repoCat.findAll());
		model.addAttribute("lstProductos", repoProd.findAll());
		return "crudproductos";
	}
	
	// Guardar los cambios!!
	@PostMapping("/grabar")
	public String registrarProducto(@ModelAttribute Producto producto, 
			Model model) {
		try {
			repoProd.save(producto);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/productos/cargar";
	}
	
	@Autowired
	private DataSource dataSource; // javax.sql

	@Autowired
	private ResourceLoader resourceLoader; // core.io

	@GetMapping("/reportes")
	public void reportes(HttpServletResponse response) {
	    // opción 1
	    // response.setHeader("Content-Disposition", "attachment; filename=\"reporte.pdf\";");
	    // opción 2
	    response.setHeader("Content-Disposition", "inline;");
	    
	    response.setContentType("application/pdf");
	    try {
	        String ru = resourceLoader.getResource("classpath:static/reporte01.jasper").getURI().getPath();
	        JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
	        OutputStream outStream = response.getOutputStream();
	        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@GetMapping("/graficos")
	public void reportesGraficos(HttpServletResponse response) {
	    // opción 1
	    // response.setHeader("Content-Disposition", "attachment; filename=\"reporte.pdf\";");
	    // opción 2
	    response.setHeader("Content-Disposition", "inline;");
	    
	    response.setContentType("application/pdf");
	    try {
	        String ru = resourceLoader.getResource("classpath:static/grafico01.jasper").getURI().getPath();
	        JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
	        OutputStream outStream = response.getOutputStream();
	        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
}
