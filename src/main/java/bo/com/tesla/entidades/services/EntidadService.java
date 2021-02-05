package bo.com.tesla.entidades.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bo.com.tesla.administracion.entity.EntidadEntity;
import bo.com.tesla.entidades.dao.IEntidadDao;
import bo.com.tesla.security.dao.ISegRolDao;


@Service
public class EntidadService implements IEntidadService {
	
	@Autowired
	private IEntidadDao entidadDao;

	@Override
	public EntidadEntity findEntidadByUserId(Long usuarioId) {
		return this.entidadDao.findEntidadByUserId(usuarioId);		
	}

}
