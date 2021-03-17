package bo.com.tesla.entidades.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bo.com.tesla.administracion.entity.EntidadEntity;
import bo.com.tesla.administracion.entity.RecaudadorEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.entidades.services.IEntidadService;
import bo.com.tesla.recaudaciones.dto.EstadoTablasDto;
import bo.com.tesla.recaudaciones.services.IRecaudadoraService;
import bo.com.tesla.security.services.ISegUsuarioService;


@RestController
@RequestMapping("api/entidades")
public class EntidadesController {
	
	@Autowired
	private IEntidadService entidadService;
	
	@Autowired
	private IRecaudadoraService recaudadoraService;
	
	@Autowired
	private ISegUsuarioService segUsuarioService;
	
	
	@GetMapping(path = "/findEntidadByRecaudacionId")
	public ResponseEntity<?> findEntidadByRecaudacionId(			
			Authentication authentication) throws Exception {

		Map<String, Object> response = new HashMap<>();
		List<EntidadEntity> entidadList=new ArrayList<>();
		
		try {
			
			SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
		
			RecaudadorEntity recaudador=this.recaudadoraService.findRecaudadorByUserId(usuario.getUsuarioId());
			
			entidadList=this.entidadService.findEntidadByRecaudacionId(recaudador.getRecaudadorId());
			if(entidadList.isEmpty()) {
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
			}
			
			response.put("data", entidadList);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

	}

}
