package bo.com.tesla.facturaciones.computarizada.services;

import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.facturaciones.computarizada.dto.AnulacionFacturaLstDto;
import bo.com.tesla.facturaciones.computarizada.dto.ResponseDto;
import bo.com.tesla.useful.config.BusinesException;

public interface IAnulacionFacturaService {
    ResponseDto postAnulacionLst(Long entidadId, AnulacionFacturaLstDto anulacionFacturaLstDto);
    Boolean anularTransaccionConRecuperacionDeudas(Long entidadId,
                                                   AnulacionFacturaLstDto anulacionFacturaLstDto,
                                                   SegUsuarioEntity usuarioEntity) throws BusinesException;
    Boolean anularTransaccionConCargadoErroneo(Long entidadId,
                                               AnulacionFacturaLstDto anulacionFacturaLstDto,
                                               SegUsuarioEntity usuarioEntity) throws BusinesException;
}
