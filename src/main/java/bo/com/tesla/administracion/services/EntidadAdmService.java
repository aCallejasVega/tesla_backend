package bo.com.tesla.administracion.services;

import bo.com.tesla.administracion.dao.IEntidadAdmDao;
import bo.com.tesla.administracion.dto.EntidadAdmDto;
import bo.com.tesla.administracion.entity.DominioEntity;
import bo.com.tesla.administracion.entity.EntidadEntity;
import bo.com.tesla.recaudaciones.dao.IDominioDao;
import bo.com.tesla.security.dao.ISegTransicionDao;
import bo.com.tesla.useful.config.Technicalexception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class EntidadAdmService implements IEntidadAdmService {

    @Autowired
    private IEntidadAdmDao iEntidadAdmDao;

    @Autowired
    private IDominioDao iDominioDao;

    @Autowired
    private ISegTransicionDao iSegTransicionDao;

    @Override
    public EntidadAdmDto addUpdateEntidad(EntidadAdmDto entidadAdmDto, Long usuarioId) throws Technicalexception {
        try {
            if (entidadAdmDto.entidadId != null) {
                /***Modificación***/
                if(!iEntidadAdmDao.existsById(entidadAdmDto.entidadId)) {
                    throw new Technicalexception("No existe EntidadId=" + entidadAdmDto.entidadId);
                }
                EntidadEntity entidadEntityOriginal = iEntidadAdmDao.findByEntidadId(entidadAdmDto.entidadId);
                entidadEntityOriginal.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
                entidadEntityOriginal.setUsuarioModificacion(usuarioId);
                entidadEntityOriginal.setTransaccion("MODIFICAR");
                return saveEntidad(entidadAdmDto, entidadEntityOriginal);
            } else {
                /***Creación***/
                EntidadEntity entidadEntity = new EntidadEntity();
                entidadEntity.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
                entidadEntity.setUsuarioCreacion(usuarioId);
                entidadEntity.setTransaccion("CREAR");
                return saveEntidad(entidadAdmDto, entidadEntity);
            }
        } catch (Exception e) {
          throw new Technicalexception(e.getMessage(), e.getCause());
        }
    }

    private EntidadAdmDto saveEntidad(EntidadAdmDto entidadAdmDto, EntidadEntity entidadEntity) {
        entidadEntity.setNombre(entidadAdmDto.nombre);
        entidadEntity.setNombreComercial(entidadAdmDto.nombreComercial);
        entidadEntity.setDireccion(entidadAdmDto.direccion);
        entidadEntity.setTelefono(entidadAdmDto.telefono);
        entidadEntity.setNit(entidadAdmDto.nit);
        entidadEntity.setPathLogo(entidadAdmDto.pathLogo);
        entidadEntity.setComprobanteEnUno(entidadAdmDto.comprobanteEnUno);

        Optional<DominioEntity> actividadEconomicaOptional = iDominioDao.getDominioEntityByDominioIdAndEstado(entidadAdmDto.actividadEconomicaId, "ACTIVO");
        if (actividadEconomicaOptional.isPresent()) {
            entidadEntity.setActividadEconomica(actividadEconomicaOptional.get());
        } else {
            throw new Technicalexception("No existe Dominio ActividadEconomicaId=" + entidadAdmDto.actividadEconomicaId);
        }

        Optional<DominioEntity> municipioOptional = iDominioDao.getDominioEntityByDominioIdAndEstado(entidadAdmDto.municipioId, "ACTIVO");
        if (municipioOptional.isPresent()) {
            entidadEntity.setMunicipio(municipioOptional.get());
        } else {
            throw new Technicalexception("No existe Dominio MunicipioId=" + entidadAdmDto.municipioId);
        }

        Optional<DominioEntity> tipoEntidadOptional = iDominioDao.getDominioEntityByDominioIdAndEstado(entidadAdmDto.tipoEntidadId, "ACTIVO");
        if (municipioOptional.isPresent()) {
            entidadEntity.setTipoEntidad(tipoEntidadOptional.get());
        } else {
            throw new Technicalexception("No existe Dominio TipoEntidadId=" + entidadAdmDto.tipoEntidadId);
        }

        entidadEntity = iEntidadAdmDao.save(entidadEntity);
        entidadAdmDto.entidadId = entidadEntity.getEntidadId();

        return entidadAdmDto;
    }

    @Override
    public void setTransaccion(Long entidadId, String transaccion, Long usuarioId) {
        try {
            if(!iEntidadAdmDao.existsById(entidadId)) {
                throw new Technicalexception("No existe registro EntidadId=" + entidadId);
            }
            iEntidadAdmDao.updateTransaccionEntidad(entidadId, transaccion, usuarioId);
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void setLstTransaccion(List<Long> entidadIdLst, String transaccion, Long usuarioId) {
        try {

            /******************
             * SE DEBE ARREGLAR LAS RELACIONES DE LA TABLAS DE SEG_TRANSCICIONES
             * CASO CONTRATIO MAPEAR NUEVAMNTE PARA CONSULTA

            for(Long entidadId : entidadIdLst) {
                Optional<EntidadEntity> entidadEntityOptional = iEntidadAdmDao.findById(entidadId);
                if(!entidadEntityOptional.isPresent()) {
                    throw new Technicalexception("No existe registro con EntidadId=" + entidadId);
                }
                Long countEntidades = iSegTransicionDao.countByTablaAndTransaccion("ENTIDADES", transaccion, entidadEntityOptional.get().getEstado());
                if(countEntidades < 1) {
                    throw new Technicalexception("No cumple parametrización de estado para EntidadId=" + entidadId);
                }
            }
             */

            iEntidadAdmDao.updateLstTransaccionEntidad(entidadIdLst, transaccion, usuarioId);
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(), e.getCause());
        }
    }

    @Override
    public EntidadAdmDto getEntidadById(Long entidadId) {
        return iEntidadAdmDao.findEntidadDtoById(entidadId);
    }

    @Override
    public List<EntidadAdmDto> getAllEntidades() {
        return iEntidadAdmDao.findEntidadesDtoAll();
    }



}
