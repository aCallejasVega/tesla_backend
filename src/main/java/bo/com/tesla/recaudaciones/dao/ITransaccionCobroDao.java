package bo.com.tesla.recaudaciones.dao;

import bo.com.tesla.administracion.entity.TransaccionCobroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransaccionCobroDao extends JpaRepository<TransaccionCobroEntity, Long> {
}
