package bo.com.tesla.recaudaciones.dao;

import bo.com.tesla.administracion.entity.HistoricoDeudaEntity;
import bo.com.tesla.entidades.dto.ConceptoDto;
import bo.com.tesla.entidades.dto.DeudasClienteDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IHistoricoDeudaDao extends JpaRepository<HistoricoDeudaEntity, Long> {

	@Query("select h  from HistoricoDeudaEntity h  where h.deudaClienteId=:deudaClienteId ")
	Optional<HistoricoDeudaEntity> findByDeudaClienteId(@Param("deudaClienteId") Long deudaClienteId);

	
	/**
	 * Obtiene todas la deudas hitoricas asociadas 
	 * al archivo.
	 * 
	 * @author Adalid Callejas	 
	 * @version 1.0 
	 * @param entidadId 
	 * @return lista de historicos
	 */
	@Query("SELECT new bo.com.tesla.entidades.dto.DeudasClienteDto(d.archivoId.archivoId,d.servicio, d.tipoServicio, d.periodo, d.codigoCliente,sum(d.subTotal)) "
			+ " FROM HistoricoDeudaEntity d "
			+ " WHERE d.archivoId.archivoId=:archivoId "
			+ "  and  (d.codigoCliente LIKE %:paramBusqueda% "
			+ "  or upper(d.periodo) LIKE  upper(concat('%', :paramBusqueda,'%')) "
			+ "  or upper(d.nombreCliente) LIKE upper(concat('%', :paramBusqueda,'%')) "
			+ "  or upper(d.nroDocumento) LIKE upper(concat('%', :paramBusqueda,'%')) "
			+ "  or upper(d.estado) LIKE upper(concat('%', :paramBusqueda,'%'))) "			
			+ " GROUP BY d.archivoId, d.servicio, d.tipoServicio, d.periodo, d.codigoCliente "
			+ " ORDER BY d.codigoCliente, d.servicio, d.tipoServicio, d.periodo  ASC")
	public Page<DeudasClienteDto> groupByDeudasClientes(@Param("archivoId") Long archivoId ,@Param("paramBusqueda") String paramBusqueda,Pageable pageable );
	
	
	
	@Query("SELECT new  bo.com.tesla.entidades.dto.ConceptoDto(d.nroRegistro, d.nombreCliente, d.nroDocumento, d.direccion, "
			+ "	d.telefono, d.nit, d.tipo, d.concepto, d.montoUnitario, d.cantidad, "
			+ "	d.subTotal, d.datoExtra, d.tipoComprobante, d.periodoCabecera,d.esPostpago) "
			+ " FROM HistoricoDeudaEntity d "
			+ " WHERE d.archivoId.archivoId= :archivoId "
			+ " and d.servicio= :servicio "
			+ " and d.tipoServicio= :tipoServicio "
			+ " and d.periodo= :periodo "
			+ " and d.codigoCliente= :codigoCliente "
			+ " and d.tipo='D' "
			+ " ORDER BY d.nroRegistro ")
	public List<ConceptoDto> findConceptos(
			@Param("archivoId") Long archivoId,
			@Param("servicio") String servicio, 
			@Param("tipoServicio") String tipoServicio, 
			@Param("periodo") String periodo, 
			@Param("codigoCliente") String codigoCliente);
}
