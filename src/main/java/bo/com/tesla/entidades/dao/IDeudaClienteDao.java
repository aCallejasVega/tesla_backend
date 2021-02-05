package bo.com.tesla.entidades.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import bo.com.tesla.administracion.entity.DeudaClienteEntity;
import bo.com.tesla.entidades.dto.ConceptoDto;
import bo.com.tesla.entidades.dto.DeudasClienteDto;

@Repository
public interface IDeudaClienteDao extends JpaRepository<DeudaClienteEntity, Long> {

	@Transactional
	@Modifying
	@Query(value =" delete from "
			+ " tesla.deudas_clientes d "
			+ " where d.archivo_id= :archivoId ",nativeQuery = true)
	public void deletByArchivoId(@Param("archivoId") Long archivoId);
	
	
	
	
	@Query("SELECT new bo.com.tesla.entidades.dto.DeudasClienteDto(d.archivoId.archivoId,d.servicio, d.tipoServicio, d.periodo, d.codigoCliente) "
			+ " FROM DeudaClienteEntity d "
			+ " WHERE d.archivoId.archivoId=:archivoId "
			+ "  and  (d.codigoCliente LIKE %:paramBusqueda% "
			+ "  or d.periodo LIKE %:paramBusqueda% "
			+ "  or d.nombreCliente LIKE %:paramBusqueda% "
			+ "  or d.nroDocumento LIKE %:paramBusqueda% )"
			+ " GROUP BY d.archivoId, d.servicio, d.tipoServicio, d.periodo, d.codigoCliente ")
	public List<DeudasClienteDto> groupByDeudasClientes(@Param("archivoId") Long archivoId ,@Param("paramBusqueda") String paramBusqueda );
	
	
	@Query("SELECT new  bo.com.tesla.entidades.dto.ConceptoDto(d.nroRegistro, d.nombreCliente, d.nroDocumento, d.direccion, "
			+ "	d.telefono, d.nit, d.tipo, d.concepto, d.montoUnitario, d.cantidad, "
			+ "	d.subTotal, d.datoExtras, d.tipoComprobante, d.periodoCabecera) "
			+ " FROM DeudaClienteEntity d "
			+ " WHERE d.archivoId.archivoId= :archivoId "
			+ " and d.servicio= :servicio "
			+ " and d.tipoServicio= :tipoServicio "
			+ " and d.periodo= :periodo"
			+ " and d.codigoCliente= :codigoCliente "
			+ " and d.tipo='D'")
	public List<ConceptoDto> findConceptos(
			@Param("archivoId") Long archivoId,
			@Param("servicio") String servicio, 
			@Param("tipoServicio") String tipoServicio, 
			@Param("periodo") String periodo, 
			@Param("codigoCliente") String codigoCliente);
	
	
	
}
