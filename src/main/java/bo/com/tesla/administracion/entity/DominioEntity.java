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
import javax.persistence.CascadeType;
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

/**
 *
 * @author aCallejas
 */
@Entity
@Table(name = "dominios", catalog = "exacta", schema = "tesla")
@NamedQueries({
    @NamedQuery(name = "DominioEntity.findAll", query = "SELECT d FROM DominioEntity d")})
public class DominioEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dominio_id", nullable = false)
    private Long dominioId;
    @Column(length = 60)
    private String dominio;
    @Column(length = 250)
    private String descripcion;
    @Column(length = 10)
    private String abreviatura;
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
    @Column(length = 10)
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "metodoCobroId")
    private List<CobroClienteEntity> cobroClienteEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "motivoCancelacionId")
    private List<CancelacionEntity> cancelacionEntityList;
    @OneToMany(mappedBy = "tipoCancelacionId")
    private List<CancelacionEntity> cancelacionEntityList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDocumentoCobroId")
    private List<DosificacionEntity> dosificacionEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ciudadId")
    private List<PersonaEntity> personaEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDocumentoId")
    private List<PersonaEntity> personaEntityList1;
    @OneToMany(mappedBy = "departamentoId")
    private List<SucursalEntity> sucursalEntityList;
    @OneToMany(mappedBy = "localidadId")
    private List<SucursalEntity> sucursalEntityList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoEntidad")
    private List<EntidadEntity> entidadEntityList;

    public DominioEntity() {
    }

    public DominioEntity(Long dominioId) {
        this.dominioId = dominioId;
    }

    public Long getDominioId() {
        return dominioId;
    }

    public void setDominioId(Long dominioId) {
        this.dominioId = dominioId;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
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

    public List<CobroClienteEntity> getCobroClienteEntityList() {
        return cobroClienteEntityList;
    }

    public void setCobroClienteEntityList(List<CobroClienteEntity> cobroClienteEntityList) {
        this.cobroClienteEntityList = cobroClienteEntityList;
    }

    public List<CancelacionEntity> getCancelacionEntityList() {
        return cancelacionEntityList;
    }

    public void setCancelacionEntityList(List<CancelacionEntity> cancelacionEntityList) {
        this.cancelacionEntityList = cancelacionEntityList;
    }

    public List<CancelacionEntity> getCancelacionEntityList1() {
        return cancelacionEntityList1;
    }

    public void setCancelacionEntityList1(List<CancelacionEntity> cancelacionEntityList1) {
        this.cancelacionEntityList1 = cancelacionEntityList1;
    }

    public List<DosificacionEntity> getDosificacionEntityList() {
        return dosificacionEntityList;
    }

    public void setDosificacionEntityList(List<DosificacionEntity> dosificacionEntityList) {
        this.dosificacionEntityList = dosificacionEntityList;
    }

    public List<PersonaEntity> getPersonaEntityList() {
        return personaEntityList;
    }

    public void setPersonaEntityList(List<PersonaEntity> personaEntityList) {
        this.personaEntityList = personaEntityList;
    }

    public List<PersonaEntity> getPersonaEntityList1() {
        return personaEntityList1;
    }

    public void setPersonaEntityList1(List<PersonaEntity> personaEntityList1) {
        this.personaEntityList1 = personaEntityList1;
    }

    public List<SucursalEntity> getSucursalEntityList() {
        return sucursalEntityList;
    }

    public void setSucursalEntityList(List<SucursalEntity> sucursalEntityList) {
        this.sucursalEntityList = sucursalEntityList;
    }

    public List<SucursalEntity> getSucursalEntityList1() {
        return sucursalEntityList1;
    }

    public void setSucursalEntityList1(List<SucursalEntity> sucursalEntityList1) {
        this.sucursalEntityList1 = sucursalEntityList1;
    }

    public List<EntidadEntity> getEntidadEntityList() {
        return entidadEntityList;
    }

    public void setEntidadEntityList(List<EntidadEntity> entidadEntityList) {
        this.entidadEntityList = entidadEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dominioId != null ? dominioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DominioEntity)) {
            return false;
        }
        DominioEntity other = (DominioEntity) object;
        if ((this.dominioId == null && other.dominioId != null) || (this.dominioId != null && !this.dominioId.equals(other.dominioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.DominioEntity[ dominioId=" + dominioId + " ]";
    }
    
}
