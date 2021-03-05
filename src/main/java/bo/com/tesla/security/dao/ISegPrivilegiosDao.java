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
			+ " left join SegPrivilegioRolEntity pr on pr.rolId.rolId=ur.rolId.rolId "
			+ " Where pr.privilegioId.estado='ACTIVO' "
			+ " and pr.estado='ACTIVO'"
			+ " and ur.usuarioId.usuarioId=:usuarioId"
			+ " and pr.privilegioId.privilegioPadreId is null"
			+ " ")
	public List<SegPrivilegioEntity> getMenuByUserId(@Param("usuarioId") Long usuarioId);
	
	@Query(value=" select pr.estado from "
			+ " tesla.seg_privilegios_roles pr "
			+ " left join tesla.seg_roles r on r.rol_id=pr.rol_id "
			+ " left join tesla.seg_usuarios_roles ur on ur.rol_id=r.rol_id "
			+ " left join tesla.seg_usuarios u on u.usuario_id=ur.usuario_id "
			+ " left join tesla.seg_privilegios p on p.privilegios_id=pr.privilegio_id "
			+ " where u.usuario_id= :usuarioId "
			+ " and privilegio_id= :privilegioId ", nativeQuery = true)
	public  String getEstadoPrivilegios(@Param("usuarioId") Long usuarioId,@Param("privilegioId") Long privilegioId);
	
	
	@Query(value=" SELECT t.transaccion_id,t.etiqueta,t.imagen,t.orden "
			+ " FROM tesla.seg_transiciones t "
			+ " left join tesla.seg_privilegios_roles_transiciones prt on (prt.tabla_id=t.tabla_id and prt.estado_inicial_id=t.estado_inicial and  prt.transaccion_id=t.transaccion_id) "
			+ " left join tesla.seg_privilegios_roles pr on pr.privilegio_rol_id=prt.privilegio_rol_id "
			+ " left join tesla.seg_roles r on r.rol_id=pr.rol_id "
			+ " left join tesla.seg_usuarios_roles ur on ur.rol_id=r.rol_id "
			+ " left join tesla.seg_usuarios u on u.usuario_id=ur.usuario_id "
			+ " where t.estado='ACTIVO' "
			+ " AND r.estado='ACTIVO' "
			+ " AND u.estado='ACTIVO' "
			+ " AND prt.estado='ACTIVO' "
			+ " AND  u.login= :login "
			+ " AND t.tabla_id= :tabla "
			+ " order by t.orden ASC ", nativeQuery = true)
	public  List<Object[]> getOperaciones(@Param("login") String login,@Param("tabla") String tabla);
	
}
