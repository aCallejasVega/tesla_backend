package bo.com.tesla.recaudaciones.dao;

import bo.com.tesla.administracion.entity.EntidadEntity;
import bo.com.tesla.recaudaciones.dto.DominioDto;
import bo.com.tesla.recaudaciones.dto.EntidadDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEntidadRDao extends JpaRepository<EntidadEntity, Long> {

    Optional<EntidadEntity> findByEntidadIdAndEstado(Long entidad_id, String estado);

    @Query(value = "select new bo.com.tesla.recaudaciones.dto.EntidadDto(" +
            " er.entidad.entidadId, er.entidad.nombre, er.entidad.nombreComercial, er.entidad.pathLogo) "+
            " from EntidadRecaudadorEntity er " +
            " where er.recaudador.recaudadorId = :pRecaudadorId " +
            " and er.entidad.tipoEntidad.dominioId = :pTipoEntidadId " +
            " and er.entidad.estado = 'ACTIVO' " +
            " and er.entidad.estado = 'ACTIVO' " +
            " and er.recaudador.estado = 'ACTIVO'")
    List<EntidadDto> findByRecaudadoraIdAndTipoEntidadId(@Param("pRecaudadorId") Long pRecaudadorId, @Param("pTipoEntidadId") Long pTipoEntidadId);

    @Query(value = "select new bo.com.tesla.recaudaciones.dto.EntidadDto(" +
            " er.entidad.entidadId, er.entidad.nombre, er.entidad.nombreComercial, er.entidad.pathLogo) "+
            " from EntidadRecaudadorEntity er " +
            " where er.recaudador.recaudadorId = :pRecaudadorId " +
            " and er.entidad.estado = 'ACTIVO' " +
            " and er.entidad.estado = 'ACTIVO' " +
            " and er.recaudador.estado = 'ACTIVO'")
    List<EntidadDto> findByRecaudadoraId(@Param("pRecaudadorId") Long pRecaudadorId);

    Optional<EntidadEntity> findByEntidadId(Long entidadId);
}

