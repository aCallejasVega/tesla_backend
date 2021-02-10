package bo.com.tesla.entidades.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bo.com.tesla.administracion.entity.ArchivoEntity;
import bo.com.tesla.entidades.dao.IArchivoDao;
import bo.com.tesla.entidades.dao.IDeudaClienteDao;
import bo.com.tesla.entidades.dao.IEntidadDao;
import bo.com.tesla.security.dao.ISegUsuarioDao;

@Service

public class ArchivoService implements IArchivoService{
	
	
	@Autowired
	private IArchivoDao archivoDao;
	
	
	@Transactional
	@Override
	public ArchivoEntity save(ArchivoEntity entity) {
		return this.archivoDao.save(entity);		
	}

	@Transactional(readOnly = true)
	@Override
	public ArchivoEntity findById(Long archivoId) {	
		return this.archivoDao.findById(archivoId).get();
	}
	
	@Transactional(readOnly = true)
	@Override
	public ArchivoEntity findByEstado(String estado, Long archivoId) {
		return this.archivoDao.findByEstado(estado, archivoId);
		
	}

	
	
	

	
	

}
