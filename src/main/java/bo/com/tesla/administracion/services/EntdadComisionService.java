package bo.com.tesla.administracion.services;

import bo.com.tesla.administracion.dao.IEntidadComisionesDao;
import bo.com.tesla.administracion.dto.EntidadComisionAdmDto;
import bo.com.tesla.administracion.entity.*;
import bo.com.tesla.recaudaciones.dao.IDominioDao;
import bo.com.tesla.recaudaciones.dao.IEntidadRDao;
import bo.com.tesla.useful.config.Technicalexception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class EntdadComisionService implements IEntidadComisionService {

    @Autowired
    private IEntidadComisionesDao iEntidadComisionesDao;

    @Autowired
    private IEntidadRDao iEntidadDao;

    @Autowired
    private IDominioDao iDominioDao;

    @Transactional
    @Override
    public EntidadComisionAdmDto addUpdateEntidadComision(EntidadComisionAdmDto entidadComisionAdmDto, Long usuarioId) throws Technicalexception {
        try {
            if (entidadComisionAdmDto.entidadComisionId != null) {
                /***Modificación***/
                Optional<EntidadComisionEntity> entidadComisionEntityOptional = iEntidadComisionesDao.findById(entidadComisionAdmDto.entidadComisionId);
                if(!entidadComisionEntityOptional.isPresent()) {
                    throw new Technicalexception("No existe entidadComisionId=" + entidadComisionAdmDto.entidadComisionId);
                }

                EntidadComisionEntity entidadComisionEntityOriginal = entidadComisionEntityOptional.get();
                entidadComisionEntityOriginal.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
                entidadComisionEntityOriginal.setUsuarioModificacion(usuarioId);
                entidadComisionEntityOriginal.setTransaccion("MODIFICAR");

                return saveEntidadComision(entidadComisionAdmDto, entidadComisionEntityOriginal, usuarioId);
            } else {
                /***Creación***/
                EntidadComisionEntity entidadComisionEntity = new EntidadComisionEntity();
                entidadComisionEntity.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
                entidadComisionEntity.setUsuarioCreacion(usuarioId);
                entidadComisionEntity.setTransaccion("ACTIVAR");

                Integer countUpdate = iEntidadComisionesDao.updateEntidadComisionActiva(entidadComisionAdmDto.entidadId, usuarioId);

                return saveEntidadComision(entidadComisionAdmDto, entidadComisionEntity, usuarioId);
            }
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(), e.getCause());
        }
    }

    public EntidadComisionAdmDto saveEntidadComision(EntidadComisionAdmDto entidadComisionAdmDto, EntidadComisionEntity entidadComisionEntity, Long usuarioId) {

        Optional<DominioEntity> tipoComisionOptional = iDominioDao.getDominioEntityByDominioIdAndDominioAndEstado(entidadComisionAdmDto.tipoComisionId, "tipo_comision_id","ACTIVO");
        if (!tipoComisionOptional.isPresent()) {
            throw new Technicalexception("No existe Dominio tipo_comision_id=" + entidadComisionAdmDto.tipoComisionId);
        }

        Optional<EntidadEntity> entidadEntityOptional = iEntidadDao.findByEntidadId(entidadComisionAdmDto.entidadId);
        if(!entidadEntityOptional.isPresent()) {
            throw new Technicalexception("No existe EntidadId=" + entidadComisionAdmDto.entidadId);
        }

        entidadComisionEntity.setTipoComision(tipoComisionOptional.get());
        entidadComisionEntity.setComision(entidadComisionAdmDto.comision);
        entidadComisionEntity.setEntidad(entidadEntityOptional.get());

        entidadComisionEntity = iEntidadComisionesDao.save(entidadComisionEntity);
        entidadComisionAdmDto.entidadComisionId = entidadComisionEntity.getEntidadComisionId();

        return entidadComisionAdmDto;
    }

    @Override
    public void setTransaccion(Long entidadComisionId, String transaccion, Long usuarioId) throws Technicalexception {
        try {
            if(!iEntidadComisionesDao.existsById(entidadComisionId)) {
                throw new Technicalexception("No existe registro entidadComisionId=" + entidadComisionId);
            }
            Integer countUpdate = iEntidadComisionesDao.updateTransaccion(entidadComisionId, transaccion, usuarioId);
            if(countUpdate < 1) {
                throw new Technicalexception("No se actualizó el estado de entidadComisionId=" + entidadComisionId);
            }
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(), e.getCause());
        }
    }

    @Override
    public EntidadComisionAdmDto getEntidadComisionById(Long entidadComisionId) throws Technicalexception {
        Optional<EntidadComisionAdmDto> entidadComisionAdmDtoOptional = iEntidadComisionesDao.findEntidadComisionDtoById(entidadComisionId);
        if(!entidadComisionAdmDtoOptional.isPresent()) {
            return null;//Se controlará el status 204 en controller
        }
        return entidadComisionAdmDtoOptional.get();
    }

    @Override
    public List<EntidadComisionAdmDto> getAllEntidadesComisionesByEntidadId(Long entidadId) {
        return iEntidadComisionesDao.findEntidadComisionByEntidadId(entidadId);
    }

    @Override
    public EntidadComisionEntity getEntidadComisionActual(EntidadEntity entidadEntity) throws Technicalexception{
        EntidadComisionEntity entidadComisionEntity = new EntidadComisionEntity();

        Optional<EntidadComisionEntity> entidadComisionEntityOptional = iEntidadComisionesDao.findEntidadComisionEntityByEntidadAndEstado(entidadEntity, "ACTIVO");
        if(!entidadComisionEntityOptional.isPresent()) {
            throw new Technicalexception("No se encuentra registro activo de comision para entidadId=" + entidadEntity.getEntidadId());
        }

        entidadComisionEntity =entidadComisionEntityOptional.get();
        return entidadComisionEntity;
    }

    @Override
    public BigDecimal calcularComision(EntidadComisionEntity entidadComisionEntity, BigDecimal monto) {
        /*********INGRESAR CALCULOS*****************/
        BigDecimal comision = new BigDecimal(0);
        BigDecimal cien = BigDecimal.valueOf(100);

        BigDecimal montoCalculado = new BigDecimal(0);

        //Aumentar otros cálculos
        if (entidadComisionEntity.getTipoComision().getDominioId() == 41L) { //Porcentaje
            comision = entidadComisionEntity.getComision().divide(cien);
            montoCalculado = monto.multiply(comision);
        } else if (entidadComisionEntity.getTipoComision().getDominioId() == 42L) { //Fijo bolivianos
            /*comision = entidadComisionEntity.getComision();
            montoCalculado = monto.subtract(comision);*/
            montoCalculado = entidadComisionEntity.getComision();
        }
        return montoCalculado;
    }

    @Override
    public BigDecimal calculateMontoByComisonByEntidadID(EntidadEntity entidadEntity, BigDecimal monto) {
        Optional<EntidadComisionEntity> entidadComisionEntityOptional = iEntidadComisionesDao.findEntidadComisionEntityByEntidadAndEstado(entidadEntity, "ACTIVO");
        if(!entidadComisionEntityOptional.isPresent()) {
            throw new Technicalexception("No se encuentra registro activo de comision para entidadId=" + entidadEntity.getEntidadId());
        }

        /*********INGRESAR CALCULOS*****************/
        EntidadComisionEntity entidadComisionEntity = entidadComisionEntityOptional.get();
        BigDecimal comision = new BigDecimal(0);
        BigDecimal cien = BigDecimal.valueOf(100);

        BigDecimal montoCalculado = new BigDecimal(0);

        //Aumentar otros cálculos
        if (entidadComisionEntity.getTipoComision().getDominioId() == 38L) { //Porcentaje
            comision = entidadComisionEntity.getComision().divide(cien);
            montoCalculado = monto.multiply(comision);
        } else if (entidadComisionEntity.getTipoComision().getDominioId() == 39L) { //Fijo bolivianos
            /*comision = entidadComisionEntity.getComision();
            montoCalculado = monto.subtract(comision);*/
            montoCalculado = entidadComisionEntity.getComision();
        }
        return montoCalculado;
    }



}
