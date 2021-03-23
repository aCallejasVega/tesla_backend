package bo.com.tesla.security.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bo.com.tesla.administracion.entity.SegPrivilegioEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.security.dto.OperacionesDto;
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
							
			response.put("data", privilegioList);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("This is error", e.getMessage());
			this.logger.error("This is cause", e.getCause());
			
			response.put("mensaje", "Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.");				
			response.put("data", privilegioList);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	

	
	@GetMapping(path = "/getOperaciones/{tabla}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getOperaciones( @PathVariable("tabla") String tabla, Authentication authentication) {
		Map<String, Object> response = new HashMap<>();
		List<OperacionesDto> operacionesList=new ArrayList<>();
		try {
			operacionesList=segPrivilegiosService.getOperaciones(authentication.getName(), tabla);
			if(operacionesList.isEmpty()) {
				response.put("data", operacionesList);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);					
			}
			response.put("data", operacionesList);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("This is error", e.getMessage());
			this.logger.error("This is cause", e.getCause());			
			response.put("mensaje", "Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
	}


	@GetMapping(path = { "/operaciones/{tabla}", "/operaciones/{tabla}/{estadoInicial}"},produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getOperacionesByEstadoInicial( @PathVariable("tabla") String tabla,
											 @PathVariable(name = "estadoInicial", required = false) Optional<String> estadoInicial,
											 Authentication authentication) {
		Map<String, Object> response = new HashMap<>();
		List<OperacionesDto> operacionesList=new ArrayList<>();
		try {
			if(estadoInicial.isPresent()) {
				operacionesList = segPrivilegiosService.getOperacionesByEstadoInicial(authentication.getName(), tabla, estadoInicial.get());
			} else {
				operacionesList=segPrivilegiosService.getOperaciones(authentication.getName(), tabla);
			}
			if(operacionesList.isEmpty()) {
				response.put("data", operacionesList);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
			}
			response.put("data", operacionesList);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);


		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("This is error", e.getMessage());
			this.logger.error("This is cause", e.getCause());
			response.put("mensaje", "Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}


	}


}
