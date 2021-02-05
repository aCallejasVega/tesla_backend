package bo.com.tesla.entidades.controller;

import java.util.ArrayList;
import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bo.com.tesla.administracion.entity.ArchivoEntity;
import bo.com.tesla.administracion.entity.EntidadEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.entidades.dao.IDeudaClienteDao;
import bo.com.tesla.entidades.dto.ArchivoDto;
import bo.com.tesla.entidades.dto.DeudasClienteDto;
import bo.com.tesla.entidades.services.IArchivoService;
import bo.com.tesla.entidades.services.IDeudaClienteService;
import bo.com.tesla.entidades.services.IEntidadService;
import bo.com.tesla.security.services.ISegUsuarioService;
import bo.com.tesla.useful.cross.HandlingFiles;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/deudaCliente")
public class DeudaClienteController {
	private Logger logger = LoggerFactory.getLogger(DeudaClienteController.class);

	@Autowired
	private IArchivoService archivoService;

	@Autowired
	private IDeudaClienteService deudaClienteService;

	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(path = "/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, Authentication authentication)
			throws Exception {

		Map<String, Object> response = new HashMap<>();
		ArchivoEntity archivo = new ArchivoEntity();

		if (file.isEmpty()) {
			response.put("Error ", "El archivo enviado está vacío o no se encuentra en el formato especificado.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response = this.archivoService.upload(file, authentication.getName());

		if (response.get("status").equals(false)) {
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
	}

	@PostMapping(path = "/processFile/{archivoId}")
	public ResponseEntity<?> processFile(@PathVariable("archivoId") Long archivoId, Authentication authentication) {
		Map<String, Object> response = new HashMap<>();

		if (archivoId <= 0 || archivoId.toString().isBlank() || archivoId.toString().isEmpty()) {
			response.put("mensaje", "Ocurrió un error en el servidor, por favor verifique los parametros de ingreso.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response = this.archivoService.process(archivoId, authentication.getName());
		if (response.get("estado").equals(false)) {
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

	}

	@GetMapping(path = { "/findDeudasClientesByArchivoId/{archivoId}/{paramBusqueda}",
			"/findDeudasClientesByArchivoId/{archivoId}/" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findDeudasClientesByArchivoId(@PathVariable("archivoId") Long archivoId,
			@PathVariable(name = "paramBusqueda", required = false) Optional<String> paramBusqueda,
			Authentication authentication) {
		System.out.println("----------------findDeudasClientesByArchivoId----------------------");
		Map<String, Object> response = new HashMap<>();
		List<DeudasClienteDto> deudasClienteList = new ArrayList<>();
		String newParamBusqueda = "";

		if (archivoId <= 0 || archivoId.toString().isBlank() || archivoId.toString().isEmpty()) {
			response.put("mensaje", "Ocurrió un error en el servidor, por favor verifique los parametros de ingreso.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		if (paramBusqueda.isPresent()) {
			newParamBusqueda = paramBusqueda.get();
		} else {
			newParamBusqueda = "";
		}

		try {
			deudasClienteList = this.deudaClienteService.findDeudasClientesByArchivoId(archivoId, newParamBusqueda);
			response.put("status", "true");
			response.put("data", deudasClienteList);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			this.logger.error("This is error", e.getMessage());
			this.logger.error("This is cause", e.getCause());
			this.logger.error("This is mesage", e.getMostSpecificCause().getMessage());
			response.put("mensaje",
					"Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

	}

}
