package bo.com.tesla.recaudaciones.dao;

import bo.com.tesla.administracion.entity.DominioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDominioDao extends JpaRepository<DominioEntity, Long> {

    Optional<DominioEntity> findByDominioId(Long dominioId);
    Optional<DominioEntity> findByDominioIdAndDominio(Long dominioId, String dominio);
    
    @Query("Select d from DominioEntity d Where d.estado= 'ACTIVO'  and d.dominio= :dominio")
    List<DominioEntity> findByDominio(@Param("dominio") String dominio);
}
