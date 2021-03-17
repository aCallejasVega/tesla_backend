package bo.com.tesla.recaudaciones.services;

<<<<<<< HEAD
import java.math.BigDecimal;
import java.util.List;

=======
import bo.com.tesla.administracion.entity.DeudaClienteEntity;
>>>>>>> 0d76ff440f69e0af8af574994a35ff1c074f7939
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import bo.com.tesla.administracion.entity.HistoricoDeudaEntity;
import bo.com.tesla.entidades.dto.DeudasClienteDto;
import bo.com.tesla.recaudaciones.dto.EstadoTablasDto;
import bo.com.tesla.recaudaciones.dto.RecaudadoraDto;

import java.util.List;

<<<<<<< HEAD
	public HistoricoDeudaEntity updateEstado(Long deudaClienteId, String estado);

	public Page<DeudasClienteDto> groupByDeudasClientes(Long archivoId, String paramBusqueda, int page, int size);

	public List<EstadoTablasDto> findEstadoHistorico();

	public List<DeudasClienteDto> findDeudasByArchivoIdAndRecaudadorIdAndEstado(Long archivoId, Long recaudadorId, String estado);
	public List<DeudasClienteDto> findDeudasByArchivoIdAndEstado(Long archivoId, String estado);

	public BigDecimal getMontoTotalCobrados(Long archivoId, Long recaudadorId);
	
	public List<RecaudadoraDto> getMontoTotalPorRecaudadora(@Param("archivoId") Long archivoId);
=======
public interface IHistoricoDeudaService {

    //public Integer updateEstado(Long deudaClienteId, String estado);
    public Integer updateHistoricoDeudaLst(List<DeudaClienteEntity> deudaClienteEntities);
    public Page<DeudasClienteDto> groupByDeudasClientes(Long archivoId , String paramBusqueda,int page,int size);
>>>>>>> 0d76ff440f69e0af8af574994a35ff1c074f7939
}
