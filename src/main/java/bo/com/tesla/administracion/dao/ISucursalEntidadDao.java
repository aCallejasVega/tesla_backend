package bo.com.tesla.administracion.dao;

import bo.com.tesla.administracion.dto.SucursalEntidadAdmDto;
import bo.com.tesla.administracion.entity.SucursalEntidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISucursalEntidadDao extends JpaRepository<SucursalEntidadEntity, Long> {

    /************************ABM****************************/

    @Modifying
    @Query(value = "UPDATE SucursalEntidadEntity se " +
            "SET se.transaccion = :transaccion, se.usuarioModificacion = :usuarioModificacion, se.fechaModificacion = CURRENT_TIMESTAMP " +
            "WHERE se.sucursalEntidadId = :sucursalEntidadId ")
    Integer updateTransaccionSucursalEntidad(@Param("sucursalEntidadId") Long sucursalEntidadId,
                                           @Param("transaccion") String transaccion,
                                           @Param("usuarioModificacion") Long usuarioModificacion);

    @Modifying
    @Query(value = "UPDATE SucursalEntidadEntity se " +
            "SET se.transaccion = :transaccion, se.usuarioModificacion = :usuarioModificacion, se.fechaModificacion = CURRENT_TIMESTAMP " +
            "WHERE se.sucursalEntidadId IN :sucursalEntidadLstId ")
    Integer updateLstTransaccionSucursalEntidad(@Param("sucursalEntidadLstId") List<Long> sucursalEntidadLstId,
                                             @Param("transaccion") String transaccion,
                                             @Param("usuarioModificacion") Long usuarioModificacion);


    @Query(value = "SELECT new bo.com.tesla.administracion.dto.SucursalEntidadAdmDto( " +
            "se.sucursalEntidadId, se.entidad.entidadId, se.nombreSucursal,se.direccion, se.telefono, u.login, se.fechaCreacion, se.estado, " +
            "se.email, se.codigoActividadEconomica, se.actividadEconomica, se.numeroSucursalSin, se.emiteFacturaTesla, " +
            "se.departamentoId.dominioId, se.departamentoId.descripcion, se.municipioId.dominioId, se.municipioId.descripcion) " +
            "FROM SucursalEntidadEntity se " +
            "INNER JOIN SegUsuarioEntity u ON se.usuarioCreacion = u.usuarioId " +
            "WHERE se.estado <> 'ELIMINADO'")
    List<SucursalEntidadAdmDto> findSucursalesEntidadesDtoAll();

    @Query(value = "SELECT new bo.com.tesla.administracion.dto.SucursalEntidadAdmDto( " +
            "se.sucursalEntidadId, se.entidad.entidadId, se.nombreSucursal,se.direccion, se.telefono, u.login, se.fechaCreacion, se.estado," +
            "se.email, se.codigoActividadEconomica, se.actividadEconomica, se.numeroSucursalSin, se.emiteFacturaTesla, " +
            "se.departamentoId.dominioId, se.departamentoId.descripcion, se.municipioId.dominioId, se.municipioId.descripcion) " +
            "FROM SucursalEntidadEntity se " +
            "INNER JOIN SegUsuarioEntity u ON se.usuarioCreacion = u.usuarioId " +
            "WHERE se.sucursalEntidadId = :sucursalEntidadId")
    Optional<SucursalEntidadAdmDto> findSucursalEntidadDtoById(@Param("sucursalEntidadId") Long sucursalEntidadId);

    @Query(value = "SELECT new bo.com.tesla.administracion.dto.SucursalEntidadAdmDto( " +
            "se.sucursalEntidadId, se.entidad.entidadId, se.nombreSucursal,se.direccion, se.telefono, u.login, se.fechaCreacion, se.estado, " +
            "se.email, se.codigoActividadEconomica, se.actividadEconomica, se.numeroSucursalSin, se.emiteFacturaTesla, " +
            "se.departamentoId.dominioId, se.departamentoId.descripcion, se.municipioId.dominioId, se.municipioId.descripcion) " +
            "FROM SucursalEntidadEntity se " +
            "INNER JOIN SegUsuarioEntity u ON se.usuarioCreacion = u.usuarioId " +
            "WHERE se.entidad.entidadId = :entidadId " +
            "AND se.estado <> 'ELIMINADO'")
    List<SucursalEntidadAdmDto> findSucursalesEntidadesDtoByEntidadId(@Param("entidadId") Long entidadId);
    
    
    
    @Query(value = "Select se "
    		+ " from SucursalEntidadEntity se "
    		+ " where se.entidad.entidadId= :entidadId"
    		+ " and se.estado= 'ACTIVO'")
    public List<SucursalEntidadEntity> findSucursalByEntidadId(@Param("entidadId") Long entidadId);

}
