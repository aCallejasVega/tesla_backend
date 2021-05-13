package bo.com.tesla.facturaciones.computarizada.dto;

import java.util.Date;

public class DosificacionDto {

    public Long dosificacionId;
    public String codigoActividadEconomica;
    public String actividadEconomica;
    public Boolean porTerceros;
    public String numeroAutorizacion;
    public Date fechaLimiteEmision;
    public String llaveDosificacion;
    public Long caracteristicasEspecialesId;
    public String caracteristicasEspecialesDescripcion;
    public Long monedaId;
    public String monedaDescripcion;
    public Long tipoDocumentoFiscalId;
    public String tipoDocumentoFiscalDescripcion;
    public Long sucursalId;
    public String nombreSucursal;
    public String estado;
    public boolean alerta;
    public Date fechaVigencia;

}
