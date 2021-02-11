package bo.com.tesla.security.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bo.com.tesla.administracion.entity.SegPrivilegioEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.security.services.ISegPrivilegiosService;
import bo.com.tesla.security.services.ISegUsuarioService;

@RestController
@RequestMapping("api/Menu")
public class MenuController {
	private Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@Autowired
	private ISegUsuarioService segUsuarioService;
	
	@Autowired
	private ISegPrivilegiosService segPrivilegiosService;
	
	
	@GetMapping(path = "/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> processFile( Authentication authentication) {
		Map<String, Object> response = new HashMap<>();
		List<SegPrivilegioEntity> privilegioList=new ArrayList<>();
		try {
			SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
			
			privilegioList=	segPrivilegiosService.getMenuByUserId(usuario.getUsuarioId());
			if(privilegioList.isEmpty()) {
				response.put("mensaje", "No se encontraron ningún privilegio para el usuario: "+ usuario.getLogin() + ".");				
				response.put("status", false);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
			}
			response.put("mensaje", "No se encontraron ningún privilegio para el usuario: "+ usuario.getLogin() + ".");				
			response.put("data", privilegioList);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			
			
		} catch (Exception e) {
			this.logger.error("This is error", e.getMessage());
			this.logger.error("This is cause", e.getCause());
			
			response.put("mensaje", "Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.");				
			response.put("data", privilegioList);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
	}
}
