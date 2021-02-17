package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.DeudaClienteEntity;
import bo.com.tesla.administracion.entity.HistoricoDeudaEntity;
import bo.com.tesla.recaudaciones.dao.IHistoricoDeudaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransaction;

import java.util.Optional;

@Service
public class HistoricoDeudaService implements IHistoricoDeudaService {

    @Autowired
    private IHistoricoDeudaDao iHistoricoDeudaDao;

    @Override
    public HistoricoDeudaEntity updateEstado(Long deudaClienteId, String estado) {
        Optional<HistoricoDeudaEntity> optionalHistoricoDeudaEntity = iHistoricoDeudaDao.findByDeudaClienteId(deudaClienteId);
        if(!optionalHistoricoDeudaEntity.isPresent()) {
            return null;
        } else {
            HistoricoDeudaEntity historicoDeudaEntity = optionalHistoricoDeudaEntity.get();
            historicoDeudaEntity.setEstado(estado);
            return historicoDeudaEntity;
        }
    }
}
