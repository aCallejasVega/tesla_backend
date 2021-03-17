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
    private long usuarioCreacion;
    @Basic(optional = false)
    @Column(name = "fecha_creacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "usuario_modificacion")
    private long usuarioModificacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Basic(optional = false)
    @Column(nullable = false, length = 15)
    private String estado;
    @Basic(optional = false)
    @Column(nullable = false, length = 15)
    private String transaccion;
    @JoinColumn(name = "tipo_recaudador_id", referencedColumnName = "dominio_id", nullable = false)
    @ManyToOne(optional = false)
    private DominioEntity tipoRecaudador;

    @OneToMany(mappedBy = "recaudador", fetch= FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<EntidadRecaudadorEntity> entidadRecaudadorEntityList;


    public RecaudadorEntity() {
    }

    public RecaudadorEntity(Long recaudadorId) {
        this.recaudadorId = recaudadorId;
    }

    public RecaudadorEntity(Long recaudadorId, String nombre, String direccion, String telefono, long usuarioCreacion, Date fechaCreacion, String estado, String transaccion) {
        this.recaudadorId = recaudadorId;
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

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public DominioEntity getTipoRecaudador() {
        return tipoRecaudador;
    }

    public void setTipoRecaudador(DominioEntity tipoRecaudadorId) {
        this.tipoRecaudador = tipoRecaudadorId;
    }

    public List<EntidadRecaudadorEntity> getEntidadRecaudadorEntityList() {
        return entidadRecaudadorEntityList;
    }

    public void setEntidadRecaudadorEntityList(List<EntidadRecaudadorEntity> entidadRecaudadorEntityList) {
        this.entidadRecaudadorEntityList = entidadRecaudadorEntityList;
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
