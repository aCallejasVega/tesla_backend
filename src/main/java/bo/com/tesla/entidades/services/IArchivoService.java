package bo.com.tesla.entidades.services;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import bo.com.tesla.administracion.entity.ArchivoEntity;
import bo.com.tesla.entidades.dto.ArchivoDto;

public interface IArchivoService {

	public ArchivoEntity save(ArchivoEntity entity);

	public ArchivoEntity findById(Long archivoId);
	
	public ArchivoEntity findByEstado(String estado,Long archivoId);

	public Page<ArchivoDto> findByEntidadIdAndFechaIniAndFechaFin(Long entidadId, Date fechaIni, Date fechaFin,String estado,
			int page,int size);

}
