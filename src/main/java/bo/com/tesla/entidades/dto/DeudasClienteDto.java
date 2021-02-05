package bo.com.tesla.entidades.dto;

import java.util.List;

public class DeudasClienteDto {
	
	public Long archivoId;
	public String servicio;
	public String tipoServicio;
	public String periodo;
	public String codigoCliente;
	public String nit;
	public String direccion;
	public String nroDocumento;
	public String telefono;
	public String nombreCliente;
	public String key;
	public List<ConceptoDto> conceptoLisit;
	
	
	public DeudasClienteDto() {
	
	}
	
	/*
	 *  Constructor utilizado para la consulta 
	 *  gropByDeudasClientes de la clase IDeudaCliente
	 *  @author   aCallejas
	 * */
	public DeudasClienteDto(Long archivoId,String servicio, String tipoServicio, String periodo, String codigoCliente) {
	
		
		this.archivoId=archivoId;
		this.servicio = servicio;
		this.tipoServicio = tipoServicio;
		this.periodo = periodo;
		this.codigoCliente = codigoCliente;
	}
	
	

}
