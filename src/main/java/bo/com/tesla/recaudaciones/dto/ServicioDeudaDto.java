package bo.com.tesla.recaudaciones.dto;

import bo.com.tesla.administracion.entity.DeudaClienteEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;

public class ServicioDeudaDto {

    public Long key;
    public String codigoCliente;
    public String nombreCliente;
    public String nroDocumento;
    public String direccion;
    public String telefono;
    public String nit;
    public String tipoServicio;
    public String servicio;
    public String periodo;
    public Long entidadId;
    public BigDecimal subTotal;
    public String plantilla;
    public Boolean editable;
    public Boolean editando;
    public List<DeudaClienteDto> deudaClienteDtos;
    /*@JsonIgnore
    public List<DeudaClienteEntity> deudaClientesTodoEntity;*/

    public ServicioDeudaDto(String tipoServicio, String servicio, String periodo, Long entidadId, BigDecimal subTotal) {
        this.tipoServicio = tipoServicio;
        this.servicio = servicio;
        this.periodo = periodo;
        this.entidadId = entidadId;
        this.subTotal = subTotal;
    }
}
