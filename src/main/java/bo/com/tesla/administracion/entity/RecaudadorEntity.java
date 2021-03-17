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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author aCallejas
 */
@Entity
@Table(name = "recaudadores", catalog = "exacta", schema = "tesla")
@NamedQueries({
    @NamedQuery(name = "RecaudadorEntity.findAll", query = "SELECT r FROM RecaudadorEntity r")})
public class RecaudadorEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "recaudador_id", nullable = false)
    private Long recaudadorId;
    /*@Column(name = "empleado_id")
    private BigInteger empleadoId;*/
    @Column(name = "tipo_recaudador_id")
    private BigInteger tipoRecaudadorId;
    @Column(length = 250)
    private String nombre;
    @Column(length = 250)
    private String direccion;
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    private String telefono;
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
    @JsonIgnore
    @OneToMany(mappedBy = "recaudador")
    private List<EntidadRecaudadorEntity> entidadRecaudadorEntityList;
    @OneToMany(mappedBy = "recaudadorId")
    @JsonIgnore
    private List<SucursalEntity> sucursalEntityList;
    
    


    public RecaudadorEntity() {
    }

    public RecaudadorEntity(Long recaudadorId) {
        this.recaudadorId = recaudadorId;
    }

    public RecaudadorEntity(Long recaudadorId, String telefono) {
        this.recaudadorId = recaudadorId;
        this.telefono = telefono;
    }

    public Long getRecaudadorId() {
        return recaudadorId;
    }

    public void setRecaudadorId(Long recaudadorId) {
        this.recaudadorId = recaudadorId;
    }
/*
    public BigInteger getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(BigInteger empleadoId) {
        this.empleadoId = empleadoId;
    }
*/
    public BigInteger getTipoRecaudadorId() {
        return tipoRecaudadorId;
    }

    public void setTipoRecaudadorId(BigInteger tipoRecaudadorId) {
        this.tipoRecaudadorId = tipoRecaudadorId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
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

    public List<EntidadRecaudadorEntity> getEntidadRecaudadorEntityList() {
        return entidadRecaudadorEntityList;
    }

    public void setEntidadRecaudadorEntityList(List<EntidadRecaudadorEntity> entidadRecaudadorEntityList) {
        this.entidadRecaudadorEntityList = entidadRecaudadorEntityList;
    }

    public List<SucursalEntity> getSucursalEntityList() {
        return sucursalEntityList;
    }

    public void setSucursalEntityList(List<SucursalEntity> sucursalEntityList) {
        this.sucursalEntityList = sucursalEntityList;
    }

    
    
    
    
   

    

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (recaudadorId != null ? recaudadorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecaudadorEntity)) {
            return false;
        }
        RecaudadorEntity other = (RecaudadorEntity) object;
        if ((this.recaudadorId == null && other.recaudadorId != null) || (this.recaudadorId != null && !this.recaudadorId.equals(other.recaudadorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.RecaudadorEntity[ recaudadorId=" + recaudadorId + " ]";
    }
    
}
