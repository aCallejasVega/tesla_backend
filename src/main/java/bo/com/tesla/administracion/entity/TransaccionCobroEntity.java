/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.tesla.administracion.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "transacciones_cobros", catalog = "exacta", schema = "tesla")

public class TransaccionCobroEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "transaccion_cobro_id")
    private Long transaccionCobroId;
    @Basic(optional = false)
    @Column(name = "tipo_servicio")
    private String tipoServicio;
    @Basic(optional = false)
    @Column(name = "servicio")
    private String servicio;
    @Basic(optional = false)
    @Column(name = "periodo")
    private String periodo;
    @Basic(optional = false)
    @Column(name = "codigo_cliente")
    private String codigoCliente;
    @Basic(optional = false)
    @Column(name = "usuario_creacion")
    private long usuarioCreacion;
    @Basic(optional = false)
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "usuario_modificacion")
    private Long usuarioModificacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @Column(name = "transaccion")
    private String transaccion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "total_deuda")
    private BigDecimal totalDeuda;
    @Basic(optional = false)
    @Column(name = "nombre_cliente_pago")
    private String nombreClientePago;
    @Basic(optional = false)
    @Column(name = "nro_documento_cliente_pago")
    private String nroDocumentoClientePago;
    @JoinColumn(name = "archivo_id", referencedColumnName = "archivo_id")
    @ManyToOne(optional = false)
    private ArchivoEntity archivoId;
    @JoinColumn(name = "entidad_id", referencedColumnName = "entidad_id")
    @ManyToOne(optional = false)
    private EntidadEntity entidadId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaccionCobroId")
    private List<DetalleComprobanteCobroEntity> detalleComprobanteCobroEntityList;

    public TransaccionCobroEntity() {
    }

    public TransaccionCobroEntity(Long transaccionCobroId) {
        this.transaccionCobroId = transaccionCobroId;
    }

    public Long getTransaccionCobroId() {
        return transaccionCobroId;
    }

    public void setTransaccionCobroId(Long transaccionCobroId) {
        this.transaccionCobroId = transaccionCobroId;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Long getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(Long usuarioCreacion) {
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

	public EntidadEntity getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(EntidadEntity entidadId) {
        this.entidadId = entidadId;
    }

    public List<DetalleComprobanteCobroEntity> getDetalleComprobanteCobroEntityList() {
        return detalleComprobanteCobroEntityList;
    }

    public void setDetalleComprobanteCobroEntityList(List<DetalleComprobanteCobroEntity> detalleComprobanteCobroEntityList) {
        this.detalleComprobanteCobroEntityList = detalleComprobanteCobroEntityList;
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public void setUsuarioCreacion(long usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public BigDecimal getTotalDeuda() {
        return totalDeuda;
    }

    public void setTotalDeuda(BigDecimal totalDeuda) {
        this.totalDeuda = totalDeuda;
    }

    public ArchivoEntity getArchivoId() {
        return archivoId;
    }

    public void setArchivoId(ArchivoEntity archivoId) {
        this.archivoId = archivoId;
    }

    public String getNombreClientePago() {
        return nombreClientePago;
    }

    public void setNombreClientePago(String nombreClientePago) {
        this.nombreClientePago = nombreClientePago;
    }

    public String getNroDocumentoClientePago() {
        return nroDocumentoClientePago;
    }

    public void setNroDocumentoClientePago(String nroDocumentoClientePago) {
        this.nroDocumentoClientePago = nroDocumentoClientePago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transaccionCobroId != null ? transaccionCobroId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransaccionCobroEntity)) {
            return false;
        }
        TransaccionCobroEntity other = (TransaccionCobroEntity) object;
        if ((this.transaccionCobroId == null && other.transaccionCobroId != null) || (this.transaccionCobroId != null && !this.transaccionCobroId.equals(other.transaccionCobroId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.TransaccionCobroEntity[ transaccionCobroId=" + transaccionCobroId + " ]";
    }
    
}
