package bo.com.tesla.facturaciones.computarizada.services;

import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.facturaciones.computarizada.dto.AnulacionFacturaLstDto;
import bo.com.tesla.facturaciones.computarizada.dto.ResponseDto;

public interface IAnulacionFacturaService {
    ResponseDto postAnulacionLst(Long entidadId, AnulacionFacturaLstDto anulacionFacturaLstDto);
    Boolean anularTransaccion(Long entidadId,
                              AnulacionFacturaLstDto anulacionFacturaLstDto,
                              SegUsuarioEntity usuarioEntity);
}
