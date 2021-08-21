package bo.com.tesla.recaudaciones.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.*;

public class ClienteDto {

    public Long entidadId;//Si se enviara una api a entidad, no deberia ser obligatorio
    @NotBlank(message = "El 'Codigo Cliente' es obligatorio.")
    public String codigoCliente;
    @NotBlank(message = "El 'Nombre Cliente' es obligatorio.")
    public String nombreCliente;
    @NotBlank(message = "El 'Nro. Documento Cliente' es obligatorio.")
    public String nroDocumento;
    @NotNull(message = "El monto total cobrado debe ser enviado")
    public BigDecimal montoTotalCobrado;
    @NotNull(message = "El Método de Cobro es obligatorio.")
    public Long metodoCobroId;
    @NotNull(message = "La Dimensión de reimpresión es obligatoria.")
    public Long dimensionId;
    @Valid
    @NotNull (message = "Debe enviar al menos un agrupador de deuda.")
    @Size(min = 1, message = "Debe enviar al menos un agrupador de deuda.")
    public List<ServicioDeudaDto> servicioDeudaDtoList;

    public ClienteDto() {
    }

    public ClienteDto(String codigoCliente, String nombreCliente, String nroDocumento) {
        this.codigoCliente = codigoCliente;
        this.nombreCliente = nombreCliente;
        this.nroDocumento = nroDocumento;
    }

}
