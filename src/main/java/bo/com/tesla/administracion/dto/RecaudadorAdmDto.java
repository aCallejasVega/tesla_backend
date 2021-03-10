package bo.com.tesla.administracion.dto;

import bo.com.tesla.administracion.entity.EntidadRecaudadorEntity;
import bo.com.tesla.administracion.entity.SucursalEntity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class RecaudadorAdmDto {

    public Long recaudadorId;
    public Long tipoRecaudadorId;
    public String tipoRecaudadorDescripcion;
    public Long usuarioCreacion;
    public Date fechaCreacion;
    public String estado;
    public List<SucursalAdmDto> sucursalAdmDtoList;
    public List<EntidadRecaudadorAdmDto> entidadRecaudadorAdmDtoList;

    public RecaudadorAdmDto(Long recaudadorId, Long tipoRecaudadorId, String tipoRecaudadorDescripcion, Long usuarioCreacion, Date fechaCreacion, String estado) {
        this.recaudadorId = recaudadorId;
        this.tipoRecaudadorId = tipoRecaudadorId;
        this.tipoRecaudadorDescripcion = tipoRecaudadorDescripcion;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }
}
