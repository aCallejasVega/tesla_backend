package bo.com.tesla.pagos.services;

import java.util.List;

import bo.com.tesla.administracion.entity.PPagoClienteEntity;
import bo.com.tesla.administracion.entity.PTransaccionPagoEntity;
import bo.com.tesla.pagos.dto.PBeneficiarioDto;

public interface IPPagoClienteService {
	
	public PPagoClienteEntity save(PPagoClienteEntity entity);
	
	public PTransaccionPagoEntity realizarPago(List<PBeneficiarioDto> abonoCliente,Long usuarioId);
	
	

}
