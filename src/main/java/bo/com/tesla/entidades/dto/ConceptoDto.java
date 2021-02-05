package bo.com.tesla.entidades.dto;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import bo.com.tesla.administracion.entity.ArchivoEntity;

public class ConceptoDto {

	
	

	
	public Integer nroRegistro;
	public String nombreCliente;	
	public String nroDocumento;
	public String direccion;
	public String telefono;
	public String nit;
	public Character tipo;
	public String concepto;
	public BigDecimal montoUnitario;
	public BigDecimal cantidad;
	public BigDecimal subTotal;
	public String datoExtras;
	public Boolean tipoComprobante;
	public String periodoCabecera;
    public Long archivoId;
	
	
	
	
	
	public ConceptoDto(Integer nroRegistro, String nombreCliente, String nroDocumento, String direccion,
			String telefono, String nit, Character tipo, String concepto, BigDecimal montoUnitario, BigDecimal cantidad,
			BigDecimal subTotal, String datoExtras, Boolean tipoComprobante, String periodoCabecera) {
		
		this.nroRegistro = nroRegistro;
		this.nombreCliente = nombreCliente;
		this.nroDocumento = nroDocumento;
		this.direccion = direccion;
		this.telefono = telefono;
		this.nit = nit;
		this.tipo = tipo;
		this.concepto = concepto;
		this.montoUnitario = montoUnitario;
		this.cantidad = cantidad;
		this.subTotal = subTotal;
		this.datoExtras = datoExtras;
		this.tipoComprobante = tipoComprobante;
		this.periodoCabecera = periodoCabecera;
	}
	
	
	
	
	
	
	

	
}
