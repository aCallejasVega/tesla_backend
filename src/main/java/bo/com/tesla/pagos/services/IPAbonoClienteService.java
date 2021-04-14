package bo.com.tesla.pagos.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import bo.com.tesla.pagos.dto.PBeneficiarioDto;

public interface IPAbonoClienteService {
	public Page<PBeneficiarioDto> groupByAbonosClientes(Long archivoId, String paramBusqueda, int page, int size);

	public void deletByArchivoId(Long archivoId);

	public List<PBeneficiarioDto> getAbonosParaPagar(Long servicioProductoId,Long recaudadorId,String paramBusqueda);	
	
	public List<PBeneficiarioDto> getBeneficiarioPagos(Long archivoId,String codigoCliente, String nroDocumentoCliente);
}
