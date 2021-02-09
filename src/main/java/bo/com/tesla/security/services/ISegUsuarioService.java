package bo.com.tesla.security.services;

import bo.com.tesla.administracion.entity.SegUsuarioEntity;

public interface ISegUsuarioService {
	
	
	public SegUsuarioEntity save(SegUsuarioEntity entity);
	public SegUsuarioEntity findByLogin(String login);

}
