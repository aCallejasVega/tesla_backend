package bo.com.tesla.administracion.dao;

import bo.com.tesla.administracion.entity.RecaudadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRecaudadorAdmDao extends JpaRepository<RecaudadorEntity, Long> {
}
