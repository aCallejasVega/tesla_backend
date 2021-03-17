package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.DeudaClienteEntity;
import org.springframework.data.domain.Page;

import bo.com.tesla.administracion.entity.HistoricoDeudaEntity;
import bo.com.tesla.entidades.dto.DeudasClienteDto;

import java.util.List;

public interface IHistoricoDeudaService {

    //public Integer updateEstado(Long deudaClienteId, String estado);
    public Integer updateHistoricoDeudaLst(List<DeudaClienteEntity> deudaClienteEntities);
    public Page<DeudasClienteDto> groupByDeudasClientes(Long archivoId , String paramBusqueda,int page,int size);
}
