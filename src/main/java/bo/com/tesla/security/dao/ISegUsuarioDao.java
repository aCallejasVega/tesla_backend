package bo.com.tesla.security.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bo.com.tesla.administracion.entity.SegUsuarioEntity;

@Repository
public interface ISegUsuarioDao extends JpaRepository<SegUsuarioEntity, Long> {
	
	
	@Query("Select u  "
			+ " from SegUsuarioEntity u "			
			+ " where u.estado ='ACTIVO' "
			+ " and u.personaId.estado='ACTIVO' "
			+ " and u.login=:login ")
	public SegUsuarioEntity findByLogin(@Param("login") String login);
	
	@Query(" Select u  "
			+ " from SegUsuarioEntity u "
			+ " where "
			+ " u.personaId.personaId = :personaId"
			+ " and u.estado ='ACTIVO' ")
	public Optional<SegUsuarioEntity> findByPersonaIdAndEstado(@Param("personaId") Long personaId);
	
	
}
