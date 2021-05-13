package bo.com.tesla.facturaciones.computarizada.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FacturaDto {

    public Long facturaId;
    public Long numeroFactura;
    public Date fechaFactura;
    public BigDecimal montoTotal;
    public BigDecimal montoBaseImporteFiscal;
    public String codigoControl;
    public String codigoCliente;
    public String nombreRazonSocial;
    public String numeroDocumento;
    public BigDecimal montoDescuento;
    public String domicilioCliente;
    public String nombreAlumno;
    public String codigoActividadEconomica;
    public String actividadEconomica;
    public String estado;
    public List<DetalleFacturaDto> detalleFacturaDtoList;
    public String numeroAutorizacion;
    public Date fechaLimiteEmision;

    public Date fechaInicioFactura;
    public Date fechaFinFactura;

    public Long keyTeslaTransaccion;
    public String formatFile;

    public FacturaDto() {
    }
}
