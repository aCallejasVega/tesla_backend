package bo.com.tesla.entidades.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import bo.com.tesla.entidades.dao.IArchivoDao;
import bo.com.tesla.entidades.dto.ArchivoDto;
import bo.com.tesla.entidades.dto.DeudasClienteDto;
import bo.com.tesla.recaudaciones.dao.IHistoricoDeudaDao;
import bo.com.tesla.recaudaciones.dao.ITransaccionCobroDao;


@Service
public class ReporteEntidadesService implements IReporteEntidadesService {
	@Autowired
	private ITransaccionCobroDao transaccionCobrosDao;
	@Autowired
	private IArchivoDao archivoDao;
	
	@Autowired
	private IHistoricoDeudaDao  iHistoricoDeudaDao;
	

	@Override
	public List<DeudasClienteDto> findDeudasPagadasByParameter(Long entidadId, String recaudadorId, String estado,
			Date fechaIni, Date fechaFin) {

		return this.transaccionCobrosDao.findDeudasPagadasByParameter(entidadId, recaudadorId, estado, fechaIni,
				fechaFin);

	}
	

	@Override
	public Page<DeudasClienteDto> findDeudasByParameter(Date fechaInicio, Date fechaFin, Long entidadId,
			List<String> recaudadorId, List<String> estado,int page,int size) {
		
		Pageable paging = PageRequest.of(page, size);
		return this.transaccionCobrosDao.findDeudasByParameterForEntidad(
				fechaInicio, 
				fechaFin, 
				entidadId, 
				recaudadorId,
				estado,
				paging);

	}


	@Override
	public List<DeudasClienteDto> findDeudasByParameterForReport(Date fechaInicio, Date fechaFin, Long entidadId,
			List<String> recaudadorId, List<String> estado) {
		return this.transaccionCobrosDao.findDeudasByParameterForReportEntidades(fechaInicio, fechaFin, entidadId, recaudadorId, estado);
				
	}
	
	
	@Override
	public Page<ArchivoDto> findByEntidadIdAndFechaIniAndFechaFin(Long entidadId, Date fechaIni, Date fechaFin,String estado,
			int page,int size) {
		Page<DeudasClienteDto> deudaClienteList ;
		Pageable paging = PageRequest.of(page, size);
		return this.archivoDao.findByEntidadIdAndFechaIniAndFechaFinForEntidades(entidadId, fechaIni, fechaFin,estado, paging);
	}
	
	@Override
	public List<DeudasClienteDto> findDeudasByArchivoIdAndEstado(Long archivoId) {

		return this.iHistoricoDeudaDao.findDeudasByArchivoIdAndEstadoForEntidad(archivoId);
	}

	

}
