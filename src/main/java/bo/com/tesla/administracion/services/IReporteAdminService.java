package bo.com.tesla.administracion.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import bo.com.tesla.administracion.dto.DeudasClienteAdmDto;
import bo.com.tesla.entidades.dto.DeudasClienteDto;
import bo.com.tesla.recaudaciones.dto.DeudasClienteRecaudacionDto;

public interface IReporteAdminService {



	public Page<DeudasClienteAdmDto> findDeudasByParameter(
			Date fechaInicio, 
			Date fechaFin, 
			String entidadId,
			String recaudadorId, 
			List<String> estado,
			int page,
			int size
			);
	
	public List<DeudasClienteAdmDto>  findDeudasByParameterForReport(
			 Date fechaInicio, 
			 Date fechaFin,
			 String entidadId,
			 String recaudadorId,
			 List<String> estado			
			);

}
