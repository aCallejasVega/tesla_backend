package bo.com.tesla.administracion.dto;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

public class EntidadAdmDto {

    public Long entidadId;
    @NotBlank(message = "El NOMBRE es obligatorio.")
    public String nombre;
    public String nombreComercial;
    public String direccion;
    public String telefono;
    public String nit;
    public String pathLogo;
    public Boolean comprobanteEnUno;
    public Long actividadEconomicaId;
    public String actividadEconomicaDescripcion;
    public Long municipioId;
    public String municipioDescripcion;
    public Long tipoEntidadId;
    public String tipoEntidadDescripcion;
    public Date fechaCreacion;
    public String usuarioCreacionLogin;
    public String estado;
    public List<SucursalEntidadAdmDto> sucursalEntidadAdmDtoList;

    public EntidadAdmDto(Long entidadId, String nombre, String nombreComercial, String direccion, String telefono, String nit, String pathLogo, Boolean comprobanteEnUno, Long actividadEconomicaId, String actividadEconomicaDescripcion, Long municipioId, String municipioDescripcion, Long tipoEntidadId, String tipoEntidadDescripcion, Date fechaCreacion, String usuarioCreacionLogin, String estado) {
        this.entidadId = entidadId;
        this.nombre = nombre;
        this.nombreComercial = nombreComercial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nit = nit;
        this.pathLogo = pathLogo;
        this.comprobanteEnUno = comprobanteEnUno;
        this.actividadEconomicaId = actividadEconomicaId;
        this.actividadEconomicaDescripcion = actividadEconomicaDescripcion;
        this.municipioId = municipioId;
        this.municipioDescripcion = municipioDescripcion;
        this.tipoEntidadId = tipoEntidadId;
        this.tipoEntidadDescripcion = tipoEntidadDescripcion;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacionLogin = usuarioCreacionLogin;
        this.estado = estado;
    }
}
