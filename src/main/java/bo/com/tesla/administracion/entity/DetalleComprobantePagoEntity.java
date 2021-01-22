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
@Table(name = "detalles_comprobantes_pagos", catalog = "exacta", schema = "tesla")

public class DetalleComprobantePagoEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "detalle_comprobante_pago_id", nullable = false)
    private Long detalleComprobantePagoId;
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
    @Column(length = 255)
    private String estado;
    @JoinColumn(name = "comprobante_pago_id", referencedColumnName = "comprobante_pago_id")
    @ManyToOne
    private ComprobantePagoEntity comprobantePagoId;
    @JoinColumn(name = "pago_cliente_id", referencedColumnName = "pago_cliente_id")
    @ManyToOne
    private PagoClienteEntity pagoClienteId;
    @JoinColumn(name = "transaccion_pago_id", referencedColumnName = "transaccion_pago_id")
    @ManyToOne
    private TransaccionPagoEntity transaccionPagoId;

    public DetalleComprobantePagoEntity() {
    }

    public DetalleComprobantePagoEntity(Long detalleComprobantePagoId) {
        this.detalleComprobantePagoId = detalleComprobantePagoId;
    }

    public Long getDetalleComprobantePagoId() {
        return detalleComprobantePagoId;
    }

    public void setDetalleComprobantePagoId(Long detalleComprobantePagoId) {
        this.detalleComprobantePagoId = detalleComprobantePagoId;
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

    public PagoClienteEntity getPagoClienteId() {
        return pagoClienteId;
    }

    public void setPagoClienteId(PagoClienteEntity pagoClienteId) {
        this.pagoClienteId = pagoClienteId;
    }

    public TransaccionPagoEntity getTransaccionPagoId() {
        return transaccionPagoId;
    }

    public void setTransaccionPagoId(TransaccionPagoEntity transaccionPagoId) {
        this.transaccionPagoId = transaccionPagoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detalleComprobantePagoId != null ? detalleComprobantePagoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleComprobantePagoEntity)) {
            return false;
        }
        DetalleComprobantePagoEntity other = (DetalleComprobantePagoEntity) object;
        if ((this.detalleComprobantePagoId == null && other.detalleComprobantePagoId != null) || (this.detalleComprobantePagoId != null && !this.detalleComprobantePagoId.equals(other.detalleComprobantePagoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.DetalleComprobantePagoEntity[ detalleComprobantePagoId=" + detalleComprobantePagoId + " ]";
    }
    
}
