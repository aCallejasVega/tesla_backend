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
import javax.persistence.Table;

/**
 *
 * @author aCallejas
 */
@Entity
@Table(name = "log_sistemas", catalog = "exacta", schema = "tesla")

public class LogSistemaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "log_sistema_id", nullable = false)
    private Long logSistemaId;
    @Column(length = 255)
    private String tabla;
    @Column(name = "registro_anterior", length = 255)
    private String registroAnterior;
    @Column(name = "registro_actual", length = 255)
    private String registroActual;
    @Column(name = "usuario_creacion", length = 255)
    private String usuarioCreacion;
    @Column(name = "fecha_creacion", length = 255)
    private String fechaCreacion;

    public LogSistemaEntity() {
    }

    public LogSistemaEntity(Long logSistemaId) {
        this.logSistemaId = logSistemaId;
    }

    public Long getLogSistemaId() {
        return logSistemaId;
    }

    public void setLogSistemaId(Long logSistemaId) {
        this.logSistemaId = logSistemaId;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getRegistroAnterior() {
        return registroAnterior;
    }

    public void setRegistroAnterior(String registroAnterior) {
        this.registroAnterior = registroAnterior;
    }

    public String getRegistroActual() {
        return registroActual;
    }

    public void setRegistroActual(String registroActual) {
        this.registroActual = registroActual;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logSistemaId != null ? logSistemaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogSistemaEntity)) {
            return false;
        }
        LogSistemaEntity other = (LogSistemaEntity) object;
        if ((this.logSistemaId == null && other.logSistemaId != null) || (this.logSistemaId != null && !this.logSistemaId.equals(other.logSistemaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.com.tesla.administracion.entity.LogSistemaEntity[ logSistemaId=" + logSistemaId + " ]";
    }
    
}
