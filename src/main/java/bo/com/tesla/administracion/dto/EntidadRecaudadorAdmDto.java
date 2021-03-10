package bo.com.tesla.administracion.dto;

import bo.com.tesla.administracion.entity.EntidadEntity;
import bo.com.tesla.administracion.entity.RecaudadorEntity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

public class EntidadRecaudadorAdmDto {

    public Long entidadRecaudadorId;
    public Long entidadId;
    public EntidadAdmDto entidadAdmDto;
    public Long recaudadorId;
    public RecaudadorAdmDto recaudadorAdmDto;
    public String usuarioCreacionLogin;
    public Date fechaCreacion;
    public String estado;

    public EntidadRecaudadorAdmDto(Long entidadId, Long recaudadorId, String usuarioCreacionLogin, Date fechaCreacion, String estado) {
        this.entidadId = entidadId;
        this.recaudadorId = recaudadorId;
        this.usuarioCreacionLogin = usuarioCreacionLogin;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }
}
