package bo.com.tesla.entidades.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bo.com.tesla.administracion.entity.ArchivoEntity;
import bo.com.tesla.administracion.entity.EntidadEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.entidades.dao.IArchivoDao;
import bo.com.tesla.entidades.dao.IDeudaClienteDao;
import bo.com.tesla.entidades.dao.IEntidadDao;
import bo.com.tesla.security.dao.ISegUsuarioDao;
import bo.com.tesla.useful.cross.HandlingFiles;

@Service

public class ArchivoService implements IArchivoService{
	private Logger logger = LoggerFactory.getLogger(ArchivoService.class);
	
	@Autowired
	private IArchivoDao archivoDao;
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	private IDeudaClienteDao deudaClienteDao;
	
	@Autowired
	private ISegUsuarioDao usuarioDao;
	
	@Autowired
	private IEntidadDao entidadDao;
	
	@Value("${tesla.path.files-debts}")
	private String filesBets;

	@Autowired
	@Qualifier("deudaClienteJob")
	Job job;

	@Override
	public ArchivoEntity save(ArchivoEntity entity) {
		return this.archivoDao.save(entity);		
	}

	@Override
	public ArchivoEntity findById(Long archivoId) {	
		return this.archivoDao.findById(archivoId).get();
	}
	
	
	@Override
	public Map<String, Object>  upload(MultipartFile file, String login) {
		Map<String, Object> response = new HashMap<>();
		ArchivoEntity archivo = new ArchivoEntity();
		String path = null;
		try {
			SegUsuarioEntity usuario = this.usuarioDao.findByLogin(login);
			EntidadEntity entidad = this.entidadDao.findEntidadByUserId(usuario.getUsuarioId());
			path = HandlingFiles.saveFileToDisc(file, entidad.getNombre(), filesBets);
			archivo.setEntidadId(entidad);
			archivo.setNombre(file.getOriginalFilename());
			archivo.setPath(path);
			archivo.setUsuarioCreacion(usuario.getUsuarioId());
			archivo.setFechaCreacion(new Date());
			archivo.setTransaccion("CREAR");
			archivo = this.archivoDao.save(archivo);
			response.put("mensaje", "El archivo fue registrado con exito");
			response.put("archivo", archivo);
			response.put("status", true);
			return response;

		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("This is error", e.getMessage());
			this.logger.error("This is cause", e.getCause());
			response.put("mensaje",
					"Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
			response.put("status", false);
			return response;
		}
		
	}
	
	

	
	@Override
	public Map<String, Object>  process(Long archivoId,String login) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			ArchivoEntity archivo = this.archivoDao.findById(archivoId).get();
			ArchivoEntity archivoPrevious = this.archivoDao.findByEstado("ACTIVO",archivo.getEntidadId().getEntidadId());
			
			JobParameters jobParameters = new JobParametersBuilder().addString("pathToFile", archivo.getPath() )
					.addLong("archivoId",archivo.getArchivoId()).addLong("time", System.currentTimeMillis())
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, jobParameters);
			if (execution.getStatus() != BatchStatus.COMPLETED) {
				this.deudaClienteDao.deletByArchivoId(archivo.getArchivoId());
				List<Throwable> throwList = execution.getAllFailureExceptions();
				for (Throwable throwable : throwList) {
					throwable.printStackTrace();
					response.put("mensaje", throwable.getMessage() + "");
					response.put("causa", throwable.getCause() + "");
					return response;
				}
			}			
			
			SegUsuarioEntity usuario = this.usuarioDao.findByLogin(login);			
			archivo.setFechaModificacion(new Date());
			archivo.setUsuarioModificacion(usuario.getUsuarioId());
			archivo.setTransaccion("PROCESAR");
			archivo=this.archivoDao.save(archivo);
			
			if(archivoPrevious !=null) {
				this.deudaClienteDao.deletByArchivoId(archivoPrevious.getArchivoId());	
				
				archivoPrevious.setFechaModificacion(new Date());
				archivoPrevious.setUsuarioModificacion(usuario.getUsuarioId());
				archivoPrevious.setTransaccion("DESACTIVAR");
				this.archivoDao.save(archivoPrevious);
			}
			
			
			
			
			response.put("mensaje", "El archivo fue procesado con éxito ");
			response.put("archivo", archivo);
			response.put("estado", true);
			return response;
			
		} catch (DataAccessException e) {
			this.deudaClienteDao.deletByArchivoId(archivoId);
			this.logger.error("This is error", e.getMessage());
			this.logger.error("This is cause", e.getCause());
			this.logger.error("This is mesage", e.getMostSpecificCause().getMessage());
			response.put("mensaje",
					"Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
			response.put("estado", false);
			return response;
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			this.deudaClienteDao.deletByArchivoId(archivoId);
			this.logger.error("This is error", e.getMessage());
			this.logger.error("This is cause", e.getCause());
			response.put("mensaje",
					"Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
			response.put("estado", false);
			return response;
		}
		
	}

	

}
