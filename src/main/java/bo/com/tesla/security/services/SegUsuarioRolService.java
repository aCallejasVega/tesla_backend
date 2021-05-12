package bo.com.tesla.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bo.com.tesla.administracion.dao.IPersonaDao;
import bo.com.tesla.administracion.dto.PersonaDto;
import bo.com.tesla.administracion.entity.PersonaEntity;
import bo.com.tesla.administracion.entity.SegRolEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.administracion.entity.SegUsuarioRolEntity;
import bo.com.tesla.security.dao.ISegRolDao;
import bo.com.tesla.security.dao.ISegUsuarioDao;
import bo.com.tesla.security.dao.ISegUsuarioRolDao;
import bo.com.tesla.useful.config.BusinesException;
import bo.com.tesla.useful.config.Technicalexception;

@Service
public class SegUsuarioRolService implements ISegUsuarioRolService {

	@Autowired
	private ISegUsuarioRolDao usuarioRolDao;
	@Autowired
	private ISegRolDao rolDao;

	@Autowired
	private ISegUsuarioDao usuarioDao;

	@Autowired
	private IPersonaDao personaDao;

	@Transactional
	@Override
	public void saveRolesByUsuarioId(PersonaDto personaDto) throws BusinesException{

		PersonaEntity persona = this.personaDao.findById(personaDto.personaId).get();
		SegUsuarioEntity usuario =new SegUsuarioEntity();
		if (!this.usuarioDao.findByPersonaIdAndEstado(persona.getPersonaId()).isPresent()) {
			new BusinesException("Antes de registrar los roles debe registrar las credenciales del usuario.");
		} else {
			 usuario = this.usuarioDao.findByPersonaIdAndEstado(persona.getPersonaId()).get();
		}

		for (Long rodId : personaDto.privilegiosKey) {
			SegUsuarioRolEntity usuarioRol = new SegUsuarioRolEntity();
			Optional<SegUsuarioRolEntity> usuarioRolUpdate = this.usuarioRolDao
					.findByRolIdAndUsuarioId(usuario.getUsuarioId(), rodId);
			if (!usuarioRolUpdate.isPresent()) {

				SegRolEntity rol = this.rolDao.findById(rodId).get();
				usuarioRol.setRolId(rol);
				usuarioRol.setUsuarioId(usuario);
				usuarioRol.setEstado("ACTIVO");
				this.usuarioRolDao.save(usuarioRol);
			} else if (!usuarioRolUpdate.get().getEstado().equals("ACTIVO")) {
				usuarioRol = usuarioRolUpdate.get();
				usuarioRol.setEstado("ACTIVO");
				this.usuarioRolDao.save(usuarioRol);
			}
		}

		List<SegRolEntity> rolesUsuario = this.rolDao.findRolesByUsuarioLogin(usuario.getLogin());

		for (SegRolEntity segRolEntity : rolesUsuario) {
			Boolean bandera = true;
			for (Long rodId : personaDto.privilegiosKey) {
				if (segRolEntity.getRolId().equals(rodId)) {
					bandera = false;
				}
			}
			if (bandera) {
				SegUsuarioRolEntity usuarioRolUpdate = this.usuarioRolDao
						.findByRolIdAndUsuarioId(usuario.getUsuarioId(), segRolEntity.getRolId()).get();
				usuarioRolUpdate.setEstado("INACTIVO");
				this.usuarioRolDao.save(usuarioRolUpdate);
			}
		}

	}

}
