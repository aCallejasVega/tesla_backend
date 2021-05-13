package bo.com.tesla.administracion.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonaDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Long personaId;
	public String nombreCompleto;
	@NotEmpty(message = "El campo no puede estar vacio")
	public String nombres;    
	@NotEmpty(message = "El campo no puede estar vacio")
    public String paterno;    
    public String materno;
	public String direccion;
	@NotEmpty(message = "El campo no puede estar vacio")
	@Email(message = "no es una direccion de correo bine formada")
	public String correoElectronico;
	public String telefono;
	@NotEmpty(message = "El campo no puede estar vacio")
	public String nroDocumento;
	public BigInteger usuarioCreacion;
	public Date fechaCreacion;
	public String usuarioModificacion;
	public Date fechaModificacion;
	public String estado;
	public String transaccion;
	public Long ciudadId;
	public String ciudad;
	public Long tipoDocumentoId;
	public String tipoDocumento;	
	public String extensionDocumento;

	public Long extensionDocumentoId;
	public String login;
	
	public String parametro;
	public int page;
	public Boolean isEntidad;
	public Long sucursalId;	
	
	public String estadoUsuario;
	public Long usuarioId;
	public String modulo;
	public String subModulo;
	
	
	public Long recaudadorId;
	public Long entidadId;
	
	public Boolean esAdmin;
	public Long empleadoId;
	
	public String nombreEntidad;
	public String nombreRecaudadora;
	public String nombreSucursal;
	
	public List<Long> privilegiosKey=new ArrayList<>();
	public List<RolTransferDto> rolTransferList=new ArrayList<>();
	
	
	
	
	
	public PersonaDto() {
		
	}

	public PersonaDto(
			Long personaId, 
			String nombres, 
			String paterno,
			String materno,
			String direccion, 
			String correoElectronico,
			String telefono, 
			String nroDocumento, 
			String estado, 
			Long ciudadId, 
			String ciudad,
			Long extensionDocumentoId,
			String extensionDocumento,
			Date fechaModificacion,
			String login,
			String estadoUsuario,
			Long usuarioId
			
			) {
	
		this.personaId = personaId;
		if(materno!=null) {
			this.nombreCompleto = nombres+" "+paterno+" "+materno;	
		}else {
			this.nombreCompleto = nombres+" "+paterno;
		}
		this.nombres=nombres;
		this.paterno=paterno;
		this.materno=materno;
		this.direccion = direccion;
		this.correoElectronico = correoElectronico;
		this.telefono = telefono;
		this.nroDocumento = nroDocumento;
		this.estado = estado;
		this.ciudadId = ciudadId;
		this.ciudad = ciudad;
		this.extensionDocumento = extensionDocumento;
		this.extensionDocumentoId = extensionDocumentoId;
		this.fechaModificacion=fechaModificacion;
		this.login=login;
		this.estadoUsuario=estadoUsuario;
		this.usuarioId=usuarioId;
		
		
	}
	
	public PersonaDto(
			Long personaId, 
			String nombres, 
			String paterno,
			String materno,
			String direccion, 
			String correoElectronico,
			String telefono, 
			String nroDocumento, 
			String estado, 
			Long ciudadId, 
			String ciudad,
			Long extensionDocumentoId,
			String extensionDocumento,
			Date fechaModificacion,
			String login,
			String estadoUsuario,
			Long usuarioId,
			Long empleadoId,
			Boolean admin
			) {
	
		this.personaId = personaId;
		if(materno!=null) {
			this.nombreCompleto = nombres+" "+paterno+" "+materno;	
		}else {
			this.nombreCompleto = nombres+" "+paterno;
		}
		
		this.nombres=nombres;
		this.paterno=paterno;
		this.materno=materno;
		this.direccion = direccion;
		this.correoElectronico = correoElectronico;
		this.telefono = telefono;
		this.nroDocumento = nroDocumento;
		this.estado = estado;
		this.ciudadId = ciudadId;
		this.ciudad = ciudad;
		this.extensionDocumento = extensionDocumento;
		this.extensionDocumentoId = extensionDocumentoId;
		this.fechaModificacion=fechaModificacion;
		this.login=login;
		this.estadoUsuario=estadoUsuario;
		this.usuarioId=usuarioId;
		this.empleadoId=empleadoId;
		this.esAdmin=admin;
	}
	
	
	
	
	

}
