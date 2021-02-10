package bo.com.tesla.entidades.services;

import java.util.List;

import org.springframework.data.domain.Page;

import bo.com.tesla.entidades.dto.DeudasClienteDto;

public interface IDeudaClienteService {
	
	public void deletByArchivoId(Long archivoId);
	
	public Page<DeudasClienteDto> findDeudasClientesByArchivoId(Long archivoId,String paramBusqueda,int page,int size);
	

}
