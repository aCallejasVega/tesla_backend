package bo.com.tesla.recaudaciones.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bo.com.tesla.administracion.entity.RecaudadorEntity;

@Repository
public interface IRecaudadorDao extends JpaRepository <RecaudadorEntity, Long> {

    @Query(value = "select e.sucursalId.recaudadorId "
            + " from SegUsuarioEntity u "
            + " inner join PersonaEntity  p on p.personaId = u.personaId.personaId "
            + " inner join EmpleadoEntity e on e.personaId.personaId = p.personaId "
            + " where u.estado = 'ACTIVO' "
            + " and e.sucursalId.estado = 'ACTIVO' "
            + " and e.sucursalId.recaudadorId.estado = 'ACTIVO'"
            + " and u.usuarioId=:usuarioId")
    RecaudadorEntity findRecaudadorByUserId(@Param("usuarioId") Long usuarioId);


}
