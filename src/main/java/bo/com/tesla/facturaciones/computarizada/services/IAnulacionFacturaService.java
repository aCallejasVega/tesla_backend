package bo.com.tesla.facturaciones.computarizada.services;

import bo.com.tesla.facturaciones.computarizada.dto.AnulacionFacturaLstDto;
import bo.com.tesla.facturaciones.computarizada.dto.ResponseDto;

public interface IAnulacionFacturaService {
    ResponseDto postAnulacionLst(Long entidadId, AnulacionFacturaLstDto anulacionFacturaLstDto);
}
