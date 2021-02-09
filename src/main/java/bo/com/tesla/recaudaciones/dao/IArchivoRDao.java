package bo.com.tesla.recaudaciones.dao;

import bo.com.tesla.administracion.entity.ArchivoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IArchivoRDao extends JpaRepository<ArchivoEntity, Long> {

    //Optional<ArchivoEntity> findByEntidadIdEntidadId(Long entidadId);
    Optional<ArchivoEntity> findByArchivoId(Long archivoId);
}
