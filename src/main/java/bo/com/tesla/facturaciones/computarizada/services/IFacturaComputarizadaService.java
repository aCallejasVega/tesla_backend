package bo.com.tesla.facturaciones.computarizada.services;

import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.administracion.entity.SucursalEntidadEntity;
import bo.com.tesla.administracion.entity.TransaccionCobroEntity;
import bo.com.tesla.facturaciones.computarizada.dto.CodigoControlDto;
import bo.com.tesla.facturaciones.computarizada.dto.FacturaDto;
import bo.com.tesla.facturaciones.computarizada.dto.ResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface IFacturaComputarizadaService {
    ResponseDto postCodigoControl(CodigoControlDto codigoControlDto, Long entidadId);
    ResponseDto postFacturas(SucursalEntidadEntity sucursalEntidadEntity, List<TransaccionCobroEntity> transaccionCobroEntityList, Boolean comprobanteEnUno, BigDecimal montoTotal);
    ResponseDto postFacturaLstFilter(Long entidadId, int page, FacturaDto facturaDto, Long recaudadoraId);
    ResponseDto getFacturaReport(Long entidadId, Long facturaId);
    ResponseDto getLibroVentasReport(Long entidadId, FacturaDto facturaDto);
    ResponseDto getFacturaDto(Long entidadId, Long facturaId);
    List<Long> findFacturasByEntidadAndRecaudador(Long entidadId, Long recaudadorId);

}
