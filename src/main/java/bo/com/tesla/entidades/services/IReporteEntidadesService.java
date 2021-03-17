package bo.com.tesla.entidades.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import bo.com.tesla.entidades.dto.DeudasClienteDto;

public interface IReporteEntidadesService {

	public List<DeudasClienteDto> findDeudasPagadasByParameter(Long entidadId, String recaudadorId, String estado,
			Date fechaIni, Date fechaFin);

	public Page<DeudasClienteDto> findDeudasByParameter(Date fechaInicio, Date fechaFin, Long entidadId,
			String recaudadorId, String estado,int page,int size);
	
	public List<DeudasClienteDto>  findDeudasByParameterForReport(
			 Date fechaInicio, 
			 Date fechaFin,
			 Long entidadId,
			 String recaudadorId,
			 String estado			
			);

}
