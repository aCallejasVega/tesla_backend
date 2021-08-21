package bo.com.tesla.recaudaciones.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bo.com.tesla.administracion.entity.EndPointEntidadEntity;

@Repository
public interface EndPointEntidadDao  extends JpaRepository<EndPointEntidadEntity, Long>{
	
	 @Query(value = "Select e "
	 		+ " From EndPointEntidadEntity e "
	 		+ " where e.entidadId.entidadId= :entidadId")
	    EndPointEntidadEntity findByEntidadId(@Param("entidadId") Long entidadId);


}
