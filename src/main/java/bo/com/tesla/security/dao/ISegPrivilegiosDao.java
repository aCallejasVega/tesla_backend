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
			+ " and pr.estado='ACTIVO'"
			+ " and ur.usuarioId.usuarioId=:usuarioId"
			+ " and pr.privilegioId.privilegioPadreId is null"
			+ " ")
	public List<SegPrivilegioEntity> getMenuByUserId(@Param("usuarioId") Long usuarioId);
	
	@Query(value=" select pr.estado from "
			+ " tesla2.seg_privilegios_roles pr "
			+ " left join tesla2.seg_roles r on r.rol_id=pr.rol_id "
			+ " left join tesla2.seg_usuarios_roles ur on ur.rol_id=r.rol_id "
			+ " left join tesla2.seg_usuarios u on u.usuario_id=ur.usuario_id "
			+ " left join tesla2.seg_privilegios p on p.privilegios_id=pr.privilegio_id "
			+ " where u.usuario_id= :usuarioId "
			+ " and privilegio_id= :privilegioId ", nativeQuery = true)
	public  String getEstadoPrivilegios(@Param("usuarioId") Long usuarioId,@Param("privilegioId") Long privilegioId);
}
