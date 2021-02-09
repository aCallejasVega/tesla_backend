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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aCallejas
 */
@Entity
@Table(name = "transacciones_cobros", catalog = "exacta", schema = "tesla2")
@NamedQueries({
    @NamedQuery(name = "TransaccionCobroEntity.findAll", query = "SELECT t FROM TransaccionCobroEntity t")})
public class TransaccionCobroEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "transaccion_cobro_id", nullable = false)
    private Long transaccionCobroId;
    @Column(name = "tipo_servicio", length = 300)
    private String tipoServicio;
    @Column(length = 300)
    private String servicio;
    @Column(length = 250)
    private String periodo;
    @Column(name = "usuario_creacion")
    private Long usuarioCreacion;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "usuario_modificacion")
    private Long usuarioModificacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(length = 15)
    private String estado;
    @JoinColumn(name = "entidad_id", referencedColumnName = "entidad_id")
    @ManyToOne
    private EntidadEntity entidadId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaccionCobroId")
    private List<DetalleComprobanteCobroEntity> detalleComprobanteCobroEntityList;

    @Column(length = 15)
    private String transaccion;

    public TransaccionCobroEntity() {
    }

    public TransaccionCobroEntity(Long transaccionCobroId) {
        this.transaccionCobroId = transaccionCobroId;
    }

    public Long getTransaccionCobroId() {
        return transaccionCobroId;
    }

    public void setTransaccionCobroId(Long transaccionCobroId) {
        this.transaccionCobroId = transaccionCobroId;
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

    public Long getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(Long usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(Long usuarioModificacion) {
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

    public List<DetalleComprobanteCobroEntity> getDetalleComprobanteCobroEntityList() {
        return detalleComprobanteCobroEntityList;
    }

    public void setDetalleComprobanteCobroEntityList(List<DetalleComprobanteCobroEntity> detalleComprobanteCobroEntityList) {
        this.detalleComprobanteCobroEntityList = detalleComprobanteCobroEntityList;
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transaccionCobroId != null ? transaccionCobroId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransaccionCobroEntity)) {
            return false;
        }
        TransaccionCobroEntity other = (TransaccionCobroEntity) object;
        if ((this.transaccionCobroId == null && other.transaccionCobroId != null) || (this.transaccionCobroId != null && !this.transaccionCobroId.equals(other.transaccionCobroId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.TransaccionCobroEntity[ transaccionCobroId=" + transaccionCobroId + " ]";
    }
    
}
