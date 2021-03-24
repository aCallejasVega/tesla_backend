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
    public List<ServicioDeudaDto> servicioDeudaDtoList;

    public ClienteDto() {
    }

    public ClienteDto(String codigoCliente, String nombreCliente, String nroDocumento) {
        this.codigoCliente = codigoCliente;
        this.nombreCliente = nombreCliente;
        this.nroDocumento = nroDocumento;
    }
/*
    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public List<ServicioDeudaDto> getServicioDeudaDtoList() {
        return servicioDeudaDtoList;
    }

    public void setServicioDeudaDtoList(List<ServicioDeudaDto> servicioDeudaDtoList) {
        this.servicioDeudaDtoList = servicioDeudaDtoList;
    }
*/
}
