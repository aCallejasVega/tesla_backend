package bo.com.tesla.entidades.controller;

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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bo.com.tesla.administracion.entity.ArchivoEntity;
import bo.com.tesla.administracion.entity.EntidadEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.entidades.dto.DeudasClienteDto;
import bo.com.tesla.entidades.services.IArchivoService;
import bo.com.tesla.entidades.services.IDeudaClienteService;
import bo.com.tesla.entidades.services.IEntidadService;
import bo.com.tesla.security.services.ISegUsuarioService;
import bo.com.tesla.useful.cross.HandlingFiles;
import bo.com.tesla.useful.cross.Util;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/deudaCliente")
public class DeudaClienteController {
	private Logger logger = LoggerFactory.getLogger(DeudaClienteController.class);

	@Autowired
	private IArchivoService archivoService;

	@Autowired
	private IDeudaClienteService deudaClienteService;

	@Autowired
	private ISegUsuarioService segUsuarioService;

	@Autowired
	private IEntidadService entidadService;

	@Value("${tesla.path.files-debts}")
	private String filesBets;

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	@Qualifier("deudaClienteJob")
	Job job;

	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(path = "/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, Authentication authentication)
			throws Exception {

		Map<String, Object> response = new HashMap<>();
		ArchivoEntity archivo = new ArchivoEntity();
		String path = null;
		try {
			SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
			EntidadEntity entidad = this.entidadService.findEntidadByUserId(usuario.getUsuarioId());
			path = HandlingFiles.saveFileToDisc(file, entidad.getNombre(), filesBets);

			Long cantidadRegistros = Util.fileDataValidate(path);

			archivo.setEntidadId(entidad);
			archivo.setNombre(file.getOriginalFilename());
			archivo.setPath(path);
			archivo.setUsuarioCreacion(usuario.getUsuarioId());
			archivo.setFechaCreacion(new Date());
			archivo.setTransaccion("CREAR");
			archivo = this.archivoService.save(archivo);
			response.put("mensaje", "El archivo fue recepcionado con éxito, los registros encontrados en este archivo fueron "
					+ cantidadRegistros + ".");
			response.put("archivo", archivo);
			response.put("status", true);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("This is error", e.getMessage());
			this.logger.error("This is cause", e.getCause());
			response.put("mensaje", e.getMessage()+"");
			response.put("status", false);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/processFile/{archivoId}")
	public ResponseEntity<?> processFile(@PathVariable("archivoId") Long archivoId, Authentication authentication) {
		Map<String, Object> response = new HashMap<>();

		if (archivoId <= 0 || archivoId.toString().isBlank() || archivoId.toString().isEmpty()) {
			response.put("mensaje", "Ocurrió un error en el servidor, por favor verifique los parametros de ingreso.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			ArchivoEntity archivo = this.archivoService.findById(archivoId);
			ArchivoEntity archivoPrevious = this.archivoService.findByEstado("ACTIVO",
					archivo.getEntidadId().getEntidadId());

			JobParameters jobParameters = new JobParametersBuilder().addString("pathToFile", archivo.getPath())
					.addLong("archivoId", archivo.getArchivoId()).addLong("time", System.currentTimeMillis())
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, jobParameters);
			if (execution.getStatus() != BatchStatus.COMPLETED) {
				this.deudaClienteService.deletByArchivoId(archivo.getArchivoId());
				List<Throwable> throwList = execution.getAllFailureExceptions();
				for (Throwable throwable : throwList) {
					throwable.printStackTrace();
					response.put("mensaje", Util.mensajeRow(throwable.getMessage() + "") + "");
					response.put("detalle", Util.causeRow(throwable.getCause() + "") + "");
					response.put("causa", "A");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

				}
			}

			SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
			archivo.setFechaModificacion(new Date());
			archivo.setUsuarioModificacion(usuario.getUsuarioId());
			archivo.setTransaccion("PROCESAR");
			archivo = this.archivoService.save(archivo);

			if (archivoPrevious != null) {
				this.deudaClienteService.deletByArchivoId(archivoPrevious.getArchivoId());

				archivoPrevious.setFechaModificacion(new Date());
				archivoPrevious.setUsuarioModificacion(usuario.getUsuarioId());
				archivoPrevious.setTransaccion("DESACTIVAR");
				this.archivoService.save(archivoPrevious);
				this.deudaClienteService.deletByArchivoId(archivoPrevious.getArchivoId());
			}

			response.put("mensaje", "El archivo fue procesado con éxito ");
			response.put("archivo", archivo);
			response.put("estado", true);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (DataAccessException e) {
			this.deudaClienteService.deletByArchivoId(archivoId);
			this.logger.error("This is error", e.getMessage());
			this.logger.error("This is cause", e.getCause());
			this.logger.error("This is mesage", e.getMostSpecificCause().getMessage());
			response.put("mensaje",
					"Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
			response.put("estado", false);
			response.put("causa", "O");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			this.deudaClienteService.deletByArchivoId(archivoId);
			this.logger.error("This is error", e.getMessage());
			this.logger.error("This is cause", e.getCause());
			response.put("mensaje",
					"Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
			response.put("estado", false);
			response.put("causa", "O");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

		}
	}

	@GetMapping(path = { "/findDeudasClientesByArchivoId/{archivoId}/{paginacion}",
			"/findDeudasClientesByArchivoId/{archivoId}/{paginacion}/{paramBusqueda}", }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findDeudasClientesByArchivoId(@PathVariable("archivoId") Long archivoId,
			@PathVariable("paginacion") int paginacion,
			@PathVariable(name = "paramBusqueda", required = false) Optional<String> paramBusqueda,

			Authentication authentication) {
		Map<String, Object> response = new HashMap<>();
		Page<DeudasClienteDto> deudasClienteList;// = new ArrayList<>();
		String newParamBusqueda = "";

		if (archivoId <= 0 || archivoId.toString().isBlank() || archivoId.toString().isEmpty()) {
			response.put("mensaje", "Ocurrió un error en el servidor, por favor verifique los parametros de ingreso.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		if (paramBusqueda.isPresent()) {
			newParamBusqueda = paramBusqueda.get();
			paginacion = 1;
		} else {
			newParamBusqueda = "";
		}

		try {
			deudasClienteList = this.deudaClienteService.findDeudasClientesByArchivoId(archivoId, newParamBusqueda,
					paginacion - 1, 5);
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
