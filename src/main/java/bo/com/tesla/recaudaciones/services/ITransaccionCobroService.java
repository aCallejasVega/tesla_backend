package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.*;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

public interface ITransaccionCobroService {

	public TransaccionCobroEntity loadTransaccionCobro(ServicioDeudaDto servicioDeudaDto, EntidadEntity entidadEntity,
			Long usuarioId, String nombreCientePago, String nroDocumentoClientePago,
			EntidadComisionEntity entidadComisionEntity, RecaudadorEntity recaudadorEntity,
			RecaudadorComisionEntity recaudadorComisionEntity, ArchivoEntity archivoEntity, DominioEntity metodoCobro);

	public TransaccionCobroEntity saveTransaccionCobro(TransaccionCobroEntity transaccionCobroEntity);

	public List<TransaccionCobroEntity> saveAllTransaccionesCobros(
			List<TransaccionCobroEntity> transaccionCobroEntities);

	public List<TransaccionCobroEntity> findDeudasCobradasByUsuarioCreacionForGrid(Long usuarioCreacion,
			Date fechaSeleccionada,Long entidadId);
}
