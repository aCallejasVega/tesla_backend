package bo.com.tesla.administracion.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import bo.com.tesla.recaudaciones.dao.ITransaccionCobroDao;
import bo.com.tesla.recaudaciones.dto.DeudasClienteRecaudacionDto;
import bo.com.tesla.administracion.dto.DeudasClienteAdmDto;
import bo.com.tesla.entidades.dto.DeudasClienteDto;

@Service
public class ReporteAdminService implements IReporteAdminService {
	
	@Autowired
	private ITransaccionCobroDao transaccionCobrosDao;

	
	

	@Override
	public Page<DeudasClienteAdmDto> findDeudasByParameter(
			Date fechaInicio, 
			Date fechaFin, 
			String entidadId,
			String recaudadorId, 
			String estado,
			int page,
			int size
			) {
		
		Pageable paging = PageRequest.of(page, size);
		return this.transaccionCobrosDao.findDeudasByParameterForAdmin(fechaInicio,	fechaFin,entidadId,	recaudadorId,estado,paging);

	}


		@Override
	public List<DeudasClienteAdmDto> findDeudasByParameterForReport(
			Date fechaInicio, 
			Date fechaFin, 
			String entidadId,
			String recaudadorId, 
			String estado) {
	
		return this.transaccionCobrosDao.findDeudasByParameterForReportAdmin(fechaInicio, fechaFin, entidadId, recaudadorId, estado);
	}

}
