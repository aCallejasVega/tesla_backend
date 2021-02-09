package bo.com.tesla.recaudaciones.dao;

import bo.com.tesla.administracion.entity.HistoricoDeudaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IHistoricoDeudaDao extends JpaRepository<HistoricoDeudaEntity, Long> {

    Optional<HistoricoDeudaEntity> findByDeudaClienteId(Long deudaClienteId);

}
