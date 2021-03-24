package bo.com.tesla.recaudaciones.dao;

import bo.com.tesla.administracion.entity.DominioEntity;
import bo.com.tesla.recaudaciones.dto.DominioDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDominioDao extends JpaRepository<DominioEntity, Long> {


    
    
    @Query("Select d from DominioEntity d Where d.estado= 'ACTIVO'  and d.dominio= :dominio")
    List<DominioEntity> findByDominio(@Param("dominio") String dominio);


    Optional<DominioEntity> getDominioEntityByDominioIdAndDominioAndEstado(Long dominioId, String dominio, String estado);

    @Query(value = "select distinct new bo.com.tesla.recaudaciones.dto.DominioDto( " +
            " er.entidad.tipoEntidad.dominioId, er.entidad.tipoEntidad.dominio, er.entidad.tipoEntidad.descripcion, er.entidad.tipoEntidad.abreviatura ) " +
            " from EntidadRecaudadorEntity er " +
            " where er.recaudador.recaudadorId = :pRecaudadorId " +
            " and er.entidad.estado = 'ACTIVO' " +
            " and er.recaudador.estado = 'ACTIVO' " +
            " and er.estado = 'ACTIVO'")
    List<DominioDto> findTipoEntidadByRecaudadorId(@Param("pRecaudadorId") Long pRecaudadorId);

    @Query(value = "SELECT new bo.com.tesla.recaudaciones.dto.DominioDto( " +
            "d.dominioId, d.dominio, d.descripcion, d.abreviatura) " +
            "FROM DominioEntity d " +
            "WHERE d.estado = 'ACTIVO' " +
            "AND d.dominio = :dominio")
    List<DominioDto> findDominioDtoByDominio(String dominio);


    @Query(value = "SELECT new bo.com.tesla.recaudaciones.dto.DominioDto( " +
            "a.dominio.dominioId, a.dominio.dominio, a.dominio.descripcion, a.dominio.abreviatura) " +
            "FROM AgrupadorDominioEntity a " +
            "WHERE a.agrupadorId = :agrupadorId " +
            "AND a.estado = 'ACTIVO' " +
            "AND a.dominio.estado = 'ACTIVO'")
    List<DominioDto> findLstByAgrupador(@Param("agrupadorId") Long agrupadorId);

}
