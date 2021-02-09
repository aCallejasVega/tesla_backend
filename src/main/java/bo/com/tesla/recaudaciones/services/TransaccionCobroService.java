package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.TransaccionCobroEntity;
import bo.com.tesla.recaudaciones.dao.ITransaccionCobroDao;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TransaccionCobroService implements ITransaccionCobroService {

    @Autowired
    private ITransaccionCobroDao iTransaccionCobroDao;

    @Override
    public TransaccionCobroEntity saveTransaccionCobro(TransaccionCobroEntity transaccionCobroEntity) {
        return iTransaccionCobroDao.save(transaccionCobroEntity);
    }

    @Override
    public List<TransaccionCobroEntity> saveAllTransaccionesCobros(List<TransaccionCobroEntity> transaccionCobroEntities) {
        return iTransaccionCobroDao.saveAll(transaccionCobroEntities);
    }

    @Override
    public TransaccionCobroEntity loadTransaccionCobro(ServicioDeudaDto servicioDeudaDto, Long usuarioId) {

        TransaccionCobroEntity transaccionCobroEntity = new TransaccionCobroEntity();
        transaccionCobroEntity.setTipoServicio(servicioDeudaDto.tipoServicio);
        transaccionCobroEntity.setServicio(servicioDeudaDto.servicio);
        transaccionCobroEntity.setPeriodo(servicioDeudaDto.periodo);
        transaccionCobroEntity.setUsuarioCreacion(usuarioId);
        transaccionCobroEntity.setFechaCreacion(new Date());
        transaccionCobroEntity.setEstado("COBRADO");
        //transaccionCobroEntity.setTransaccion("COBRAR");

        return  transaccionCobroEntity;
    }

}
