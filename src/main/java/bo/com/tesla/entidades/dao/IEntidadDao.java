package bo.com.tesla.entidades.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bo.com.tesla.administracion.entity.EntidadEntity;

@Repository
public interface IEntidadDao extends JpaRepository<EntidadEntity, Long>  {

	
	@Query("select e.entidadId "
			+ " from SegUsuarioEntity u "
			+ " left join PersonaEntity  p on p.personaId=u.personaId.personaId "
			+ " left join EmpleadoEntity e on e.personaId.personaId=p.personaId  "
			+ " Where u.estado='ACTIVO' AND e.entidadId.estado='ACTIVO' AND u.usuarioId=:usuarioId")
	public EntidadEntity findEntidadByUserId(@Param("usuarioId") Long usuarioId);
}
