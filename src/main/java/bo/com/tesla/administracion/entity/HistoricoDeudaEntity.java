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
@Table(name = "historicos_deudas", catalog = "exacta", schema = "tesla2")
@NamedQueries({
    @NamedQuery(name = "HistoricoDeudaEntity.findAll", query = "SELECT h FROM HistoricoDeudaEntity h")})
public class HistoricoDeudaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "historico_deuda_id", nullable = false)
    private Long historicoDeudaId;
    @Basic(optional = false)
    @Column(name = "deuda_cliente_id", nullable = false)
    private long deudaClienteId;
    @Basic(optional = false)
    @Column(name = "nro_registro", nullable = false)
    private int nroRegistro;
    @Basic(optional = false)
    @Column(name = "codigo_cliente", nullable = false, length = 15)
    private String codigoCliente;
    @Column(name = "nombre_cliente", length = 200)
    private String nombreCliente;
    @Column(name = "nro_documento", length = 15)
    private String nroDocumento;
    @Basic(optional = false)
    @Column(name = "tipo_servicio", nullable = false, length = 300)
    private String tipoServicio;
    @Basic(optional = false)
    @Column(nullable = false, length = 250)
    private String periodo;
    @Basic(optional = false)
    @Column(nullable = false)
    private Character tipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;
    @Basic(optional = false)
    @Column(nullable = false, length = 250)
    private String concepto;
    @Column(name = "monto_unitario", precision = 17, scale = 2)
    private BigDecimal montoUnitario;
    @Column(name = "dato_extra", length = 250)
    private String datoExtra;
    @Basic(optional = false)
    @Column(name = "tipo_plantilla", nullable = false)
    private boolean tipoPlantilla;
    @Column(name = "periodo_cabecera", length = 250)
    private String periodoCabecera;
    @Basic(optional = false)
    @Column(name = "usuario_creacion", nullable = false)
    private long usuarioCreacion;
    @Basic(optional = false)
    @Column(name = "fecha_creacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "archivo_id", referencedColumnName = "archivo_id", nullable = false)
    @ManyToOne(optional = false)
    private ArchivoEntity archivoId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "historicoDeudaId")
    private List<AccionEntity> accionEntityList;

    public HistoricoDeudaEntity() {
    }

    public HistoricoDeudaEntity(Long historicoDeudaId) {
        this.historicoDeudaId = historicoDeudaId;
    }

    public HistoricoDeudaEntity(Long historicoDeudaId, long deudaClienteId, int nroRegistro, String codigoCliente, String tipoServicio, String periodo, Character tipo, BigDecimal cantidad, String concepto, boolean tipoPlantilla, long usuarioCreacion, Date fechaCreacion) {
        this.historicoDeudaId = historicoDeudaId;
        this.deudaClienteId = deudaClienteId;
        this.nroRegistro = nroRegistro;
        this.codigoCliente = codigoCliente;
        this.tipoServicio = tipoServicio;
        this.periodo = periodo;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.concepto = concepto;
        this.tipoPlantilla = tipoPlantilla;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getHistoricoDeudaId() {
        return historicoDeudaId;
    }

    public void setHistoricoDeudaId(Long historicoDeudaId) {
        this.historicoDeudaId = historicoDeudaId;
    }

    public long getDeudaClienteId() {
        return deudaClienteId;
    }

    public void setDeudaClienteId(long deudaClienteId) {
        this.deudaClienteId = deudaClienteId;
    }

    public int getNroRegistro() {
        return nroRegistro;
    }

    public void setNroRegistro(int nroRegistro) {
        this.nroRegistro = nroRegistro;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
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

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getMontoUnitario() {
        return montoUnitario;
    }

    public void setMontoUnitario(BigDecimal montoUnitario) {
        this.montoUnitario = montoUnitario;
    }

    public String getDatoExtra() {
        return datoExtra;
    }

    public void setDatoExtra(String datoExtra) {
        this.datoExtra = datoExtra;
    }

    public boolean getTipoPlantilla() {
        return tipoPlantilla;
    }

    public void setTipoPlantilla(boolean tipoPlantilla) {
        this.tipoPlantilla = tipoPlantilla;
    }

    public String getPeriodoCabecera() {
        return periodoCabecera;
    }

    public void setPeriodoCabecera(String periodoCabecera) {
        this.periodoCabecera = periodoCabecera;
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

    public ArchivoEntity getArchivoId() {
        return archivoId;
    }

    public void setArchivoId(ArchivoEntity archivoId) {
        this.archivoId = archivoId;
    }

    public List<AccionEntity> getAccionEntityList() {
        return accionEntityList;
    }

    public void setAccionEntityList(List<AccionEntity> accionEntityList) {
        this.accionEntityList = accionEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historicoDeudaId != null ? historicoDeudaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoricoDeudaEntity)) {
            return false;
        }
        HistoricoDeudaEntity other = (HistoricoDeudaEntity) object;
        if ((this.historicoDeudaId == null && other.historicoDeudaId != null) || (this.historicoDeudaId != null && !this.historicoDeudaId.equals(other.historicoDeudaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.HistoricoDeudaEntity[ historicoDeudaId=" + historicoDeudaId + " ]";
    }
    
}