package bo.com.tesla.recaudaciones.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bo.com.tesla.administracion.entity.RecaudadorEntity;
import bo.com.tesla.recaudaciones.dao.IRecaudadorDao;

@Service
public class RecaudadoraService implements IRecaudadoraService {
	
	@Autowired
	private IRecaudadorDao recaudadorDao;

	@Override
	public RecaudadorEntity findRecaudadorByUserId(Long usuarioId) {
	
		return this.recaudadorDao.findRecaudadorByUserId(usuarioId);
	}

	@Override
	public RecaudadorEntity findByRecaudadorId(Long recaudadorId) {	
		return this.recaudadorDao.findByRecaudadorId(recaudadorId);
	}

	@Override
	public List<RecaudadorEntity> findRecaudadoresByEntidadId(Long entidadId) {
		
		return this.recaudadorDao.findRecaudadoresByEntidadId(entidadId);
	}
	

}
