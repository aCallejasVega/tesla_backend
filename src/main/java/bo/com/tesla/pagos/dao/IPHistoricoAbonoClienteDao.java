package bo.com.tesla.pagos.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bo.com.tesla.administracion.entity.PHistoricoAbonoClienteEntity;

@Repository
public interface IPHistoricoAbonoClienteDao extends JpaRepository<PHistoricoAbonoClienteEntity, Long>{
	
	
	
	
	@Modifying
	@Query(value =" Update tesla.p_historicos_abonos_clientes  "
			+ " set estado='PAGADO',"
			+ "     usuario_modificacion = :usuarioModificacion, "
			+ "     fecha_modificacion = :fechaModificacion"
			+ " Where "
			+ "     archivo_id= :archivoId "
			+ " 	and codigo_cliente= :codigoCliente "
			+ "     and nro_documento_cliente= :nroDocumentoCliente "
			+ "		and periodo = :periodo ",nativeQuery = true)
	public void updateByArchivoId(
			@Param("archivoId") Long archivoId, 
			@Param("codigoCliente") String codigoCliente,
			@Param("nroDocumentoCliente") String nroDocumentoCliente,
			@Param("usuarioModificacion") Long usuarioModificacion,
			@Param("fechaModificacion") Date fechaModificacion,
			@Param("periodo") String periodo
			);

}
