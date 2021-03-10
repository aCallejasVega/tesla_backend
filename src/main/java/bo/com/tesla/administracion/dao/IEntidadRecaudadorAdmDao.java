package bo.com.tesla.administracion.dao;

import bo.com.tesla.administracion.entity.EntidadRecaudadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEntidadRecaudadorAdmDao extends JpaRepository<EntidadRecaudadorEntity, Long> {
}
