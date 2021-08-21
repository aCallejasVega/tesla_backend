/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.tesla.administracion.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author aCallejas
 */
@Entity
@Table(name = "end_point_entidades", catalog = "exacta_tesla", schema = "tesla")
public class EndPointEntidadEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "end_point_entidad_id", nullable = false)
    private Long endPointEntidadId;
    @Column(length = 255)
    private String ruta;
    @JoinColumn(name = "entidad_id", referencedColumnName = "entidad_id")
    @ManyToOne
    private EntidadEntity entidadId;

    public EndPointEntidadEntity() {
    }

    public EndPointEntidadEntity(Long endPointEntidadId) {
        this.endPointEntidadId = endPointEntidadId;
    }

    public Long getEndPointEntidadId() {
        return endPointEntidadId;
    }

    public void setEndPointEntidadId(Long endPointEntidadId) {
        this.endPointEntidadId = endPointEntidadId;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public EntidadEntity getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(EntidadEntity entidadId) {
        this.entidadId = entidadId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (endPointEntidadId != null ? endPointEntidadId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EndPointEntidadEntity)) {
            return false;
        }
        EndPointEntidadEntity other = (EndPointEntidadEntity) object;
        if ((this.endPointEntidadId == null && other.endPointEntidadId != null) || (this.endPointEntidadId != null && !this.endPointEntidadId.equals(other.endPointEntidadId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.EndPointEntidadEntity[ endPointEntidadId=" + endPointEntidadId + " ]";
    }
    
}
