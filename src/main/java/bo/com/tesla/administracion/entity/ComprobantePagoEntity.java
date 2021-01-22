/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.tesla.administracion.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aCallejas
 */
@Entity
@Table(name = "comprobantes_pagos", catalog = "exacta", schema = "tesla")

public class ComprobantePagoEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "comprobante_pago_id", nullable = false)
    private Long comprobantePagoId;
    @Basic(optional = false)
    @Column(name = "entidad_id", nullable = false)
    private int entidadId;
    @Basic(optional = false)
    @Column(name = "sucursal_id", nullable = false)
    private long sucursalId;
    @Column(name = "dosificacion_id")
    private BigInteger dosificacionId;
    @Basic(optional = false)
    @Column(name = "nro_factura", nullable = false)
    private long nroFactura;
    @Basic(optional = false)
    @Column(name = "fecha_factura", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFactura;
    @Column(name = "nit_cliente", length = 255)
    private String nitCliente;
    @Basic(optional = false)
    @Column(name = "nombre_cliente", nullable = false, length = 255)
    private String nombreCliente;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "monto_total", nullable = false, precision = 17, scale = 2)
    private BigDecimal montoTotal;
    @Column(name = "codigo_control", length = 15)
    private String codigoControl;
    @Basic(optional = false)
    @Column(name = "usuario_creacion", nullable = false)
    private long usuarioCreacion;
    @Basic(optional = false)
    @Column(name = "fecha_creacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "usuario_modificacion")
    private BigInteger usuarioModificacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(length = 15)
    private String estado;
    @OneToMany(mappedBy = "comprobantePagoId")
    private List<DetalleComprobantePagoEntity> detalleComprobantePagoEntityList;
    @OneToMany(mappedBy = "comprobantePagoId")
    private List<CancelacionEntity> cancelacionEntityList;
    @OneToMany(mappedBy = "comprobantePagoId")
    private List<DosificacionEntity> dosificacionEntityList;
    @OneToMany(mappedBy = "comprobantePagoId")
    private List<PlantillaEntity> plantillaEntityList;

    public ComprobantePagoEntity() {
    }

    public ComprobantePagoEntity(Long comprobantePagoId) {
        this.comprobantePagoId = comprobantePagoId;
    }

    public ComprobantePagoEntity(Long comprobantePagoId, int entidadId, long sucursalId, long nroFactura, Date fechaFactura, String nombreCliente, BigDecimal montoTotal, long usuarioCreacion, Date fechaCreacion) {
        this.comprobantePagoId = comprobantePagoId;
        this.entidadId = entidadId;
        this.sucursalId = sucursalId;
        this.nroFactura = nroFactura;
        this.fechaFactura = fechaFactura;
        this.nombreCliente = nombreCliente;
        this.montoTotal = montoTotal;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getComprobantePagoId() {
        return comprobantePagoId;
    }

    public void setComprobantePagoId(Long comprobantePagoId) {
        this.comprobantePagoId = comprobantePagoId;
    }

    public int getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(int entidadId) {
        this.entidadId = entidadId;
    }

    public long getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(long sucursalId) {
        this.sucursalId = sucursalId;
    }

    public BigInteger getDosificacionId() {
        return dosificacionId;
    }

    public void setDosificacionId(BigInteger dosificacionId) {
        this.dosificacionId = dosificacionId;
    }

    public long getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(long nroFactura) {
        this.nroFactura = nroFactura;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getCodigoControl() {
        return codigoControl;
    }

    public void setCodigoControl(String codigoControl) {
        this.codigoControl = codigoControl;
    }

    public long getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(long usuarioCreacion) {
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

    public List<DetalleComprobantePagoEntity> getDetalleComprobantePagoEntityList() {
        return detalleComprobantePagoEntityList;
    }

    public void setDetalleComprobantePagoEntityList(List<DetalleComprobantePagoEntity> detalleComprobantePagoEntityList) {
        this.detalleComprobantePagoEntityList = detalleComprobantePagoEntityList;
    }

    public List<CancelacionEntity> getCancelacionEntityList() {
        return cancelacionEntityList;
    }

    public void setCancelacionEntityList(List<CancelacionEntity> cancelacionEntityList) {
        this.cancelacionEntityList = cancelacionEntityList;
    }

    public List<DosificacionEntity> getDosificacionEntityList() {
        return dosificacionEntityList;
    }

    public void setDosificacionEntityList(List<DosificacionEntity> dosificacionEntityList) {
        this.dosificacionEntityList = dosificacionEntityList;
    }

    public List<PlantillaEntity> getPlantillaEntityList() {
        return plantillaEntityList;
    }

    public void setPlantillaEntityList(List<PlantillaEntity> plantillaEntityList) {
        this.plantillaEntityList = plantillaEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comprobantePagoId != null ? comprobantePagoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComprobantePagoEntity)) {
            return false;
        }
        ComprobantePagoEntity other = (ComprobantePagoEntity) object;
        if ((this.comprobantePagoId == null && other.comprobantePagoId != null) || (this.comprobantePagoId != null && !this.comprobantePagoId.equals(other.comprobantePagoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.ComprobantePagoEntity[ comprobantePagoId=" + comprobantePagoId + " ]";
    }
    
}
