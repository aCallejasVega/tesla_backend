package bo.com.tesla.administracion.services;

import bo.com.tesla.administracion.dao.IEntidadAdmDao;
import bo.com.tesla.administracion.dao.ISucursalEntidadAdmDao;
import bo.com.tesla.administracion.dto.SucursalEntidadAdmDto;
import bo.com.tesla.administracion.entity.EntidadEntity;
import bo.com.tesla.administracion.entity.SucursalEntidadEntity;
import bo.com.tesla.useful.config.Technicalexception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class SucursalEntidadAdmService implements ISucursalEntidadAdmService {

    @Autowired
    private ISucursalEntidadAdmDao iSucursalEntidadAdmDao;

    @Autowired
    private IEntidadAdmDao iEntidadAdmDao;

    @Override
    public SucursalEntidadAdmDto addUpdateSucursalEntidad(SucursalEntidadAdmDto sucursalEntidadAdmDto, Long usuarioId) throws Technicalexception {
        try {
            if (sucursalEntidadAdmDto.sucursalEntidadId != null) {
                /***Modificacion***/
                if(!iSucursalEntidadAdmDao.existsById(sucursalEntidadAdmDto.sucursalEntidadId)) {
                    throw new Technicalexception("No existe EntidadId=" + sucursalEntidadAdmDto.sucursalEntidadId);
                }
                SucursalEntidadEntity sucursalEntidadEntityOriginal = iSucursalEntidadAdmDao.findBySucursalEntidadId(sucursalEntidadAdmDto.sucursalEntidadId);
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

        Optional<EntidadEntity> entidadEntityOptional = iEntidadAdmDao.findById(sucursalEntidadAdmDto.entidadId);
        if(!entidadEntityOptional.isPresent()) {
            throw new Technicalexception("No existe ENTIDAD EntidadId=" + sucursalEntidadAdmDto.entidadId);
        }

        sucursalEntidadEntity.setEntidad(entidadEntityOptional.get());
        sucursalEntidadEntity.setNombreSucursal(sucursalEntidadAdmDto.nombreSucursal);
        sucursalEntidadEntity.setDireccion(sucursalEntidadAdmDto.direccion);
        sucursalEntidadEntity.setTelefono(sucursalEntidadAdmDto.telefono);

        sucursalEntidadEntity = iSucursalEntidadAdmDao.save(sucursalEntidadEntity);
        sucursalEntidadAdmDto.sucursalEntidadId = sucursalEntidadEntity.getSucursalEntidadId();

        return sucursalEntidadAdmDto;
    }

    @Override
    public void setTransaccionSucursalEntidad(Long sucursalEntidadId, String transaccion, Long usuarioId) {
        try {
            if(!iSucursalEntidadAdmDao.existsById(sucursalEntidadId)) {
                throw new Technicalexception("No existe registro SucursalEntidadId=" + sucursalEntidadId);
            }
            iSucursalEntidadAdmDao.updateTransaccionSucursalEntidad(sucursalEntidadId, transaccion, usuarioId);
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(), e.getCause());
        }
    }

    @Override
    public SucursalEntidadAdmDto getSucursalEntidadById(Long sucursalEntidadId) {
        return iSucursalEntidadAdmDao.findSucursalEntidadDtoById(sucursalEntidadId);
    }

    @Override
    public List<SucursalEntidadAdmDto> getAllSucursalEntidades() {
        return iSucursalEntidadAdmDao.findSucursalesEntidadesDtoAll();
    }

    @Override
    public List<SucursalEntidadAdmDto> getLisSucursalEntidadesByEntidadId(Long entidadId) {
        return iSucursalEntidadAdmDao.findSucursalesEntidadesDtoByEntidadId(entidadId);
    }
}