package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.*;
import bo.com.tesla.administracion.services.IEntidadComisionService;
import bo.com.tesla.entidades.dao.IArchivoDao;
import bo.com.tesla.recaudaciones.dao.IEntidadRDao;
import bo.com.tesla.recaudaciones.dao.IRecaudadorDao;
import bo.com.tesla.recaudaciones.dao.ITransaccionCobroDao;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;
import bo.com.tesla.useful.config.Technicalexception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private IRecaudadorDao iRecaudadorDao;

    @Override
    public TransaccionCobroEntity saveTransaccionCobro(TransaccionCobroEntity transaccionCobroEntity) {
        return iTransaccionCobroDao.save(transaccionCobroEntity);
    }

    @Override
    public List<TransaccionCobroEntity> saveAllTransaccionesCobros(List<TransaccionCobroEntity> transaccionCobroEntities) {
        return iTransaccionCobroDao.saveAll(transaccionCobroEntities);
    }

    @Override
    public TransaccionCobroEntity loadTransaccionCobro(ServicioDeudaDto servicioDeudaDto, Long usuarioId, String nombreCientePago, String nroDocumentoClientePago) {

        Optional<EntidadEntity> entidadEntityOptional = iEntidadRDao.findByEntidadId(servicioDeudaDto.entidadId);//debe considerar estado??
        if(!entidadEntityOptional.isPresent()) {
            throw new Technicalexception("No se encontró la EntidadId" + servicioDeudaDto.entidadId);
        }

        ArchivoEntity archivoEntity = iArchivoDao.findByEstado("ACTIVO", servicioDeudaDto.entidadId);
        if(archivoEntity == null) {
            throw new Technicalexception("No se encontró la EntidadId" + servicioDeudaDto.entidadId);
        }

        Optional<RecaudadorEntity> recaudadorEntityOptional = iRecaudadorDao.findRecaudadorByUserId(usuarioId);
        if(!recaudadorEntityOptional.isPresent()) {
            throw new Technicalexception("El usuarioId=" + usuarioId + " no esta registrado en ninguna sucursal de recaudadción");
        }


        EntidadComisionEntity entidadComisionEntity = iEntidadComisionService.getEntidadComisionActual(entidadEntityOptional.get());

        TransaccionCobroEntity transaccionCobroEntity = new TransaccionCobroEntity();
        transaccionCobroEntity.setTipoServicio(servicioDeudaDto.tipoServicio);
        transaccionCobroEntity.setServicio(servicioDeudaDto.servicio);
        transaccionCobroEntity.setPeriodo(servicioDeudaDto.periodo);
        transaccionCobroEntity.setUsuarioCreacion(usuarioId);
        transaccionCobroEntity.setFechaCreacion(new Date());
        transaccionCobroEntity.setEntidadId(entidadEntityOptional.get());
        transaccionCobroEntity.setTransaccion("COBRAR");
        transaccionCobroEntity.setArchivoId(archivoEntity);
        transaccionCobroEntity.setCodigoCliente(servicioDeudaDto.codigoCliente);
        transaccionCobroEntity.setNombreClientePago(nombreCientePago);
        transaccionCobroEntity.setTotalDeuda(servicioDeudaDto.subTotal);
        transaccionCobroEntity.setNroDocumentoClientePago(nroDocumentoClientePago);
        transaccionCobroEntity.setComision(iEntidadComisionService.calcularComision(entidadComisionEntity, servicioDeudaDto.subTotal));//iEntidadComisionService.calculateMontoByComisonByEntidadID(entidadEntityOptional.get(), servicioDeudaDto.subTotal));
        transaccionCobroEntity.setRecaudador(recaudadorEntityOptional.get());
        transaccionCobroEntity.setNombreClienteArchivo("");
        transaccionCobroEntity.setNroDocumentoClienteArchivo("");
        transaccionCobroEntity.setEntidadComision(entidadComisionEntity);
        return  transaccionCobroEntity;
    }

}
