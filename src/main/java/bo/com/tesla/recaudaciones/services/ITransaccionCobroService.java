package bo.com.tesla.recaudaciones.services;

import java.util.Date;
import java.util.List;

import bo.com.tesla.administracion.entity.ArchivoEntity;
import bo.com.tesla.administracion.entity.DominioEntity;
import bo.com.tesla.administracion.entity.EntidadComisionEntity;
import bo.com.tesla.administracion.entity.EntidadEntity;
import bo.com.tesla.administracion.entity.RecaudadorComisionEntity;
import bo.com.tesla.administracion.entity.RecaudadorEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.administracion.entity.TransaccionCobroEntity;
import bo.com.tesla.facturaciones.computarizada.dto.AnulacionFacturaLstDto;
import bo.com.tesla.facturaciones.computarizada.dto.FacturaDto;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;

public interface ITransaccionCobroService {

	public TransaccionCobroEntity loadTransaccionCobro(ServicioDeudaDto servicioDeudaDto, EntidadEntity entidadEntity,
			Long usuarioId, String nombreCientePago, String nroDocumentoClientePago,
			EntidadComisionEntity entidadComisionEntity, RecaudadorEntity recaudadorEntity,
			RecaudadorComisionEntity recaudadorComisionEntity, ArchivoEntity archivoEntity, DominioEntity metodoCobro);

	public TransaccionCobroEntity saveTransaccionCobro(TransaccionCobroEntity transaccionCobroEntity);

	public List<TransaccionCobroEntity> findDeudasCobradasByUsuarioCreacionForGrid(Long usuarioCreacion,
			Date fechaSeleccionada, Long entidadId);

	public TransaccionCobroEntity loadTransaccionCobro(ServicioDeudaDto servicioDeudaDto, EntidadEntity entidadEntity,
			Long usuarioId, String nombreCientePago, String nroDocumentoClientePago,
			EntidadComisionEntity entidadComisionEntity, RecaudadorEntity recaudadorEntity,
			RecaudadorComisionEntity recaudadorComisionEntity, ArchivoEntity archivoEntity, DominioEntity metodoCobro,
			DominioEntity modalidadFacturacion, String codigoActividadEconomic);

	public List<TransaccionCobroEntity> saveAllTransaccionesCobros(
			List<TransaccionCobroEntity> transaccionCobroEntities);

	Boolean anularTransaccion(Long entidadId, AnulacionFacturaLstDto anulacionFacturaLstDto, Long modalidadFacturacionId,
			SegUsuarioEntity usuarioEntity);

	void updateFacturasTransaccion(List<FacturaDto> facturaDtoList);

	List<String> getCodigosActividadUnicos(List<TransaccionCobroEntity> transaccionCobroEntityList);

}
