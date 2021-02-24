package bo.com.tesla.recaudaciones.dao;

import bo.com.tesla.administracion.entity.HistoricoDeudaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IHistoricoDeudaDao extends JpaRepository<HistoricoDeudaEntity, Long> {


	@Query("select h  from HistoricoDeudaEntity h  where h.deudaClienteId=:deudaClienteId ")
	Optional<HistoricoDeudaEntity> findByDeudaClienteId(@Param("deudaClienteId") Long deudaClienteId);

}
