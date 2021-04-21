package bo.com.tesla.pagos.services;

import bo.com.tesla.administracion.entity.PTransaccionPagoEntity;
import bo.com.tesla.pagos.dto.PBeneficiarioDto;

public interface IPTransaccionPagoService {
	
	public PTransaccionPagoEntity save(PTransaccionPagoEntity entity);
	
	public PTransaccionPagoEntity saveForPagoAbonado(PBeneficiarioDto beneficiario, Long usuarioId, Long secuencialTransaccion);
	
	public Long getSecuencialTransaccion();

}
