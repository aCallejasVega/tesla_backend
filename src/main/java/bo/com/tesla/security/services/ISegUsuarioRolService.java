package bo.com.tesla.security.services;

import bo.com.tesla.administracion.dto.PersonaDto;
import bo.com.tesla.useful.config.BusinesException;

public interface ISegUsuarioRolService {
	
	public void saveRolesByUsuarioId(PersonaDto personaDto) throws BusinesException;

}
