package bo.com.tesla.recaudaciones.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bo.com.tesla.administracion.entity.RecaudadorEntity;

@Repository
public interface IRecaudadorDao extends JpaRepository <RecaudadorEntity, Long> {

    @Query(value = "select e.sucursalId.recaudadorId "
            + " from SegUsuarioEntity u "
            + " left join PersonaEntity  p on p.personaId = u.personaId.personaId "
            + " left join EmpleadoEntity e on e.personaId.personaId = p.personaId "
            + " where u.estado = 'ACTIVO' "
            + " and e.sucursalId.estado = 'ACTIVO' "
            + " and e.sucursalId.recaudadorId.estado = 'ACTIVO'"
            + " and u.usuarioId=:usuarioId")
    RecaudadorEntity findRecaudadorByUserId(@Param("usuarioId") Long usuarioId);
    
    
    @Query("Select r from RecaudadorEntity r where r.recaudadorId= :recaudadorId and r.estado='ACTIVO'")
	public RecaudadorEntity findByRecaudadorId(@Param("recaudadorId") Long recaudadorId);

	@Query(" Select r " 
			+ " from RecaudadorEntity r "
			+ " left join EntidadRecaudadorEntity er on er.recaudador.recaudadorId=r.recaudadorId "
			+ " left join EntidadEntity e on e.entidadId=er.entidad.entidadId " + " Where " + " r.estado='ACTIVO' "
			+ " and e.entidadId= :entidadId")
	public List<RecaudadorEntity> findRecaudadoresByEntidadId(@Param("entidadId") Long entidadId);



}
