package bo.com.tesla.administracion.dto;

import bo.com.tesla.administracion.entity.DominioEntity;
import bo.com.tesla.administracion.entity.RecaudadorEntity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

public class SucursalAdmDto {

    public Long sucursalId;
    public Long recaudadorId;
    public String direccion;
    public Integer telefono;
    public Long departamentoId;
    public String departamentoDescripcion;
    public Long localidadId;
    public Long localidadDescripcion;
    public Long usuarioCreacionLogin;
    public Date fechaCreacion;
    public String estado;

    public SucursalAdmDto(Long sucursalId, Long recaudadorId, String direccion, Integer telefono, Long departamentoId, String departamentoDescripcion, Long localidadId, Long localidadDescripcion, Long usuarioCreacionLogin, Date fechaCreacion, String estado) {
        this.sucursalId = sucursalId;
        this.recaudadorId = recaudadorId;
        this.direccion = direccion;
        this.telefono = telefono;
        this.departamentoId = departamentoId;
        this.departamentoDescripcion = departamentoDescripcion;
        this.localidadId = localidadId;
        this.localidadDescripcion = localidadDescripcion;
        this.usuarioCreacionLogin = usuarioCreacionLogin;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }
}
