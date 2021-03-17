package bo.com.tesla.administracion.services;

import bo.com.tesla.administracion.dao.ISucursalEntidadDao;
import bo.com.tesla.administracion.dto.SucursalEntidadAdmDto;
import bo.com.tesla.administracion.entity.EntidadEntity;
import bo.com.tesla.administracion.entity.SucursalEntidadEntity;
import bo.com.tesla.recaudaciones.dao.IEntidadRDao;
import bo.com.tesla.useful.config.Technicalexception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class SucursalEntidadService implements ISucursalEntidadService {

    @Autowired
    private ISucursalEntidadDao iSucursalEntidadDao;

    @Autowired
    private IEntidadRDao iEntidadRDao;

    /*********************ABM**************************/

    @Override
    public SucursalEntidadAdmDto addUpdateSucursalEntidad(SucursalEntidadAdmDto sucursalEntidadAdmDto, Long usuarioId) throws Technicalexception {
        try {
            if (sucursalEntidadAdmDto.sucursalEntidadId != null) {
                /***Modificacion***/

                Optional<SucursalEntidadEntity> sucursalEntidadEntityOptional = iSucursalEntidadDao.findById(sucursalEntidadAdmDto.sucursalEntidadId);
                if(!sucursalEntidadEntityOptional.isPresent()) {
                    throw new Technicalexception("No existe sucursalEntidadId=" + sucursalEntidadAdmDto.sucursalEntidadId);
                }
                SucursalEntidadEntity sucursalEntidadEntityOriginal = sucursalEntidadEntityOptional.get();
                sucursalEntidadEntityOriginal.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
                sucursalEntidadEntityOriginal.setUsuarioModificacion(usuarioId);
                sucursalEntidadEntityOriginal.setTransaccion("MODIFICAR");

                return saveSucursalEntidad(sucursalEntidadAdmDto, sucursalEntidadEntityOriginal);
            } else {
                /***Creación***/
                SucursalEntidadEntity sucursalEntidadEntity = new SucursalEntidadEntity();
                sucursalEntidadEntity.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
                sucursalEntidadEntity.setUsuarioCreacion(usuarioId);
                sucursalEntidadEntity.setTransaccion("CREAR");

                return saveSucursalEntidad(sucursalEntidadAdmDto, sucursalEntidadEntity);
            }
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(), e.getCause());
        }
    }

    private SucursalEntidadAdmDto saveSucursalEntidad(SucursalEntidadAdmDto sucursalEntidadAdmDto, SucursalEntidadEntity sucursalEntidadEntity) {

        Optional<EntidadEntity> entidadEntityOptional = iEntidadRDao.findById(sucursalEntidadAdmDto.entidadId);
        if(!entidadEntityOptional.isPresent()) {
            throw new Technicalexception("No existe ENTIDAD EntidadId=" + sucursalEntidadAdmDto.entidadId);
        }

        sucursalEntidadEntity.setEntidad(entidadEntityOptional.get());
        sucursalEntidadEntity.setNombreSucursal(sucursalEntidadAdmDto.nombreSucursal.toUpperCase().trim());
        sucursalEntidadEntity.setDireccion(sucursalEntidadAdmDto.direccion.toUpperCase().trim());
        sucursalEntidadEntity.setTelefono(sucursalEntidadAdmDto.telefono);

        sucursalEntidadEntity = iSucursalEntidadDao.save(sucursalEntidadEntity);
        sucursalEntidadAdmDto.sucursalEntidadId = sucursalEntidadEntity.getSucursalEntidadId();

        return sucursalEntidadAdmDto;
    }

    @Override
    public void setTransaccionSucursalEntidad(Long sucursalEntidadId, String transaccion, Long usuarioId) {
        try {
            if(!iSucursalEntidadDao.existsById(sucursalEntidadId)) {
                throw new Technicalexception("No existe registro SucursalEntidadId=" + sucursalEntidadId);
            }
            Integer countUpdate = iSucursalEntidadDao.updateTransaccionSucursalEntidad(sucursalEntidadId, transaccion, usuarioId);
            if(countUpdate != 1) {
                throw new Technicalexception("No se actualizó el estado de sucursalEntidadId=" + sucursalEntidadId);
            }
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(), e.getCause());
        }
    }

    @Transactional
    @Override
    public void setLstTransaccion(List<Long> sucursalEntidadIdLst, String transaccion, Long usuarioId) throws Technicalexception{
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

            Integer countUpdate = iSucursalEntidadDao.updateLstTransaccionSucursalEntidad(sucursalEntidadIdLst, transaccion, usuarioId);
            if(countUpdate != sucursalEntidadIdLst.size()) {
                throw new Technicalexception("No se actualizaron todos los registros o no se encuentran algunos registros.");
            }
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(), e.getCause());
        }
    }

    @Override
    public SucursalEntidadAdmDto getSucursalEntidadById(Long sucursalEntidadId) {
        Optional<SucursalEntidadAdmDto> sucursalEntidadAdmDtoOptional = iSucursalEntidadDao.findSucursalEntidadDtoById(sucursalEntidadId);
        if(!sucursalEntidadAdmDtoOptional.isPresent()){
            return null;//Se controlará el status 204 en controllee
        }
        return sucursalEntidadAdmDtoOptional.get();
    }

    @Override
    public List<SucursalEntidadAdmDto> getAllSucursalEntidades() {
        return iSucursalEntidadDao.findSucursalesEntidadesDtoAll();
    }

    @Override
    public List<SucursalEntidadAdmDto> getLisSucursalEntidadesByEntidadId(Long entidadId) {
        return iSucursalEntidadDao.findSucursalesEntidadesDtoByEntidadId(entidadId);
    }
}