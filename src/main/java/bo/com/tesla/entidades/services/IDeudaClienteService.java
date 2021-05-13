package bo.com.tesla.entidades.services;

import org.springframework.data.domain.Page;

import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.entidades.dto.DeudasClienteDto;
import bo.com.tesla.entidades.dto.DeudasClienteRestDto;

public interface IDeudaClienteService {
	
	public void deletByArchivoId(Long archivoId);
	
	public Page<DeudasClienteDto> findDeudasClientesByArchivoId(Long archivoId,String paramBusqueda,int page,int size);
	
	public String  saveCustomerDebt(DeudasClienteRestDto deuda,SegUsuarioEntity usuario) throws Exception;
}
