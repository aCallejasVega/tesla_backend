package bo.com.tesla.facturaciones.computarizada.services;

import bo.com.tesla.facturaciones.computarizada.dto.ResponseDto;

public interface IActividadEconomicaService {
    //ResponseDto getLstFilter(Long entidadId, String codigoActividadEconomica);
    //ResponseDto getLstBySucursal(Long entidadId, Long sucursalId);
    ResponseDto getByCodigo(Long entidadId, String codigoActividadEconomica);
    ResponseDto getActividadesEconomicas(Long entidadId);
}
