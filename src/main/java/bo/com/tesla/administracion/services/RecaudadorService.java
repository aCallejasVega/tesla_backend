package bo.com.tesla.administracion.services;

import bo.com.tesla.administracion.dao.IEntidadRecaudadorDao;
import bo.com.tesla.administracion.dto.EntidadAdmDto;
import bo.com.tesla.administracion.dto.RecaudadorAdmDto;
import bo.com.tesla.administracion.entity.DominioEntity;
import bo.com.tesla.administracion.entity.EntidadEntity;
import bo.com.tesla.administracion.entity.EntidadRecaudadorEntity;
import bo.com.tesla.administracion.entity.RecaudadorEntity;
import bo.com.tesla.recaudaciones.dao.IDominioDao;
import bo.com.tesla.recaudaciones.dao.IEntidadRDao;
import bo.com.tesla.recaudaciones.dao.IRecaudadorDao;
import bo.com.tesla.useful.config.Technicalexception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class RecaudadorService implements IRecaudadorService{

    @Autowired
    private IRecaudadorDao iRecaudadorDao;

    @Autowired
    private IDominioDao iDominioDao;

    @Autowired
    private IEntidadRDao iEntidadRDao;

    @Autowired
    private IEntidadRecaudadorDao iEntidadRecaudadorDao;


    /*********************ABM**************************/

    @Override
    public RecaudadorAdmDto addUpdateRecaudador(RecaudadorAdmDto recaudadorAdmDto, Long usuarioId) throws Technicalexception {
        try {
            if (recaudadorAdmDto.recaudadorId != null) {
                /***Modificación***/
                Optional<RecaudadorEntity> recaudadorEntityOptional = iRecaudadorDao.findById(recaudadorAdmDto.recaudadorId);
                if(!recaudadorEntityOptional.isPresent()) {
                    throw new Technicalexception("No existe recaudadorId=" + recaudadorAdmDto.recaudadorId);
                }
                RecaudadorEntity recaudadorEntityOriginal = recaudadorEntityOptional.get();
                recaudadorEntityOriginal.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
                recaudadorEntityOriginal.setUsuarioModificacion(usuarioId);
                recaudadorEntityOriginal.setTransaccion("MODIFICAR");
                return saveRecaudador(recaudadorAdmDto, recaudadorEntityOriginal);
            } else {
                /***Creación***/
                RecaudadorEntity recaudadorEntity = new RecaudadorEntity();
                recaudadorEntity.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
                recaudadorEntity.setUsuarioCreacion(usuarioId);
                recaudadorEntity.setTransaccion("CREAR");

                if(recaudadorAdmDto.entidadIdLst != null) {
                    if (recaudadorAdmDto.entidadIdLst.size() > 0) {
                        List<EntidadRecaudadorEntity> entidadRecaudadorEntityList = new ArrayList<>();
                        for (Long entidadId : recaudadorAdmDto.entidadIdLst) {
                            Optional<EntidadEntity> entidadEntityOptional = iEntidadRDao.findById(entidadId);
                            if (!entidadEntityOptional.isPresent()) {
                                throw new Technicalexception("No existe la entidadId=" + entidadId);
                            }

                            EntidadRecaudadorEntity entidadRecaudadorEntity = new EntidadRecaudadorEntity();
                            entidadRecaudadorEntity.setEntidad(entidadEntityOptional.get());
                            entidadRecaudadorEntity.setRecaudador(recaudadorEntity);
                            entidadRecaudadorEntity.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
                            entidadRecaudadorEntity.setUsuarioCreacion(usuarioId);
                            entidadRecaudadorEntity.setEstado("CREADO");

                            entidadRecaudadorEntityList.add(entidadRecaudadorEntity);
                        }
                        recaudadorEntity.setEntidadRecaudadorEntityList(entidadRecaudadorEntityList);
                    }
                }






                return saveRecaudador(recaudadorAdmDto, recaudadorEntity);
            }
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(), e.getCause());
        }
    }

    private RecaudadorAdmDto saveRecaudador(RecaudadorAdmDto recaudadorAdmDto, RecaudadorEntity recaudadorEntity) {

        Optional<DominioEntity> tipoRecaudadorOptional = iDominioDao.getDominioEntityByDominioIdAndDominioAndEstado(recaudadorAdmDto.tipoRecaudadorId, "tipo_recaudador_id","ACTIVO");
        if (!tipoRecaudadorOptional.isPresent()) {
            throw new Technicalexception("No existe Dominio tipo_recaudador_id=" + recaudadorAdmDto.tipoRecaudadorId);
        }
        recaudadorEntity.setTipoRecaudador(tipoRecaudadorOptional.get()); //tipoRecaudadorOptional.get());
        recaudadorEntity.setNombre(recaudadorAdmDto.nombre.toUpperCase().trim());
        recaudadorEntity.setDireccion(recaudadorAdmDto.direccion.toUpperCase().trim());
        recaudadorEntity.setTelefono(recaudadorAdmDto.telefono);

        recaudadorEntity = iRecaudadorDao.save(recaudadorEntity);
        recaudadorAdmDto.recaudadorId = recaudadorEntity.getRecaudadorId();

        return recaudadorAdmDto;
    }

    @Override
    public void setTransaccion(Long recaudadorId, String transaccion, Long usuarioId) throws Technicalexception{
        try {
            if(!iRecaudadorDao.existsById(recaudadorId)) {
                throw new Technicalexception("No existe registro recaudadorId=" + recaudadorId);
            }
            Integer countUpdate = iRecaudadorDao.updateTransaccionRecaudadora(recaudadorId, transaccion, usuarioId);
            if(countUpdate < 1) {
                throw new Technicalexception("No se actualizó el estado de recaudadorId=" + recaudadorId);
            }
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(), e.getCause());
        }
    }

    @Transactional
    @Override
    public void setLstTransaccion(List<Long> recaudadorIdLst, String transaccion, Long usuarioId) throws Technicalexception{
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

            Integer countUpdate = iRecaudadorDao.updateLstTransaccionRecaudadora(recaudadorIdLst, transaccion, usuarioId);
            if(countUpdate != recaudadorIdLst.size()) {
                throw new Technicalexception("No se actualizaron todos los registros o no se encuentran algunos registros.");
            }
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(), e.getCause());
        }
    }

    @Override
    public RecaudadorAdmDto getRecaudadoraById(Long recaudadorId) throws Technicalexception {
        Optional<RecaudadorAdmDto> recaudadorAdmDtoOptional = iRecaudadorDao.findRecaudadorDtoById(recaudadorId);
        if (!recaudadorAdmDtoOptional.isPresent()) {
            return null;//Se controlará el status 204 en controllee
        }

        RecaudadorAdmDto recaudadorAdmDto = recaudadorAdmDtoOptional.get();
        List<Long> entidadIdLst = new ArrayList<>();
        entidadIdLst = iEntidadRecaudadorDao.getLstByRecaudadorIdActivo(recaudadorId);
        recaudadorAdmDto.entidadIdLst = entidadIdLst;

        return recaudadorAdmDto;
    }

    @Override
    public List<RecaudadorAdmDto> getAllRecaudadoras() throws Technicalexception{
        return iRecaudadorDao.findRecaudadorDtoAll();
    }

}
