package bo.com.tesla.facturaciones.computarizada.services;

import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.facturaciones.computarizada.dto.ResponseDto;

public interface IDominioFacturaService {
    ResponseDto getDominiosLst(Long entidadId, String dominio);
}
