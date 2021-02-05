package bo.com.tesla.entidades.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bo.com.tesla.administracion.entity.ArchivoEntity;

@Repository
public interface IArchivoDao extends JpaRepository<ArchivoEntity, Long> {

	@Query("Select a from ArchivoEntity a Where a.estado= :estado  and a.entidadId.entidadId= :entidadId ")
	public ArchivoEntity findByEstado(@Param("estado") String estado,@Param("entidadId") Long entidadId);

}
