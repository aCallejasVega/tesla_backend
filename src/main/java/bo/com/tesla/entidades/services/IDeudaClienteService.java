package bo.com.tesla.entidades.services;

import java.util.List;

import bo.com.tesla.entidades.dto.DeudasClienteDto;

public interface IDeudaClienteService {
	
	public void deletByArchivoId(Long archivoId);
	
	public List<DeudasClienteDto> findDeudasClientesByArchivoId(Long archivoId,String paramBusqueda);
	

}
