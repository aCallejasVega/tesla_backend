package bo.com.tesla.recaudaciones.dto;

import java.math.BigDecimal;

public class DeudaClienteDto {

    public Long deudaClienteId;
    public Integer nroRegistro;
    public BigDecimal cantidad;
    public String concepto;
    public BigDecimal montoUnitario;
    public BigDecimal subTotal;
    public Character tipo;
    public String datoExtras;
    public Boolean tipoComprobante;
    public String periodoCabecera;
    public String codigoCliente;
    public String nombreCliente;
    public String nroDocumento;

    public DeudaClienteDto(Long deudaClienteId, Integer nroRegistro, BigDecimal cantidad, String concepto, BigDecimal montoUnitario, BigDecimal subTotal, Character tipo, String datoExtras, Boolean tipoComprobante, String periodoCabecera, String codigoCliente, String nombreCliente, String nroDocumento) {
        this.deudaClienteId = deudaClienteId;
        this.nroRegistro = nroRegistro;
        this.cantidad = cantidad;
        this.concepto = concepto;
        this.montoUnitario = montoUnitario;
        this.subTotal = subTotal;
        this.tipo = tipo;
        this.datoExtras = datoExtras;
        this.tipoComprobante = tipoComprobante;
        this.periodoCabecera = periodoCabecera;
        this.codigoCliente = codigoCliente;
        this.nombreCliente = nombreCliente;
        this.nroDocumento = nroDocumento;
    }
}
