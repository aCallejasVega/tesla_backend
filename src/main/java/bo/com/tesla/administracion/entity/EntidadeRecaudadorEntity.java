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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author aCallejas
 */
@Entity
@Table(name = "entidades_recaudadores", catalog = "exacta", schema = "tesla2")
@NamedQueries({
    @NamedQuery(name = "EntidadeRecaudadorEntity.findAll", query = "SELECT e FROM EntidadeRecaudadorEntity e")})
public class EntidadeRecaudadorEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "entidad_recaudador_id", nullable = false)
    private Long entidadRecaudadorId;
    @JoinColumn(name = "entidad_id", referencedColumnName = "entidad_id")
    @ManyToOne
    private EntidadEntity entidadId;
    @JoinColumn(name = "recaudador_id", referencedColumnName = "recaudador_id")
    @ManyToOne
    private RecaudadorEntity recaudadorId;

    public EntidadeRecaudadorEntity() {
    }

    public EntidadeRecaudadorEntity(Long entidadRecaudadorId) {
        this.entidadRecaudadorId = entidadRecaudadorId;
    }

    public Long getEntidadRecaudadorId() {
        return entidadRecaudadorId;
    }

    public void setEntidadRecaudadorId(Long entidadRecaudadorId) {
        this.entidadRecaudadorId = entidadRecaudadorId;
    }

    public EntidadEntity getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(EntidadEntity entidadId) {
        this.entidadId = entidadId;
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
        hash += (entidadRecaudadorId != null ? entidadRecaudadorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntidadeRecaudadorEntity)) {
            return false;
        }
        EntidadeRecaudadorEntity other = (EntidadeRecaudadorEntity) object;
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
