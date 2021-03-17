/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.tesla.administracion.entity;

import javafx.scene.Parent;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author aCallejas
 */
@Entity
@Table(name = "entidades_recaudadores", catalog = "exacta", schema = "tesla")
@NamedQueries({
    @NamedQuery(name = "EntidadeRecaudadorEntity.findAll", query = "SELECT e FROM EntidadRecaudadorEntity e")})
public class EntidadRecaudadorEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "entidad_recaudador_id", nullable = false)
    private Long entidadRecaudadorId;

    @JoinColumn(name = "entidad_id", referencedColumnName = "entidad_id")
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = EntidadEntity.class)
    private EntidadEntity entidad;

    @JoinColumn(name = "recaudador_id", referencedColumnName = "recaudador_id")
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = RecaudadorEntity.class)
    private RecaudadorEntity recaudador;

    @Column(name = "usuario_creacion")
    private Long usuarioCreacion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "usuario_modificacion")
    private Long usuarioModificacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Basic(optional = false)
    @Column(nullable = false, length = 15)
    private String estado;

    @Basic(optional = false)
    @Column(nullable = false, length = 15)
    private String transaccion;

    public EntidadRecaudadorEntity() {
    }

    public EntidadRecaudadorEntity(Long entidadRecaudadorId) {
        this.entidadRecaudadorId = entidadRecaudadorId;
    }

    public Long getEntidadRecaudadorId() {
        return entidadRecaudadorId;
    }

    public void setEntidadRecaudadorId(Long entidadRecaudadorId) {
        this.entidadRecaudadorId = entidadRecaudadorId;
    }

    public EntidadEntity getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadEntity entidadId) {
        this.entidad = entidadId;
    }

    public RecaudadorEntity getRecaudador() {
        return recaudador;
    }

    public void setRecaudador(RecaudadorEntity recaudadorId) {
        this.recaudador = recaudadorId;
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

    public Long getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(Long usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entidadRecaudadorId != null ? entidadRecaudadorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntidadRecaudadorEntity)) {
            return false;
        }
        EntidadRecaudadorEntity other = (EntidadRecaudadorEntity) object;
        if ((this.entidadRecaudadorId == null && other.entidadRecaudadorId != null) || (this.entidadRecaudadorId != null && !this.entidadRecaudadorId.equals(other.entidadRecaudadorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.EntidadeRecaudadorEntity[ entidadRecaudadorId=" + entidadRecaudadorId + " ]";
    }
    
}
