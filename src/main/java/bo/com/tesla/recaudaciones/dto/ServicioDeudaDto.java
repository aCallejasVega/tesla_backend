package bo.com.tesla.recaudaciones.dto;

import bo.com.tesla.administracion.entity.DeudaClienteEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public class ServicioDeudaDto {

    public Long key;
    @NotBlank(message = "El 'Tipo Servicio' es obligatorio.")
    public String tipoServicio;
    @NotBlank(message = "El 'Servicio' es obligatorio.")
    public String servicio;
    @NotBlank(message = "El 'Periodo' es obligatorio.")
    public String periodo;
    @NotNull(message = "El 'Subtotal' es obligatorio.")
    public BigDecimal subTotal;
    @JsonIgnore
    public String plantilla;
    public Boolean editable;
    public Boolean editando;
    @Valid
    @NotNull (message = "Debe enviar al menos una deuda.")
    @Size(min = 1, message = "Debe enviar al menos una deuda.")
    public List<DeudaClienteDto> deudaClienteDtos;

    public ServicioDeudaDto(String tipoServicio, String servicio, String periodo, BigDecimal subTotal) {
        this.tipoServicio = tipoServicio;
        this.servicio = servicio;
        this.periodo = periodo;
        this.subTotal = subTotal;
    }
}
