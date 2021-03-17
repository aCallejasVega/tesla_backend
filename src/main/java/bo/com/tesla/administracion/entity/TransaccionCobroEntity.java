/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.tesla.administracion.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "transacciones_cobros", catalog = "exacta", schema = "tesla")

public class TransaccionCobroEntity implements Serializable {

	 private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "transaccion_cobro_id", nullable = false)
	    private Long transaccionCobroId;
	    @Column(name = "tipo_servicio", length = 300)
	    private String tipoServicio;
	    @Column(length = 300)
	    private String servicio;
	    @Column(length = 250)
	    private String periodo;
	    @Column(name = "usuario_creacion")
	    private Long usuarioCreacion;
	    @Column(name = "fecha_creacion")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date fechaCreacion;
	    @Column(name = "usuario_modificacion")
	    private Long usuarioModificacion;
	    @Column(name = "fecha_modificacion")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date fechaModificacion;
	    @Column(length = 15)
	    private String estado;
	    @Column(length = 15)
	    private String transaccion;
	    @Column(name = "codigo_cliente", length = 15)
	    private String codigoCliente;
	    @Column(name = "nombre_cliente_pago", length = 200)
	    private String nombreClientePago;
	    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	    @Column(name = "total_deuda", precision = 17, scale = 2)
	    private BigDecimal totalDeuda;
	    @Column(name = "nro_documento_cliente_pago", length = 15)
	    private String nroDocumentoClientePago;
	    @Column(precision = 17, scale = 2)
	    private BigDecimal comision;
	    @JoinColumn(name = "archivo_id", referencedColumnName = "archivo_id")
	    @ManyToOne
	    private ArchivoEntity archivoId;
	    @JoinColumn(name = "entidad_id", referencedColumnName = "entidad_id", nullable = false)
	    @ManyToOne(optional = false)
	    private EntidadEntity entidadId;
	    @JoinColumn(name = "recaudador_id", referencedColumnName = "recaudador_id")
	    @ManyToOne
	    private RecaudadorEntity recaudadorId;
	    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaccionCobroId")
	    private List<DetalleComprobanteCobroEntity> detallesComprobantesCobrosList;

	
	
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

	public BigDecimal getTotalDeuda() {
		return totalDeuda;
	}

	public void setTotalDeuda(BigDecimal totalDeuda) {
		this.totalDeuda = totalDeuda;
	}

	public String getNombreClientePago() {
		return nombreClientePago;
	}

	public void setNombreClientePago(String nombreClientePago) {
		this.nombreClientePago = nombreClientePago;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public EntidadEntity getEntidadId() {
		return entidadId;
	}

	public void setEntidadId(EntidadEntity entidadId) {
		this.entidadId = entidadId;
	}

		

	public String getNroDocumentoClientePago() {
		return nroDocumentoClientePago;
	}

	public void setNroDocumentoClientePago(String nroDocumentoClientePago) {
		this.nroDocumentoClientePago = nroDocumentoClientePago;
	}

	public BigDecimal getComision() {
		return comision;
	}

	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}

	public ArchivoEntity getArchivoId() {
		return archivoId;
	}

	public void setArchivoId(ArchivoEntity archivoId) {
		this.archivoId = archivoId;
	}

	public RecaudadorEntity getRecaudadorId() {
		return recaudadorId;
	}

	public void setRecaudadorId(RecaudadorEntity recaudadorId) {
		this.recaudadorId = recaudadorId;
	}

	public List<DetalleComprobanteCobroEntity> getDetallesComprobantesCobrosList() {
		return detallesComprobantesCobrosList;
	}

	public void setDetallesComprobantesCobrosList(List<DetalleComprobanteCobroEntity> detallesComprobantesCobrosList) {
		this.detallesComprobantesCobrosList = detallesComprobantesCobrosList;
	}

	public List<DetalleComprobanteCobroEntity> getDetalleComprobanteCobroEntityList() {
		return detalleComprobanteCobroEntityList;
	}

	public void setDetalleComprobanteCobroEntityList(
			List<DetalleComprobanteCobroEntity> detalleComprobanteCobroEntityList) {
		this.detalleComprobanteCobroEntityList = detalleComprobanteCobroEntityList;
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
		if ((this.transaccionCobroId == null && other.transaccionCobroId != null)
				|| (this.transaccionCobroId != null && !this.transaccionCobroId.equals(other.transaccionCobroId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "bo.com.tesla.administracion.entity.TransaccionCobroEntity[ transaccionCobroId=" + transaccionCobroId
				+ " ]";
	}

}
