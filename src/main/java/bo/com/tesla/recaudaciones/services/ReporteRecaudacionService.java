package bo.com.tesla.recaudaciones.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import bo.com.tesla.recaudaciones.dao.ITransaccionCobroDao;
import bo.com.tesla.recaudaciones.dto.DeudasClienteRecaudacionDto;
import bo.com.tesla.entidades.dto.DeudasClienteDto;

@Service
public class ReporteRecaudacionService implements IReporteRecaudacionService {
	
	@Autowired
	private ITransaccionCobroDao transaccionCobrosDao;

	
	

	@Override
	public Page<DeudasClienteRecaudacionDto> findDeudasByParameter(
			Date fechaInicio, 
			Date fechaFin, 
			String entidadId,
			Long recaudadorId, 
			String estado,
			int page,
			int size
			) {
		
		Pageable paging = PageRequest.of(page, size);
		return this.transaccionCobrosDao.findDeudasByParameter(fechaInicio,	fechaFin,entidadId,	recaudadorId,estado,paging);

	}


		@Override
	public List<DeudasClienteRecaudacionDto> findDeudasByParameterForReport(
			Date fechaInicio, 
			Date fechaFin, 
			String entidadId,
			Long recaudadorId, 
			String estado) {
	
		return this.transaccionCobrosDao.findDeudasByParameterForReport(fechaInicio, fechaFin, entidadId, recaudadorId, estado);
	}

}
