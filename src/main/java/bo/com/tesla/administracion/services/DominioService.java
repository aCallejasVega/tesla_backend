package bo.com.tesla.administracion.services;

import bo.com.tesla.administracion.entity.DominioEntity;
import bo.com.tesla.recaudaciones.dao.IDominioDao;
import bo.com.tesla.recaudaciones.dto.DominioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DominioService implements IDominioService {

    @Autowired
    private IDominioDao iDominioDao;

    @Override
    public List<DominioDto> getListDominios(String dominio) {
        return iDominioDao.findDominioDtoByDominio(dominio);
    }

    @Override
	public List<DominioEntity> findByDominio(String dominio) {	
		return iDominioDao.findByDominio(dominio);
	}

    @Override
    public List<DominioDto> getLstDominiosByAgrupador(Long agrupadorId) {
        return iDominioDao.findLstByAgrupador(agrupadorId);
    }

	@Override
	public DominioEntity findById(Long dominioId) {	
		return this.iDominioDao.findById(dominioId).get();
	}
    
    

}
