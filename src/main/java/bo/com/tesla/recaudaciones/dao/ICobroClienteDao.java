package bo.com.tesla.recaudaciones.dao;

import bo.com.tesla.administracion.entity.CobroClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICobroClienteDao extends JpaRepository<CobroClienteEntity, Long> {
}
