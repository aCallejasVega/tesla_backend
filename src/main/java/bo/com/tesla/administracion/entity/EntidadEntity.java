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
@Table(name = "entidades", catalog = "exacta", schema = "tesla")

public class EntidadEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "entidad_id", nullable = false)
    private Long entidadId;
    @Basic(optional = false)
    @Column(nullable = false, length = 200)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "nombre_comercial", nullable = false, length = 200)
    private String nombreComercial;
    @Basic(optional = false)
    @Column(nullable = false, length = 200)
    private String direccion;
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    private String telefono;
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    private String nit;
    @Column(name = "llave_dosificacion", length = 255)
    private String llaveDosificacion;
    @Basic(optional = false)
    @Column(name = "fecha_creacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @Column(name = "usuario_creacion", nullable = false)
    private long usuarioCreacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "usuario_modificacion")
    private BigInteger usuarioModificacion;
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    private String estado;
    @OneToMany(mappedBy = "entidadId")
    private List<TransaccionPagoEntity> transaccionPagoEntityList;
    @OneToMany(mappedBy = "entidadId")
    private List<TiposTransaccionEntity> tiposTransaccionEntityList;
    @OneToMany(mappedBy = "entidadId")
    private List<ArchivoEntity> archivoEntityList;
    @OneToMany(mappedBy = "entidadId")
    private List<EntidadRecaudadorEntity> entidadRecaudadorEntityList;
    @OneToMany(mappedBy = "entidadId")
    private List<EmpleadoEntity> empleadoEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadId")
    private List<DosificacionEntity> dosificacionEntityList;
    @JoinColumn(name = "tipo_entidad_id", referencedColumnName = "dominio_id", nullable = false)
    @ManyToOne(optional = false)
    private DominioEntity tipoEntidadId;
    @OneToMany(mappedBy = "entidadId")
    private List<PlantillaEntity> plantillaEntityList;

    public EntidadEntity() {
    }

    public EntidadEntity(Long entidadId) {
        this.entidadId = entidadId;
    }

    public EntidadEntity(Long entidadId, String nombre, String nombreComercial, String direccion, String telefono, String nit, Date fechaCreacion, long usuarioCreacion, String estado) {
        this.entidadId = entidadId;
        this.nombre = nombre;
        this.nombreComercial = nombreComercial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nit = nit;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.estado = estado;
    }

    public Long getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(Long entidadId) {
        this.entidadId = entidadId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
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

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getLlaveDosificacion() {
        return llaveDosificacion;
    }

    public void setLlaveDosificacion(String llaveDosificacion) {
        this.llaveDosificacion = llaveDosificacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public long getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(long usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public BigInteger getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(BigInteger usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<TransaccionPagoEntity> getTransaccionPagoEntityList() {
        return transaccionPagoEntityList;
    }

    public void setTransaccionPagoEntityList(List<TransaccionPagoEntity> transaccionPagoEntityList) {
        this.transaccionPagoEntityList = transaccionPagoEntityList;
    }

    public List<TiposTransaccionEntity> getTiposTransaccionEntityList() {
        return tiposTransaccionEntityList;
    }

    public void setTiposTransaccionEntityList(List<TiposTransaccionEntity> tiposTransaccionEntityList) {
        this.tiposTransaccionEntityList = tiposTransaccionEntityList;
    }

    public List<ArchivoEntity> getArchivoEntityList() {
        return archivoEntityList;
    }

    public void setArchivoEntityList(List<ArchivoEntity> archivoEntityList) {
        this.archivoEntityList = archivoEntityList;
    }

    public List<EntidadRecaudadorEntity> getEntidadRecaudadorEntityList() {
        return entidadRecaudadorEntityList;
    }

    public void setEntidadRecaudadorEntityList(List<EntidadRecaudadorEntity> entidadRecaudadorEntityList) {
        this.entidadRecaudadorEntityList = entidadRecaudadorEntityList;
    }

    public List<EmpleadoEntity> getEmpleadoEntityList() {
        return empleadoEntityList;
    }

    public void setEmpleadoEntityList(List<EmpleadoEntity> empleadoEntityList) {
        this.empleadoEntityList = empleadoEntityList;
    }

    public List<DosificacionEntity> getDosificacionEntityList() {
        return dosificacionEntityList;
    }

    public void setDosificacionEntityList(List<DosificacionEntity> dosificacionEntityList) {
        this.dosificacionEntityList = dosificacionEntityList;
    }

    public DominioEntity getTipoEntidadId() {
        return tipoEntidadId;
    }

    public void setTipoEntidadId(DominioEntity tipoEntidadId) {
        this.tipoEntidadId = tipoEntidadId;
    }

    public List<PlantillaEntity> getPlantillaEntityList() {
        return plantillaEntityList;
    }

    public void setPlantillaEntityList(List<PlantillaEntity> plantillaEntityList) {
        this.plantillaEntityList = plantillaEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entidadId != null ? entidadId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntidadEntity)) {
            return false;
        }
        EntidadEntity other = (EntidadEntity) object;
        if ((this.entidadId == null && other.entidadId != null) || (this.entidadId != null && !this.entidadId.equals(other.entidadId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.EntidadEntity[ entidadId=" + entidadId + " ]";
    }
    
}
