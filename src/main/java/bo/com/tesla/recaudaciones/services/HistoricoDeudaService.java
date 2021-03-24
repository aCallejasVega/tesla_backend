package bo.com.tesla.recaudaciones.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bo.com.tesla.administracion.entity.DeudaClienteEntity;
import bo.com.tesla.entidades.dto.ConceptoDto;
import bo.com.tesla.entidades.dto.DeudasClienteDto;
import bo.com.tesla.recaudaciones.dao.IHistoricoDeudaDao;
import bo.com.tesla.recaudaciones.dto.EstadoTablasDto;
import bo.com.tesla.recaudaciones.dto.RecaudadoraDto;

@Service
public class HistoricoDeudaService implements IHistoricoDeudaService {

	@Autowired
	private IHistoricoDeudaDao iHistoricoDeudaDao;

	/*
	 * @Override public Integer updateEstado(Long deudaClienteId, String estado) {
	 * return iHistoricoDeudaDao.updateEstado(deudaClienteId, estado); }
	 */

	@Override
	public Integer updateHistoricoDeudaLst(List<DeudaClienteEntity> deudaClienteEntities) {
		List<Long> deudaClienteIdLst = deudaClienteEntities.stream().mapToLong(d -> d.getDeudaClienteId()).boxed()
				.collect(Collectors.toList());

		return iHistoricoDeudaDao.updateLstEstado(deudaClienteIdLst, "COBRADO");
	}

	@Transactional(readOnly = true)
	@Override
	public Page<DeudasClienteDto> groupByDeudasClientes(Long archivoId, String paramBusqueda, int page, int size) {
		Page<DeudasClienteDto> historicoDeudasList;
		Pageable paging = PageRequest.of(page, size);
		historicoDeudasList = this.iHistoricoDeudaDao.groupByDeudasClientes(archivoId, paramBusqueda, paging);
		Integer key = 0;

		for (DeudasClienteDto deudasClienteDto : historicoDeudasList) {
			List<ConceptoDto> conceptosList = this.iHistoricoDeudaDao.findConceptos(deudasClienteDto.archivoId,
					deudasClienteDto.servicio, deudasClienteDto.tipoServicio, deudasClienteDto.periodo,
					deudasClienteDto.codigoCliente);
			if (!conceptosList.isEmpty()) {
				key++;
				deudasClienteDto.key = key + "";
				deudasClienteDto.nombreCliente = conceptosList.get(0).nombreCliente;
				deudasClienteDto.direccion = conceptosList.get(0).direccion;
				deudasClienteDto.nit = conceptosList.get(0).nit;
				deudasClienteDto.nroDocumento = conceptosList.get(0).nroDocumento;
				deudasClienteDto.telefono = conceptosList.get(0).telefono;
				deudasClienteDto.esPostpago = conceptosList.get(0).esPostpago;
			}
			deudasClienteDto.conceptoLisit = conceptosList;
		}
		return historicoDeudasList;

	}

	@Override
	public List<EstadoTablasDto> findEstadoHistorico() {

		return this.iHistoricoDeudaDao.findEstadoHistorico();
	}

	
	/*@Override
	public List<DeudasClienteDto> findDeudasByArchivoIdAndEstado(Long archivoId, String recaudadorId, String estado) {

		return this.iHistoricoDeudaDao.findDeudasByArchivoIdAndEstadoForEntidad(archivoId, recaudadorId, estado);
	}*/

	@Override
	public BigDecimal getMontoTotalCobrados(Long archivoId, Long recaudadorId) {

		return this.iHistoricoDeudaDao.getMontoTotalCobrados(archivoId, recaudadorId);
	}

	@Override
	public List<RecaudadoraDto> getMontoTotalPorRecaudadora(Long archivoId) {

		List<Object[]> objectList = this.iHistoricoDeudaDao.getMontoTotalPorRecaudadora(archivoId);
		List<RecaudadoraDto> recaudadorList = new ArrayList<>();
		for (Object[] objects : objectList) {
			RecaudadoraDto recaudador = new RecaudadoraDto(objects[0] + "", new BigDecimal(objects[1] + ""));
			recaudadorList.add(recaudador);
		}
		return recaudadorList;
	}

}