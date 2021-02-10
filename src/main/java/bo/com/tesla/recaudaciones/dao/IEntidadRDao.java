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

    @Query(value = "select new bo.com.tesla.recaudaciones.dto.EntidadDto(" +
            " er.entidad.entidadId, er.entidad.nombre, er.entidad.nombreComercial, er.entidad.pathLogo) "+
            " from EntidadRecaudadorEntity er " +
            " where er.recaudador.recaudadorId = :pRecaudadorId " +
            " and er.entidad.tipoEntidad.dominioId = :pTipoEntidadId " +
            " and er.entidad.estado = 'ACTIVO'")
    Optional<List<EntidadDto>> findByRecaudadoraIdAndTipoEntidadId(@Param("pRecaudadorId") Long pRecaudadorId, @Param("pTipoEntidadId") Long pTipoEntidadId);


    @Query(value = "select new bo.com.tesla.recaudaciones.dto.EntidadDto(" +
            " er.entidad.entidadId, er.entidad.nombre, er.entidad.nombreComercial, er.entidad.pathLogo) "+
            " from EntidadRecaudadorEntity er " +
            " where er.recaudador.recaudadorId = :pRecaudadorId " +
            " and er.entidad.estado = 'ACTIVO'")
    Optional<List<EntidadDto>> findByRecaudadoraId(@Param("pRecaudadorId") Long pRecaudadorId);

    @Query(value = "select distinct new bo.com.tesla.recaudaciones.dto.DominioDto( " +
            " er.entidad.tipoEntidad.dominioId, er.entidad.tipoEntidad.dominio, er.entidad.tipoEntidad.descripcion, er.entidad.tipoEntidad.abreviatura ) " +
            " from EntidadRecaudadorEntity er " +
            " where er.recaudador.recaudadorId = :pRecaudadorId " +
            " and er.entidad.estado = 'ACTIVO'")
    Optional<List<DominioDto>> findTipoEntidadByRecaudadorId(@Param("pRecaudadorId") Long pRecaudadorId);

    Optional<EntidadEntity> findByEntidadId(Long entidadId);
}

