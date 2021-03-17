package bo.com.tesla.entidades.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import bo.com.tesla.entidades.dao.ITransaccionCobrosDao;
import bo.com.tesla.entidades.dto.DeudasClienteDto;

@Service
public class ReporteEntidadesService implements IReporteEntidadesService {
	@Autowired
	private ITransaccionCobrosDao transaccionCobrosDao;

	@Override
	public List<DeudasClienteDto> findDeudasPagadasByParameter(Long entidadId, String recaudadorId, String estado,
			Date fechaIni, Date fechaFin) {

		return this.transaccionCobrosDao.findDeudasPagadasByParameter(entidadId, recaudadorId, estado, fechaIni,
				fechaFin);

	}
	

	@Override
	public Page<DeudasClienteDto> findDeudasByParameter(Date fechaInicio, Date fechaFin, Long entidadId,
			String recaudadorId, String estado,int page,int size) {
		
		Pageable paging = PageRequest.of(page, size);
		return this.transaccionCobrosDao.findDeudasByParameter(
				fechaInicio, 
				fechaFin, 
				entidadId, 
				recaudadorId,
				estado,
				paging);

	}


	@Override
	public List<DeudasClienteDto> findDeudasByParameterForReport(Date fechaInicio, Date fechaFin, Long entidadId,
			String recaudadorId, String estado) {
		return this.transaccionCobrosDao.findDeudasByParameterForReport(fechaInicio, fechaFin, entidadId, recaudadorId, estado);
				
	}

}
