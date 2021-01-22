/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.tesla.administracion.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aCallejas
 */
@Entity
@Table(name = "transacciones_pagos", catalog = "exacta", schema = "tesla")

public class TransaccionPagoEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "transaccion_pago_id", nullable = false)
    private Long transaccionPagoId;
    @Column(name = "tipo_servicio", length = 300)
    private String tipoServicio;
    @Column(length = 300)
    private String servicio;
    @Column(length = 250)
    private String periodo;
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
    @JoinColumn(name = "entidad_id", referencedColumnName = "entidad_id")
    @ManyToOne
    private EntidadEntity entidadId;
    @OneToMany(mappedBy = "transaccionPagoId")
    private List<DetalleComprobantePagoEntity> detalleComprobantePagoEntityList;

    public TransaccionPagoEntity() {
    }

    public TransaccionPagoEntity(Long transaccionPagoId) {
        this.transaccionPagoId = transaccionPagoId;
    }

    public Long getTransaccionPagoId() {
        return transaccionPagoId;
    }

    public void setTransaccionPagoId(Long transaccionPagoId) {
        this.transaccionPagoId = transaccionPagoId;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
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

    public EntidadEntity getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(EntidadEntity entidadId) {
        this.entidadId = entidadId;
    }

    public List<DetalleComprobantePagoEntity> getDetalleComprobantePagoEntityList() {
        return detalleComprobantePagoEntityList;
    }

    public void setDetalleComprobantePagoEntityList(List<DetalleComprobantePagoEntity> detalleComprobantePagoEntityList) {
        this.detalleComprobantePagoEntityList = detalleComprobantePagoEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transaccionPagoId != null ? transaccionPagoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransaccionPagoEntity)) {
            return false;
        }
        TransaccionPagoEntity other = (TransaccionPagoEntity) object;
        if ((this.transaccionPagoId == null && other.transaccionPagoId != null) || (this.transaccionPagoId != null && !this.transaccionPagoId.equals(other.transaccionPagoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.TransaccionPagoEntity[ transaccionPagoId=" + transaccionPagoId + " ]";
    }
    
}
