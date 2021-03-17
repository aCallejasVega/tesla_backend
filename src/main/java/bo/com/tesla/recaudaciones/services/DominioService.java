package bo.com.tesla.recaudaciones.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bo.com.tesla.administracion.entity.DominioEntity;
import bo.com.tesla.recaudaciones.dao.IDominioDao;

@Service
public class DominioService implements IDominioService{
	@Autowired
	private IDominioDao dominioDao;

	@Override
	public List<DominioEntity> findByDominio(String dominio) {	
		return dominioDao.findByDominio(dominio);
	}

}
