package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.*;
import bo.com.tesla.administracion.services.IEntidadComisionService;
import bo.com.tesla.administracion.services.IRecaudadorComisionService;
import bo.com.tesla.entidades.dao.IArchivoDao;
import bo.com.tesla.recaudaciones.dao.IEntidadRDao;
import bo.com.tesla.recaudaciones.dao.IRecaudadorDao;
import bo.com.tesla.recaudaciones.dao.ITransaccionCobroDao;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;
import bo.com.tesla.useful.config.Technicalexception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class TransaccionCobroService implements ITransaccionCobroService {

    @Autowired
    private ITransaccionCobroDao iTransaccionCobroDao;

    @Autowired
    private IEntidadRDao iEntidadRDao;

    @Autowired
    private IArchivoDao iArchivoDao;

    @Autowired
    private IEntidadComisionService iEntidadComisionService;

    @Autowired
    private IRecaudadorComisionService iRecaudadorComisionService;

    @Override
    public TransaccionCobroEntity saveTransaccionCobro(TransaccionCobroEntity transaccionCobroEntity) {
        return iTransaccionCobroDao.save(transaccionCobroEntity);
    }

    @Override
    public List<TransaccionCobroEntity> saveAllTransaccionesCobros(List<TransaccionCobroEntity> transaccionCobroEntities) {
        return iTransaccionCobroDao.saveAll(transaccionCobroEntities);
    }

    @Transactional(readOnly = true)
    @Override
    public TransaccionCobroEntity loadTransaccionCobro(ServicioDeudaDto servicioDeudaDto, EntidadEntity entidadEntity, Long usuarioId, String nombreCientePago, String nroDocumentoClientePago,
                                                       EntidadComisionEntity entidadComisionEntity, RecaudadorEntity recaudadorEntity, RecaudadorComisionEntity recaudadorComisionEntity,
                                                       ArchivoEntity archivoEntity, DominioEntity metodoCobro) {

        TransaccionCobroEntity transaccionCobroEntity = new TransaccionCobroEntity();
        transaccionCobroEntity.setTipoServicio(servicioDeudaDto.tipoServicio);
        transaccionCobroEntity.setServicio(servicioDeudaDto.servicio);
        transaccionCobroEntity.setPeriodo(servicioDeudaDto.periodo);
        transaccionCobroEntity.setUsuarioCreacion(usuarioId);
        transaccionCobroEntity.setFechaCreacion(new Date());
        transaccionCobroEntity.setEntidadId(entidadEntity);
        transaccionCobroEntity.setTransaccion("COBRAR");
        transaccionCobroEntity.setArchivoId(archivoEntity);
        transaccionCobroEntity.setCodigoCliente(servicioDeudaDto.codigoCliente);
        transaccionCobroEntity.setNombreClientePago(nombreCientePago);
        transaccionCobroEntity.setTotalDeuda(servicioDeudaDto.subTotal);
        transaccionCobroEntity.setNroDocumentoClientePago(nroDocumentoClientePago);
        transaccionCobroEntity.setComision(iEntidadComisionService.calcularComision(entidadComisionEntity, servicioDeudaDto.subTotal));
        transaccionCobroEntity.setRecaudador(recaudadorEntity);
        transaccionCobroEntity.setNombreClienteArchivo(servicioDeudaDto.nombreCliente);
        transaccionCobroEntity.setNroDocumentoClienteArchivo(servicioDeudaDto.nroDocumento);
        transaccionCobroEntity.setEntidadComision(entidadComisionEntity);
        transaccionCobroEntity.setComisionRecaudacion(iRecaudadorComisionService.calcularComision(recaudadorComisionEntity, servicioDeudaDto.subTotal));
        transaccionCobroEntity.setRecaudadorComision(recaudadorComisionEntity);
        transaccionCobroEntity.setMetodoCobro(metodoCobro);
        return  transaccionCobroEntity;
    }

	@Override
	public List<TransaccionCobroEntity> findDeudasCobradasByUsuarioCreacionForGrid(Long usuarioCreacion,
			Date fechaSeleccionada,Long entidadId) {
	
		return this.iTransaccionCobroDao.findDeudasCobradasByUsuarioCreacionForGrid(usuarioCreacion, fechaSeleccionada, entidadId);
	}

}
