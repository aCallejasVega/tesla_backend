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
@Table(name = "archivos", catalog = "exacta", schema = "tesla")

public class ArchivoEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "archivo_id", nullable = false)
    private Long archivoId;
    @Column(length = 150)
    private String nombre;
    @Column(length = 150)
    private String path;
    @Column(name = "inicio_cargado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicioCargado;
    @Column(name = "fin_cargado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finCargado;
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
    @OneToMany(mappedBy = "archivoId")
    private List<HistoricoDeudaEntity> historicoDeudaEntityList;
    @JoinColumn(name = "entidad_id", referencedColumnName = "entidad_id")
    @ManyToOne
    private EntidadEntity entidadId;
    @OneToMany(mappedBy = "archivoId")
    private List<PagoClienteEntity> pagoClienteEntityList;
    @OneToMany(mappedBy = "archivoId")
    private List<DeudaClienteEntity> deudaClienteEntityList;

    public ArchivoEntity() {
    }

    public ArchivoEntity(Long archivoId) {
        this.archivoId = archivoId;
    }

    public Long getArchivoId() {
        return archivoId;
    }

    public void setArchivoId(Long archivoId) {
        this.archivoId = archivoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getInicioCargado() {
        return inicioCargado;
    }

    public void setInicioCargado(Date inicioCargado) {
        this.inicioCargado = inicioCargado;
    }

    public Date getFinCargado() {
        return finCargado;
    }

    public void setFinCargado(Date finCargado) {
        this.finCargado = finCargado;
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

    public List<HistoricoDeudaEntity> getHistoricoDeudaEntityList() {
        return historicoDeudaEntityList;
    }

    public void setHistoricoDeudaEntityList(List<HistoricoDeudaEntity> historicoDeudaEntityList) {
        this.historicoDeudaEntityList = historicoDeudaEntityList;
    }

    public EntidadEntity getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(EntidadEntity entidadId) {
        this.entidadId = entidadId;
    }

    public List<PagoClienteEntity> getPagoClienteEntityList() {
        return pagoClienteEntityList;
    }

    public void setPagoClienteEntityList(List<PagoClienteEntity> pagoClienteEntityList) {
        this.pagoClienteEntityList = pagoClienteEntityList;
    }

    public List<DeudaClienteEntity> getDeudaClienteEntityList() {
        return deudaClienteEntityList;
    }

    public void setDeudaClienteEntityList(List<DeudaClienteEntity> deudaClienteEntityList) {
        this.deudaClienteEntityList = deudaClienteEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (archivoId != null ? archivoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArchivoEntity)) {
            return false;
        }
        ArchivoEntity other = (ArchivoEntity) object;
        if ((this.archivoId == null && other.archivoId != null) || (this.archivoId != null && !this.archivoId.equals(other.archivoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.ArchivoEntity[ archivoId=" + archivoId + " ]";
    }
    
}
