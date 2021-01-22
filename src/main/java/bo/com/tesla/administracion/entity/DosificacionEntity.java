/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.tesla.administracion.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aCallejas
 */
@Entity
@Table(name = "dosificaciones", catalog = "exacta", schema = "tesla")

public class DosificacionEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dosificacion_id", nullable = false)
    private Long dosificacionId;
    @Basic(optional = false)
    @Column(name = "nro_autorizacion", nullable = false, length = 15)
    private String nroAutorizacion;
    @Basic(optional = false)
    @Column(name = "nro_inicial", nullable = false)
    private long nroInicial;
    @Basic(optional = false)
    @Column(name = "nro_final", nullable = false)
    private long nroFinal;
    @Basic(optional = false)
    @Column(name = "nro_factura", nullable = false, length = 255)
    private String nroFactura;
    @Basic(optional = false)
    @Column(name = "fecha_limite", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLimite;
    @Basic(optional = false)
    @Column(name = "usuario_creacion", nullable = false, length = 255)
    private String usuarioCreacion;
    @Basic(optional = false)
    @Column(name = "fecha_creacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "usuario_modificacion", length = 255)
    private String usuarioModificacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String estado;
    @JoinColumn(name = "comprobante_pago_id", referencedColumnName = "comprobante_pago_id")
    @ManyToOne
    private ComprobantePagoEntity comprobantePagoId;
    @JoinColumn(name = "tipo_documento_pago_id", referencedColumnName = "dominio_id")
    @ManyToOne
    private DominioEntity tipoDocumentoPagoId;
    @JoinColumn(name = "entidad_id", referencedColumnName = "entidad_id", nullable = false)
    @ManyToOne(optional = false)
    private EntidadEntity entidadId;

    public DosificacionEntity() {
    }

    public DosificacionEntity(Long dosificacionId) {
        this.dosificacionId = dosificacionId;
    }

    public DosificacionEntity(Long dosificacionId, String nroAutorizacion, long nroInicial, long nroFinal, String nroFactura, Date fechaLimite, String usuarioCreacion, Date fechaCreacion, String estado) {
        this.dosificacionId = dosificacionId;
        this.nroAutorizacion = nroAutorizacion;
        this.nroInicial = nroInicial;
        this.nroFinal = nroFinal;
        this.nroFactura = nroFactura;
        this.fechaLimite = fechaLimite;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }

    public Long getDosificacionId() {
        return dosificacionId;
    }

    public void setDosificacionId(Long dosificacionId) {
        this.dosificacionId = dosificacionId;
    }

    public String getNroAutorizacion() {
        return nroAutorizacion;
    }

    public void setNroAutorizacion(String nroAutorizacion) {
        this.nroAutorizacion = nroAutorizacion;
    }

    public long getNroInicial() {
        return nroInicial;
    }

    public void setNroInicial(long nroInicial) {
        this.nroInicial = nroInicial;
    }

    public long getNroFinal() {
        return nroFinal;
    }

    public void setNroFinal(long nroFinal) {
        this.nroFinal = nroFinal;
    }

    public String getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(String nroFactura) {
        this.nroFactura = nroFactura;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ComprobantePagoEntity getComprobantePagoId() {
        return comprobantePagoId;
    }

    public void setComprobantePagoId(ComprobantePagoEntity comprobantePagoId) {
        this.comprobantePagoId = comprobantePagoId;
    }

    public DominioEntity getTipoDocumentoPagoId() {
        return tipoDocumentoPagoId;
    }

    public void setTipoDocumentoPagoId(DominioEntity tipoDocumentoPagoId) {
        this.tipoDocumentoPagoId = tipoDocumentoPagoId;
    }

    public EntidadEntity getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(EntidadEntity entidadId) {
        this.entidadId = entidadId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dosificacionId != null ? dosificacionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DosificacionEntity)) {
            return false;
        }
        DosificacionEntity other = (DosificacionEntity) object;
        if ((this.dosificacionId == null && other.dosificacionId != null) || (this.dosificacionId != null && !this.dosificacionId.equals(other.dosificacionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.DosificacionEntity[ dosificacionId=" + dosificacionId + " ]";
    }
    
}
