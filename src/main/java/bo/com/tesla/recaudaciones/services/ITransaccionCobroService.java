package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.TransaccionCobroEntity;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;

import java.util.List;

public interface ITransaccionCobroService {

    public TransaccionCobroEntity loadTransaccionCobro(ServicioDeudaDto servicioDeudaDto, Long usuarioId, String nombreCientePago, String nroDocumentoClientePago);
    public TransaccionCobroEntity saveTransaccionCobro(TransaccionCobroEntity transaccionCobroEntity);
    public List<TransaccionCobroEntity> saveAllTransaccionesCobros(List<TransaccionCobroEntity> transaccionCobroEntities);
}
