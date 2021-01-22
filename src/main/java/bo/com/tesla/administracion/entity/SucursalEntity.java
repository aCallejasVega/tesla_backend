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
@Table(name = "sucursales", catalog = "exacta", schema = "tesla")

public class SucursalEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sucursal_id", nullable = false)
    private Long sucursalId;
    @Column(length = 250)
    private String direccion;
    private Integer telefono;
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
    @OneToMany(mappedBy = "sucursalId")
    private List<EmpleadoEntity> empleadoEntityList;
    @JoinColumn(name = "departamento_id", referencedColumnName = "dominio_id")
    @ManyToOne
    private DominioEntity departamentoId;
    @JoinColumn(name = "localidad_id", referencedColumnName = "dominio_id")
    @ManyToOne
    private DominioEntity localidadId;
    @JoinColumn(name = "recaudador_id", referencedColumnName = "recaudador_id")
    @ManyToOne
    private RecaudadorEntity recaudadorId;

    public SucursalEntity() {
    }

    public SucursalEntity(Long sucursalId) {
        this.sucursalId = sucursalId;
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

    public List<EmpleadoEntity> getEmpleadoEntityList() {
        return empleadoEntityList;
    }

    public void setEmpleadoEntityList(List<EmpleadoEntity> empleadoEntityList) {
        this.empleadoEntityList = empleadoEntityList;
    }

    public DominioEntity getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(DominioEntity departamentoId) {
        this.departamentoId = departamentoId;
    }

    public DominioEntity getLocalidadId() {
        return localidadId;
    }

    public void setLocalidadId(DominioEntity localidadId) {
        this.localidadId = localidadId;
    }

    public RecaudadorEntity getRecaudadorId() {
        return recaudadorId;
    }

    public void setRecaudadorId(RecaudadorEntity recaudadorId) {
        this.recaudadorId = recaudadorId;
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
        return "bo.com.tesla.administracion.entity.SucursalEntity[ sucursalId=" + sucursalId + " ]";
    }
    
}
