package bo.com.tesla.security.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bo.com.tesla.administracion.entity.SegRolEntity;

public interface ISegRolDao extends JpaRepository<SegRolEntity, Long> {

	@Query("Select ur.rolId " 
			+ " from SegUsuarioRolEntity ur "
			+ " where ur.usuarioId.login=:login AND ur.usuarioId.estado='ACTIVO' " 
			+ " and ur.rolId.estado='ACTIVO' ")
	public List<SegRolEntity> findRolesByUsuarioLogin(@Param("login") String login);

}
