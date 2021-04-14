package bo.com.tesla.pagos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bo.com.tesla.administracion.entity.PHistoricoAbonoClienteEntity;
import bo.com.tesla.pagos.dao.IPHistoricoAbonoClienteDao;

@Service
public class PHistoricoAbonoClienteService implements IPHistoricoAbonoClienteService {
	
	@Autowired
	private IPHistoricoAbonoClienteDao  historicoAbonoClienteDao;

	@Override
	public PHistoricoAbonoClienteEntity save(PHistoricoAbonoClienteEntity entity) {		
		return this.historicoAbonoClienteDao.save(entity);
	}

}
