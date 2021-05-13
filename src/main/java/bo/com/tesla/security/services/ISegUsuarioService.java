package bo.com.tesla.security.services;

import java.util.Optional;

import bo.com.tesla.administracion.entity.SegUsuarioEntity;

public interface ISegUsuarioService {
	
	
	public SegUsuarioEntity save(SegUsuarioEntity entity);
	public SegUsuarioEntity findByLogin(String login);
	public Optional<SegUsuarioEntity> findByPersonaIdAndEstado( Long personaId);

}
