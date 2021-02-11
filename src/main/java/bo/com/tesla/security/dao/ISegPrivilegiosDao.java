package bo.com.tesla.security.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bo.com.tesla.administracion.entity.SegPrivilegioEntity;


@Repository
public interface ISegPrivilegiosDao extends JpaRepository<SegPrivilegioEntity, Long>{
	@Query("Select pr.privilegioId"
			+ " From SegUsuarioRolEntity ur "
			+ " left join SegPrivilegioRolEntity pr on pr.rolId.rolId=ur.rolId "
			+ " Where pr.privilegioId.estado='ACTIVO' "
			+ " and ur.usuarioId.usuarioId=:usuarioId"
			+ " and pr.privilegioId.privilegioPadreId is null"
			+ " ")
	public List<SegPrivilegioEntity> getMenuByUserId(@Param("usuarioId") Long usuarioId);
}
