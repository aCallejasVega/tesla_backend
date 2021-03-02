package bo.com.tesla.recaudaciones.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bo.com.tesla.administracion.entity.HistoricoDeudaEntity;
import bo.com.tesla.entidades.dto.ConceptoDto;
import bo.com.tesla.entidades.dto.DeudasClienteDto;
import bo.com.tesla.recaudaciones.dao.IHistoricoDeudaDao;

@Service
public class HistoricoDeudaService implements IHistoricoDeudaService {

    @Autowired
    private IHistoricoDeudaDao iHistoricoDeudaDao;

    @Override
    public HistoricoDeudaEntity updateEstado(Long deudaClienteId, String estado) {
        Optional<HistoricoDeudaEntity> optionalHistoricoDeudaEntity = this.iHistoricoDeudaDao.findByDeudaClienteId(deudaClienteId);
        if(!optionalHistoricoDeudaEntity.isPresent()) {
            return null;
        } else {
            HistoricoDeudaEntity historicoDeudaEntity = optionalHistoricoDeudaEntity.get();
            historicoDeudaEntity.setEstado(estado);
            return historicoDeudaEntity;
        }
    }

    @Transactional(readOnly = true)
	@Override
	public Page<DeudasClienteDto> groupByDeudasClientes(Long archivoId, String paramBusqueda, int page, int size) {
		Page<DeudasClienteDto> historicoDeudasList ;
		Pageable paging = PageRequest.of(page, size);
		historicoDeudasList=this.iHistoricoDeudaDao.groupByDeudasClientes(archivoId, paramBusqueda, paging);
		Integer key=0;
		
		for (DeudasClienteDto deudasClienteDto : historicoDeudasList) {
			List<ConceptoDto> conceptosList = this.iHistoricoDeudaDao.findConceptos(deudasClienteDto.archivoId,
					deudasClienteDto.servicio, deudasClienteDto.tipoServicio, deudasClienteDto.periodo,
					deudasClienteDto.codigoCliente);
			if(!conceptosList.isEmpty()) {
				key++;
				deudasClienteDto.key=key+"";
				deudasClienteDto.nombreCliente=conceptosList.get(0).nombreCliente;
				deudasClienteDto.direccion=conceptosList.get(0).direccion;
				deudasClienteDto.nit=conceptosList.get(0).nit;
				deudasClienteDto.nroDocumento=conceptosList.get(0).nroDocumento;
				deudasClienteDto.telefono=conceptosList.get(0).telefono;	
				deudasClienteDto.esPostpago=conceptosList.get(0).esPostpago;
			}
			deudasClienteDto.conceptoLisit = conceptosList;
		}
		return historicoDeudasList;
		
	}
    
}
