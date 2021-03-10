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
import javax.persistence.*;

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
    @Basic(optional = false)
    @Column(name = "tipo_recaudador_id", nullable = false)
    private Long tipoRecaudadorId;
    @Basic(optional = false)
    @Column(nullable = false, length = 250)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false, length = 250)
    private String direccion;
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    private String telefono;
    @Basic(optional = false)
    @Column(name = "usuario_creacion", nullable = false)
    private Long usuarioCreacion;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recaudador")
    private List<SucursalEntity> sucursalEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recaudador")
    private List<EntidadRecaudadorEntity> entidadRecaudadorEntityList;

    public RecaudadorEntity() {
    }

    public RecaudadorEntity(Long recaudadorId) {
        this.recaudadorId = recaudadorId;
    }

    public RecaudadorEntity(Long recaudadorId, long tipoRecaudadorId, String nombre, String direccion, String telefono, boolean comprobanteEnUno, long usuarioCreacion, Date fechaCreacion, String estado, String transaccion) {
        this.recaudadorId = recaudadorId;
        this.tipoRecaudadorId = tipoRecaudadorId;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.transaccion = transaccion;
    }

    public Long getRecaudadorId() {
        return recaudadorId;
    }

    public void setRecaudadorId(Long recaudadorId) {
        this.recaudadorId = recaudadorId;
    }

    public long getTipoRecaudadorId() {
        return tipoRecaudadorId;
    }

    public void setTipoRecaudadorId(long tipoRecaudadorId) {
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

    public List<SucursalEntity> getSucursalEntityList() {
        return sucursalEntityList;
    }

    public void setSucursalEntityList(List<SucursalEntity> sucursalEntityCollection) {
        this.sucursalEntityList = sucursalEntityCollection;
    }

    public List<EntidadRecaudadorEntity> getEntidadRecaudadorEntityList() {
        return entidadRecaudadorEntityList;
    }

    public void setEntidadRecaudadorEntityList(List<EntidadRecaudadorEntity> entidadRecaudadorEntityCollection) {
        this.entidadRecaudadorEntityList = entidadRecaudadorEntityCollection;
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
        if (!( object instanceof RecaudadorEntity)) {
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
        return "bo.com.tesla.administrador.entity.RecaudadorEntity[ recaudadorId=" + recaudadorId + " ]";
    }
    
}
