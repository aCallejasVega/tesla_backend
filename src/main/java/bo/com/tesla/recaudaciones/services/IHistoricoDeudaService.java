package bo.com.tesla.recaudaciones.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;

import bo.com.tesla.administracion.entity.DeudaClienteEntity;
import bo.com.tesla.administracion.entity.HistoricoDeudaEntity;
import bo.com.tesla.entidades.dto.DeudasClienteDto;
import bo.com.tesla.recaudaciones.dto.EstadoTablasDto;
import bo.com.tesla.recaudaciones.dto.RecaudadoraDto;

public interface IHistoricoDeudaService {

    //public Integer updateEstado(Long deudaClienteId, String estado);
    public Integer updateHistoricoDeudaLst(List<DeudaClienteEntity> deudaClienteEntities);
    public Page<DeudasClienteDto> groupByDeudasClientes(Long archivoId , String paramBusqueda,int page,int size);
    
    
	public List<EstadoTablasDto> findEstadoHistorico();

	
	//public List<DeudasClienteDto> findDeudasByArchivoIdAndEstado(Long archivoId, String recaudadorId, String estado);

	public BigDecimal getMontoTotalCobrados(Long archivoId, Long recaudadorId);
	
	public List<RecaudadoraDto> getMontoTotalPorRecaudadora( Long archivoId);
	
}