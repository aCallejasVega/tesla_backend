/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.tesla.administracion.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author aCallejas
 */
@Entity
@Table(name = "seg_roles", catalog = "exacta", schema = "tesla")

public class SegRolEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rol_id", nullable = false)
    private Long rolId;
    @Column(length = 255)
    private String rol;
    @Column(length = 255)
    private String descripcion;
    @Column(name = "usuario_creacion", length = 255)
    private String usuarioCreacion;
    @Column(name = "fecha_creacion", length = 255)
    private String fechaCreacion;
    @Column(name = "usuario_modificacion", length = 255)
    private String usuarioModificacion;
    @Column(name = "fecha_modificacion", length = 255)
    private String fechaModificacion;
    @Column(length = 15)
    private String estado;
    @OneToMany(mappedBy = "rolId")
    private List<SegPrivilegioRolEntity> segPrivilegioRolEntityList;
    @OneToMany(mappedBy = "rolId")
    private List<SegUsuarioRolEntity> segUsuarioRolEntityList;

    public SegRolEntity() {
    }

    public SegRolEntity(Long rolId) {
        this.rolId = rolId;
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    

    public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<SegPrivilegioRolEntity> getSegPrivilegioRolEntityList() {
        return segPrivilegioRolEntityList;
    }

    public void setSegPrivilegioRolEntityList(List<SegPrivilegioRolEntity> segPrivilegioRolEntityList) {
        this.segPrivilegioRolEntityList = segPrivilegioRolEntityList;
    }

    public List<SegUsuarioRolEntity> getSegUsuarioRolEntityList() {
        return segUsuarioRolEntityList;
    }

    public void setSegUsuarioRolEntityList(List<SegUsuarioRolEntity> segUsuarioRolEntityList) {
        this.segUsuarioRolEntityList = segUsuarioRolEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolId != null ? rolId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegRolEntity)) {
            return false;
        }
        SegRolEntity other = (SegRolEntity) object;
        if ((this.rolId == null && other.rolId != null) || (this.rolId != null && !this.rolId.equals(other.rolId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.SegRolEntity[ rolId=" + rolId + " ]";
    }
    
}
