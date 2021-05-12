package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.*;
import bo.com.tesla.facturaciones.computarizada.dto.AnulacionFacturaLstDto;
import bo.com.tesla.facturaciones.computarizada.dto.FacturaDto;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;

import java.util.List;

public interface ITransaccionCobroService {

    public TransaccionCobroEntity loadTransaccionCobro(ServicioDeudaDto servicioDeudaDto, EntidadEntity entidadEntity, Long usuarioId, String nombreCientePago, String nroDocumentoClientePago,
                                                       EntidadComisionEntity entidadComisionEntity, RecaudadorEntity recaudadorEntity, RecaudadorComisionEntity recaudadorComisionEntity,
                                                       ArchivoEntity archivoEntity, DominioEntity metodoCobro,
                                                       DominioEntity modalidadFacturacion,
                                                       String codigoActividadEconomic);
    public TransaccionCobroEntity saveTransaccionCobro(TransaccionCobroEntity transaccionCobroEntity);
    public List<TransaccionCobroEntity> saveAllTransaccionesCobros(List<TransaccionCobroEntity> transaccionCobroEntities);
    Boolean anularTransaccion(Long entidadId,
                              AnulacionFacturaLstDto anulacionFacturaLstDto,
                              SegUsuarioEntity usuarioEntity);
    void updateFacturas(List<TransaccionCobroEntity> transaccionCobroEntityList,
                        boolean comprobanteEnUno,
                        List<FacturaDto> facturaDtoList);
    List<String> getCodigosActividadUnicos(List<TransaccionCobroEntity> transaccionCobroEntityList);
}
