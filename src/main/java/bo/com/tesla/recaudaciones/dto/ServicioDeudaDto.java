package bo.com.tesla.recaudaciones.dto;

import bo.com.tesla.administracion.entity.DeudaClienteEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;

public class ServicioDeudaDto {

    public Long key;
    public Long entidadId;
    public String codigoCliente;
    public String nombreCliente;
    public String nroDocumento;
    public String tipoServicio;
    public String servicio;
    public String periodo;
    public BigDecimal subTotal;
    @JsonIgnore
    public String plantilla;
    public Boolean editable;
    public Boolean editando;
    public List<DeudaClienteDto> deudaClienteDtos;
    //public String codigoActividadEconomica;

    public ServicioDeudaDto(Long entidadId, String tipoServicio, String servicio, String periodo, BigDecimal subTotal) {
        this.entidadId = entidadId;
        this.tipoServicio = tipoServicio;
        this.servicio = servicio;
        this.periodo = periodo;
        this.subTotal = subTotal;
    }
}
