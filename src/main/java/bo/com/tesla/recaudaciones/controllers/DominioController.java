package bo.com.tesla.recaudaciones.controllers;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bo.com.tesla.administracion.entity.DominioEntity;
import bo.com.tesla.entidades.dto.BusquedaReportesDto;
import bo.com.tesla.recaudaciones.dao.IDominioDao;
import bo.com.tesla.recaudaciones.services.IDominioService;

@RestController
@RequestMapping("api/dominio")
public class DominioController {
	
	@Autowired
	private IDominioService dominioService;
	
	@GetMapping(path = "/findByDominio/{dominio}")
	public ResponseEntity<?> findByDominio( @PathVariable("dominio") String dominio,
			Authentication authentication) throws Exception {

		Map<String, Object> response = new HashMap<>();
		List<DominioEntity> dominioList=new ArrayList<>();
		
		try {
			dominioList=this.dominioService.findByDominio(dominio);
			if(dominioList.isEmpty()) {
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
			}
			
			response.put("data", dominioList);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

	}
}
