package bo.com.tesla.recaudaciones.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class DeudaClienteDto {

    public Long deudaClienteId;
    @JsonIgnore
    public Long archivoId;
    public BigDecimal cantidad;
    public String concepto;
    public BigDecimal montoUnitario;
    public BigDecimal subTotal;
    @JsonIgnore
    public Boolean tipoComprobante;
    public String periodoCabecera;
    @JsonIgnore
    public String codigoCliente;
    @JsonIgnore
    public String nombreCliente;
    @JsonIgnore
    public String nroDocumento;
    public Boolean esPostpago;
    public Boolean editable;

    public DeudaClienteDto(Long deudaClienteId, Long archivoId, BigDecimal cantidad, String concepto, BigDecimal montoUnitario, BigDecimal subTotal, Boolean tipoComprobante, String periodoCabecera, String codigoCliente, String nombreCliente, String nroDocumento, Boolean esPostpago, Boolean editable) {
        this.deudaClienteId = deudaClienteId;
        this.archivoId = archivoId;
        this.cantidad = cantidad;
        this.concepto = concepto;
        this.montoUnitario = montoUnitario;
        this.subTotal = subTotal;
        this.tipoComprobante = tipoComprobante;
        this.periodoCabecera = periodoCabecera;
        this.codigoCliente = codigoCliente;
        this.nombreCliente = nombreCliente;
        this.nroDocumento = nroDocumento;
        this.esPostpago = esPostpago;
        this.editable = editable;
    }
}
