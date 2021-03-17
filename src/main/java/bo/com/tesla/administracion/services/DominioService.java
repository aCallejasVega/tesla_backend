package bo.com.tesla.administracion.services;

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
}
