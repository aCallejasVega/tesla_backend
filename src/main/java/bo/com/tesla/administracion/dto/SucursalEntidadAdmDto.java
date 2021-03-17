package bo.com.tesla.administracion.dto;

import java.util.Date;

public class SucursalEntidadAdmDto {

    public Long sucursalEntidadId;
    public Long entidadId;
    public String nombreSucursal;
    public String direccion;
    public String telefono;
    public String usuarioCreacionLogin;
    public Date fechaCreacion;
    public String estado;

    public SucursalEntidadAdmDto(Long sucursalEntidadId, Long entidadId, String nombreSucursal, String direccion, String telefono, String usuarioCreacionLogin, Date fechaCreacion, String estado) {
        this.sucursalEntidadId = sucursalEntidadId;
        this.entidadId = entidadId;
        this.nombreSucursal = nombreSucursal;
        this.direccion = direccion;
        this.telefono = telefono;
        this.usuarioCreacionLogin = usuarioCreacionLogin;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }
}
