/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.tesla.administracion.entity;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "cancelaciones", catalog = "exacta", schema = "tesla")

public class CancelacionEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cancelacion_id", nullable = false)
    private Long cancelacionId;
    @Column(length = 255)
    private String descripcion;
    @Column(name = "fecha_cancelacion", length = 255)
    private String fechaCancelacion;
    @Column(name = "usuario_creacion")
    private BigInteger usuarioCreacion;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "usuario_modificacion")
    private BigInteger usuarioModificacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(length = 15)
    private String estado;
    @JoinColumn(name = "comprobante_pago_id", referencedColumnName = "comprobante_pago_id")
    @ManyToOne
    private ComprobantePagoEntity comprobantePagoId;
    @JoinColumn(name = "motivo_cancelacion_id", referencedColumnName = "dominio_id")
    @ManyToOne
    private DominioEntity motivoCancelacionId;
    @JoinColumn(name = "tipo_cancelacion_id", referencedColumnName = "dominio_id")
    @ManyToOne
    private DominioEntity tipoCancelacionId;

    public CancelacionEntity() {
    }

    public CancelacionEntity(Long cancelacionId) {
        this.cancelacionId = cancelacionId;
    }

    public Long getCancelacionId() {
        return cancelacionId;
    }

    public void setCancelacionId(Long cancelacionId) {
        this.cancelacionId = cancelacionId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(String fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public BigInteger getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(BigInteger usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigInteger getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(BigInteger usuarioModificacion) {
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

    public DominioEntity getMotivoCancelacionId() {
        return motivoCancelacionId;
    }

    public void setMotivoCancelacionId(DominioEntity motivoCancelacionId) {
        this.motivoCancelacionId = motivoCancelacionId;
    }

    public DominioEntity getTipoCancelacionId() {
        return tipoCancelacionId;
    }

    public void setTipoCancelacionId(DominioEntity tipoCancelacionId) {
        this.tipoCancelacionId = tipoCancelacionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cancelacionId != null ? cancelacionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CancelacionEntity)) {
            return false;
        }
        CancelacionEntity other = (CancelacionEntity) object;
        if ((this.cancelacionId == null && other.cancelacionId != null) || (this.cancelacionId != null && !this.cancelacionId.equals(other.cancelacionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.CancelacionEntity[ cancelacionId=" + cancelacionId + " ]";
    }
    
}
