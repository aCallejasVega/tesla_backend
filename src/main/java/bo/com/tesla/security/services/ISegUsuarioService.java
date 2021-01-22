package bo.com.tesla.security.services;

import bo.com.tesla.administracion.entity.SegUsuarioEntity;

public interface ISegUsuarioService {
	
	public SegUsuarioEntity findByLogin(String login);

}
