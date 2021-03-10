package bo.com.tesla.administracion.dao;

import bo.com.tesla.administracion.dto.EntidadAdmDto;
import bo.com.tesla.administracion.entity.EntidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.List;

@Repository
public interface IEntidadAdmDao extends JpaRepository<EntidadEntity, Long> {

    EntidadEntity findByEntidadId(Long entidadId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE EntidadEntity e " +
            "SET e.transaccion = :transaccion, e.usuarioModificacion = :usuarioModificacion, e.fechaModificacion = current_timestamp " +//:fechaModificacion  " +
            "WHERE e.entidadId = :entidadId")
    void updateTransaccionEntidad(@Param("entidadId") Long entidadId,
                                  @Param("transaccion") String transaccion,
                                  @Param("usuarioModificacion") Long usuarioModificacion);

    //Long countByEntidadIdIn(List<Long> entidadIdLst);

    @Transactional
    @Modifying
    @Query(value = "UPDATE EntidadEntity e " +
            "SET e.transaccion = :transaccion, e.usuarioModificacion = :usuarioModificacion, e.fechaModificacion = current_timestamp " +//:fechaModificacion  " +
            "WHERE e.entidadId IN :entidadIdLst")
    void updateLstTransaccionEntidad(@Param("entidadIdLst") List<Long> entidadIdLst,
                                  @Param("transaccion") String transaccion,
                                  @Param("usuarioModificacion") Long usuarioModificacion);

    @Query(value = "SELECT new bo.com.tesla.administracion.dto.EntidadAdmDto(" +
            "e.entidadId, e.nombre, e.nombreComercial, e.direccion, e.telefono, e.nit, " +
            "e.pathLogo, e.comprobanteEnUno, e.actividadEconomica.dominioId, e.actividadEconomica.descripcion, " +
            "e.municipio.dominioId, e.municipio.descripcion, e.tipoEntidad.dominioId, e.tipoEntidad.descripcion, " +
            "e.fechaCreacion, s.login, e.estado) " +
            "FROM EntidadEntity e " +
            "INNER JOIN SegUsuarioEntity s ON s.usuarioId = e.usuarioCreacion " +
            "WHERE e.estado <> 'ELIMINADO'" +
            "ORDER BY e.nombre  ")
    List<EntidadAdmDto> findEntidadesDtoAll();

    @Query(value = "SELECT new bo.com.tesla.administracion.dto.EntidadAdmDto(" +
            "e.entidadId, e.nombre, e.nombreComercial, e.direccion, e.telefono, e.nit, " +
            "e.pathLogo, e.comprobanteEnUno, e.actividadEconomica.dominioId, e.actividadEconomica.descripcion, " +
            "e.municipio.dominioId, e.municipio.descripcion, e.tipoEntidad.dominioId, e.tipoEntidad.descripcion, " +
            "e.fechaCreacion, s.login, e.estado) " +
            "FROM EntidadEntity e " +
            "INNER JOIN SegUsuarioEntity s ON s.usuarioId = e.usuarioCreacion " +
            "WHERE e.entidadId = :entidadId")
    EntidadAdmDto findEntidadDtoById(@Param("entidadId") Long entidadId);

}
