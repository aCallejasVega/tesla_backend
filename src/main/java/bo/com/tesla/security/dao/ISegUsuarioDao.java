package bo.com.tesla.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bo.com.tesla.administracion.entity.SegUsuarioEntity;

public interface ISegUsuarioDao extends JpaRepository<SegUsuarioEntity, Long> {
	@Query("Select u  from SegUsuarioEntity u  where u.estado ='ACTIVO' and u.login=:login ")
	public SegUsuarioEntity findByLogin(@Param("login") String login);
}
