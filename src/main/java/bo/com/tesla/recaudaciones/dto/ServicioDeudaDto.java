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
    public List<DeudaClienteDto> deudaClienteDtos;
    @JsonIgnore
    private List<DeudaClienteEntity> deudaClientes;

    public ServicioDeudaDto(String tipoServicio, String servicio, String periodo, Long entidadId, BigDecimal subTotal) {
        this.tipoServicio = tipoServicio;
        this.servicio = servicio;
        this.periodo = periodo;
        this.entidadId = entidadId;
        this.subTotal = subTotal;
    }

    public List<DeudaClienteEntity> getDeudaClientes() {
        return deudaClientes;
    }

    public void setDeudaClientes(List<DeudaClienteEntity> deudaClientes) {
        this.deudaClientes = deudaClientes;
    }
}
