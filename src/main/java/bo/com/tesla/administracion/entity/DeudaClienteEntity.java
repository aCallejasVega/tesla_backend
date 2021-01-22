/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.tesla.administracion.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aCallejas
 */
@Entity
@Table(name = "deudas_clientes", catalog = "exacta", schema = "tesla")

public class DeudaClienteEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "deuda_cliente_id", nullable = false)
    private Long deudaClienteId;
    @Column(name = "nombre_cliente", length = 200)
    private String nombreCliente;
    @Column(name = "nro_documento", length = 15)
    private String nroDocumento;
    @Basic(optional = false)
    @Column(name = "codigo_cliente", nullable = false, length = 15)
    private String codigoCliente;
    @Basic(optional = false)
    @Column(name = "tipo_servicio", nullable = false, length = 300)
    private String tipoServicio;
    @Column(length = 300)
    private String servicio;
    @Basic(optional = false)
    @Column(nullable = false)
    private Character tipo;
    @Basic(optional = false)
    @Column(nullable = false, length = 250)
    private String periodo;
    @Basic(optional = false)
    @Column(nullable = false, length = 250)
    private String concepto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;
    @Column(name = "monto_unitario", precision = 17, scale = 2)
    private BigDecimal montoUnitario;
    @Column(name = "dato_extras", length = 250)
    private String datoExtras;
    @Basic(optional = false)
    @Column(name = "usuario_creacion", nullable = false)
    private long usuarioCreacion;
    @Basic(optional = false)
    @Column(name = "fecha_creacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "tipo_plantilla")
    private Boolean tipoPlantilla;
    @Column(length = 15)
    private String estado;
    @Column(name = "nro_registro")
    private Integer nroRegistro;
    @Column(name = "periodo_cabecera", length = 250)
    private String periodoCabecera;
    @JoinColumn(name = "archivo_id", referencedColumnName = "archivo_id")
    @ManyToOne
    private ArchivoEntity archivoId;

    public DeudaClienteEntity() {
    }

    public DeudaClienteEntity(Long deudaClienteId) {
        this.deudaClienteId = deudaClienteId;
    }

    public DeudaClienteEntity(Long deudaClienteId, String codigoCliente, String tipoServicio, Character tipo, String periodo, String concepto, BigDecimal cantidad, long usuarioCreacion, Date fechaCreacion) {
        this.deudaClienteId = deudaClienteId;
        this.codigoCliente = codigoCliente;
        this.tipoServicio = tipoServicio;
        this.tipo = tipo;
        this.periodo = periodo;
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getDeudaClienteId() {
        return deudaClienteId;
    }

    public void setDeudaClienteId(Long deudaClienteId) {
        this.deudaClienteId = deudaClienteId;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
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

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getMontoUnitario() {
        return montoUnitario;
    }

    public void setMontoUnitario(BigDecimal montoUnitario) {
        this.montoUnitario = montoUnitario;
    }

    public String getDatoExtras() {
        return datoExtras;
    }

    public void setDatoExtras(String datoExtras) {
        this.datoExtras = datoExtras;
    }

    public long getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(long usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Boolean getTipoPlantilla() {
        return tipoPlantilla;
    }

    public void setTipoPlantilla(Boolean tipoPlantilla) {
        this.tipoPlantilla = tipoPlantilla;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getNroRegistro() {
        return nroRegistro;
    }

    public void setNroRegistro(Integer nroRegistro) {
        this.nroRegistro = nroRegistro;
    }

    public String getPeriodoCabecera() {
        return periodoCabecera;
    }

    public void setPeriodoCabecera(String periodoCabecera) {
        this.periodoCabecera = periodoCabecera;
    }

    public ArchivoEntity getArchivoId() {
        return archivoId;
    }

    public void setArchivoId(ArchivoEntity archivoId) {
        this.archivoId = archivoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deudaClienteId != null ? deudaClienteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeudaClienteEntity)) {
            return false;
        }
        DeudaClienteEntity other = (DeudaClienteEntity) object;
        if ((this.deudaClienteId == null && other.deudaClienteId != null) || (this.deudaClienteId != null && !this.deudaClienteId.equals(other.deudaClienteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.DeudaClienteEntity[ deudaClienteId=" + deudaClienteId + " ]";
    }
    
}
