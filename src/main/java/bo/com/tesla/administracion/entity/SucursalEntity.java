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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aCallejas
 */
@Entity
@Table(name = "sucursales", catalog = "exacta", schema = "tesla")
@NamedQueries({
    @NamedQuery(name = "SucursalEntity.findAll", query = "SELECT s FROM SucursalEntity s")})
public class SucursalEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sucursal_id", nullable = false)
    private Long sucursalId;
    @Basic(optional = false)
    @Column(nullable = false, length = 250)
    private String direccion;
    private Integer telefono;
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
    @Basic(optional = false)
    @Column(nullable = false, length = 15)
    private String estado;
    @Basic(optional = false)
    @Column(nullable = false, length = 15)
    private String transaccion;
    @JoinColumn(name = "departamento_id", referencedColumnName = "dominio_id", nullable = false)
    @ManyToOne(optional = false)
    private DominioEntity departamento;
    @JoinColumn(name = "localidad_id", referencedColumnName = "dominio_id", nullable = false)
    @ManyToOne(optional = false)
    private DominioEntity localidad;
    @JoinColumn(name = "recaudador_id", referencedColumnName = "recaudador_id", nullable = false)
    @ManyToOne(optional = false)
    private RecaudadorEntity recaudador;

    public SucursalEntity() {
    }

    public SucursalEntity(Long sucursalId) {
        this.sucursalId = sucursalId;
    }

    public SucursalEntity(Long sucursalId, String direccion, long usuarioCreacion, Date fechaCreacion, String estado, String transaccion) {
        this.sucursalId = sucursalId;
        this.direccion = direccion;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.transaccion = transaccion;
    }

    public Long getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Long sucursalId) {
        this.sucursalId = sucursalId;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
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

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public DominioEntity getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DominioEntity departamentoId) {
        this.departamento = departamentoId;
    }

    public DominioEntity getLocalidad() {
        return localidad;
    }

    public void setLocalidad(DominioEntity localidadId) {
        this.localidad = localidadId;
    }

    public RecaudadorEntity getRecaudador() {
        return recaudador;
    }

    public void setRecaudador(RecaudadorEntity recaudadorId) {
        this.recaudador = recaudadorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sucursalId != null ? sucursalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SucursalEntity)) {
            return false;
        }
        SucursalEntity other = (SucursalEntity) object;
        if ((this.sucursalId == null && other.sucursalId != null) || (this.sucursalId != null && !this.sucursalId.equals(other.sucursalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administrador.entity.SucursalEntity[ sucursalId=" + sucursalId + " ]";
    }
}
