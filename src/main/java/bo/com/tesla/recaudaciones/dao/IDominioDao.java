package bo.com.tesla.recaudaciones.dao;

import bo.com.tesla.administracion.entity.DominioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDominioDao extends JpaRepository<DominioEntity, Long> {

    Optional<DominioEntity> findByDominioId(Long dominioId);
}
