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
@Table(name = "empleados", catalog = "exacta", schema = "tesla2")
@NamedQueries({
    @NamedQuery(name = "EmpleadoEntity.findAll", query = "SELECT e FROM EmpleadoEntity e")})
public class EmpleadoEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "empleado_id", nullable = false)
    private Long empleadoId;
    @JoinColumn(name = "entidad_id", referencedColumnName = "entidad_id")
    @ManyToOne
    private EntidadEntity entidadId;
    @JoinColumn(name = "persona_id", referencedColumnName = "persona_id")
    @ManyToOne
    private PersonaEntity personaId;
    @JoinColumn(name = "sucursal_id", referencedColumnName = "sucursal_id")
    @ManyToOne
    private SucursalEntity sucursalId;

    public EmpleadoEntity() {
    }

    public EmpleadoEntity(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public EntidadEntity getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(EntidadEntity entidadId) {
        this.entidadId = entidadId;
    }

    public PersonaEntity getPersonaId() {
        return personaId;
    }

    public void setPersonaId(PersonaEntity personaId) {
        this.personaId = personaId;
    }

    public SucursalEntity getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(SucursalEntity sucursalId) {
        this.sucursalId = sucursalId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empleadoId != null ? empleadoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoEntity)) {
            return false;
        }
        EmpleadoEntity other = (EmpleadoEntity) object;
        if ((this.empleadoId == null && other.empleadoId != null) || (this.empleadoId != null && !this.empleadoId.equals(other.empleadoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.EmpleadoEntity[ empleadoId=" + empleadoId + " ]";
    }
    
}
