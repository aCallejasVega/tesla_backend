package bo.com.tesla.entidades.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bo.com.tesla.entidades.dao.IDeudaClienteDao;
import bo.com.tesla.entidades.dto.ConceptoDto;
import bo.com.tesla.entidades.dto.DeudasClienteDto;

@Service

public class DeudaClienteService implements IDeudaClienteService {

	@Autowired
	private IDeudaClienteDao deudaClienteDao;

	@Override
	public void deletByArchivoId(Long archivoId) {
		this.deudaClienteDao.deletByArchivoId(archivoId);

	}

	@Transactional(readOnly = true)
	@Override
	public Page<DeudasClienteDto> findDeudasClientesByArchivoId(Long archivoId,String paramBusqueda,int page,int size) {
		Page<DeudasClienteDto> deudaClienteList ;
		Pageable paging = PageRequest.of(page, size);
		
		deudaClienteList = this.deudaClienteDao.groupByDeudasClientes(archivoId,paramBusqueda,paging);

		for (DeudasClienteDto deudasClienteDto : deudaClienteList) {
			
			List<ConceptoDto> conceptosList = this.deudaClienteDao.findConceptos(deudasClienteDto.archivoId,
					deudasClienteDto.servicio, deudasClienteDto.tipoServicio, deudasClienteDto.periodo,
					deudasClienteDto.codigoCliente);
			
			if(!conceptosList.isEmpty()) {
				deudasClienteDto.key=conceptosList.get(0).nroRegistro+"_"+conceptosList.get(0).archivoId;
				deudasClienteDto.nombreCliente=conceptosList.get(0).nombreCliente;
				deudasClienteDto.direccion=conceptosList.get(0).direccion;
				deudasClienteDto.nit=conceptosList.get(0).nit;
				deudasClienteDto.nroDocumento=conceptosList.get(0).nroDocumento;
				deudasClienteDto.telefono=conceptosList.get(0).telefono;					
			}
					
			deudasClienteDto.conceptoLisit = conceptosList;
		}

		return deudaClienteList;
	}

}
