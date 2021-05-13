package bo.com.tesla.recaudaciones.dto;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.*;

public class ClienteDto {

    @NotBlank(message = "El 'Codigo Cliente' es obligatorio.")
    public String codigoCliente;
    @NotBlank(message = "El 'Nombre Cliente' es obligatorio.")
    public String nombreCliente;
    @NotBlank(message = "El 'Nro. Documento Cliente' es obligatorio.")
    public String nroDocumento;
    @NotBlank(message = "El monto total cobrado debe ser enviado")
    public BigDecimal montoTotalCobrado;
    public List<ServicioDeudaDto> servicioDeudaDtoList;

    public ClienteDto() {
    }

    public ClienteDto(String codigoCliente, String nombreCliente, String nroDocumento) {
        this.codigoCliente = codigoCliente;
        this.nombreCliente = nombreCliente;
        this.nroDocumento = nroDocumento;
    }

}
