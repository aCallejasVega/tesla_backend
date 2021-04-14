package bo.com.tesla.pagos.services;

import java.util.List;

import bo.com.tesla.administracion.entity.PTransaccionPagoEntity;
import bo.com.tesla.pagos.dto.PBeneficiarioDto;

public interface IPTransaccionPagoService {
	
	public PTransaccionPagoEntity save(PTransaccionPagoEntity entity);
	
	public PTransaccionPagoEntity saveForPagoAbonado(List<PBeneficiarioDto> abonoCliente,Long usuarioId);

}
