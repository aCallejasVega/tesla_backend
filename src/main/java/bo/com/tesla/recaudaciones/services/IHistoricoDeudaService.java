package bo.com.tesla.recaudaciones.services;

import org.springframework.data.domain.Page;

import bo.com.tesla.administracion.entity.HistoricoDeudaEntity;
import bo.com.tesla.entidades.dto.DeudasClienteDto;

public interface IHistoricoDeudaService {

    public HistoricoDeudaEntity updateEstado(Long deudaClienteId, String estado);

    public Page<DeudasClienteDto> groupByDeudasClientes(Long archivoId , String paramBusqueda,int page,int size);
}
