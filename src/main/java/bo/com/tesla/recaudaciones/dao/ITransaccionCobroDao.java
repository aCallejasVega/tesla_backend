package bo.com.tesla.recaudaciones.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import bo.com.tesla.administracion.entity.RecaudadorEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bo.com.tesla.administracion.dto.DeudasClienteAdmDto;
import bo.com.tesla.administracion.entity.TransaccionCobroEntity;
import bo.com.tesla.entidades.dto.DeudasClienteDto;
import bo.com.tesla.recaudaciones.dto.DeudasClienteRecaudacionDto;
import bo.com.tesla.recaudaciones.dto.TransaccionesCobroApiDto;


@Repository
public interface ITransaccionCobroDao extends JpaRepository<TransaccionCobroEntity, Long> {
	
	@Query("Select new bo.com.tesla.recaudaciones.dto.DeudasClienteRecaudacionDto(hd.archivoId.archivoId, hd.codigoCliente,hd.servicio, "
			+ " hd.tipoServicio,hd.periodo, hd.estado,hd.nombreCliente, "
			+ "	tc.fechaCreacion,tc.totalDeuda,r.nombre,tc.comision,p.nombres ||' '||p.paterno||' '||p.materno,e.nombreComercial) "			
			+ " from HistoricoDeudaEntity hd "
			+ " left join TransaccionCobroEntity tc on ( tc.archivoId.archivoId=hd.archivoId.archivoId "
			+ "											 and tc.codigoCliente=hd.codigoCliente "
			+ "											 and tc.servicio=hd.servicio "
			+ "											 and tc.tipoServicio=hd.tipoServicio "
			+ "											 and tc.periodo=hd.periodo ) "
			+ " left join RecaudadorEntity r on r.recaudadorId=tc.recaudador.recaudadorId "
			+ " left join ArchivoEntity a on a.archivoId=hd.archivoId.archivoId "
			+ " left join EntidadEntity e on e.entidadId=a.entidadId.entidadId "
			+ " left join SegUsuarioEntity u on u.usuarioId=tc.usuarioCreacion "
			+ " left join PersonaEntity p on p.personaId=u.personaId.personaId "
			+ " Where "					
			+ " hd.estado in :estado "			
			+ " and (CAST(e.entidadId as string ) in :entidadId and r.recaudadorId = :recaudadorId )  "
			+ " and (  date(tc.fechaCreacion) BETWEEN   date(:fechaInicio) and date(:fechaFin) "
			+ "         or tc.fechaCreacion is null) "
			+ " GROUP BY hd.archivoId,hd.codigoCliente,hd.tipoServicio,hd.servicio,hd.periodo,hd.nombreCliente,hd.estado, "
			+ " tc.totalDeuda,tc.fechaCreacion,tc.comision,r.nombre,p.nombres ||' '||p.paterno||' '||p.materno,e.nombreComercial "
			+ " ORDER BY hd.estado,e.nombreComercial,tc.fechaCreacion ASC"
			)
	public Page<DeudasClienteRecaudacionDto>  findDeudasByParameterForRecaudacion(
			@Param("fechaInicio") Date fechaInicio, 
			@Param("fechaFin") Date fechaFin,
			@Param("entidadId") List<String> entidadId,
			@Param("recaudadorId") Long recaudadorId,
			@Param("estado") List<String> estado,
			Pageable pageable
			);
	
	@Query("Select new bo.com.tesla.recaudaciones.dto.DeudasClienteRecaudacionDto(hd.archivoId.archivoId, hd.codigoCliente,hd.servicio, "
			+ " hd.tipoServicio,hd.periodo, hd.estado,hd.nombreCliente, "
			+ "	tc.fechaCreacion,tc.totalDeuda,r.nombre,tc.comision,p.nombres ||' '||p.paterno||' '||p.materno,e.nombreComercial) "			
			+ " from HistoricoDeudaEntity hd "
			+ " left join TransaccionCobroEntity tc on ( tc.archivoId.archivoId=hd.archivoId.archivoId "
			+ "											 and tc.codigoCliente=hd.codigoCliente "
			+ "											 and tc.servicio=hd.servicio "
			+ "											 and tc.tipoServicio=hd.tipoServicio "
			+ "											 and tc.periodo=hd.periodo ) "
			+ " left join RecaudadorEntity r on r.recaudadorId=tc.recaudador.recaudadorId "
			+ " left join ArchivoEntity a on a.archivoId=hd.archivoId.archivoId "
			+ " left join EntidadEntity e on e.entidadId=a.entidadId.entidadId "
			+ " left join SegUsuarioEntity u on u.usuarioId=tc.usuarioCreacion "
			+ " left join PersonaEntity p on p.personaId=u.personaId.personaId "
			+ " Where "					
			+ " hd.estado in :estado "
			+ " and (CAST(e.entidadId as string ) in :entidadId and r.recaudadorId = :recaudadorId)  "
			+ " and (  date(tc.fechaCreacion) BETWEEN   date(:fechaInicio) and date(:fechaFin) "
			+ "         or tc.fechaCreacion is null) "
			+ " GROUP BY hd.archivoId,hd.codigoCliente,hd.tipoServicio,hd.servicio,hd.periodo,hd.nombreCliente,hd.estado, "
			+ " tc.totalDeuda,tc.fechaCreacion,tc.comision,r.nombre,p.nombres ||' '||p.paterno||' '||p.materno,e.nombreComercial "
			+ " ORDER BY hd.estado,e.nombreComercial,tc.fechaCreacion ASC"
			)
	public List<DeudasClienteRecaudacionDto>  findDeudasByParameterForReportRecaudacion(
			@Param("fechaInicio") Date fechaInicio, 
			@Param("fechaFin") Date fechaFin,
			@Param("entidadId") List<String> entidadId,
			@Param("recaudadorId") Long recaudadorId,
			@Param("estado") List<String> estado		
			);
	
	
	@Query("Select new bo.com.tesla.entidades.dto.DeudasClienteDto(hd.archivoId.archivoId, hd.codigoCliente,hd.servicio, "
			+ " hd.tipoServicio,hd.periodo, hd.estado,hd.nombreCliente, "
			+ "	tc.fechaCreacion,tc.totalDeuda,r.nombre,tc.comision) "			
			+ " from HistoricoDeudaEntity hd "
			+ " left join TransaccionCobroEntity tc on ( tc.archivoId.archivoId=hd.archivoId.archivoId "
			+ "											 and tc.codigoCliente=hd.codigoCliente "
			+ "											 and tc.servicio=hd.servicio "
			+ "											 and tc.tipoServicio=hd.tipoServicio "
			+ "											 and tc.periodo=hd.periodo ) "
			+ " left join RecaudadorEntity r on r.recaudadorId=tc.recaudador.recaudadorId "
			+ " inner join ArchivoEntity a on a.archivoId=hd.archivoId.archivoId "
			+ " inner join EntidadEntity e on e.entidadId=a.entidadId.entidadId "
			+ " Where "			
			+ " e.entidadId= :entidadId "		
			+ " and (COALESCE(CAST(r.recaudadorId as string ),'') in :recaudadorId or r.recaudadorId = null ) "
			+ " and hd.estado in :estado  "
			+ " and (  date(tc.fechaCreacion) BETWEEN   date(:fechaInicio) and date(:fechaFin) "
			+ "         or tc.fechaCreacion is null) "
			+ " GROUP BY hd.archivoId,hd.codigoCliente,hd.tipoServicio,hd.servicio,hd.periodo,hd.nombreCliente,hd.estado, "
			+ " tc.totalDeuda,tc.fechaCreacion,tc.comision,r.nombre "
			+ " ORDER BY hd.estado,r.nombre,tc.fechaCreacion ASC "
			)
	public Page<DeudasClienteDto>  findDeudasByParameterForEntidad(
			@Param("fechaInicio") Date fechaInicio, 
			@Param("fechaFin") Date fechaFin,
			@Param("entidadId") Long entidadId,
			@Param("recaudadorId") List<String> recaudadorId,
			@Param("estado") List<String> estado,
			Pageable pageable
			);
	
	@Query("Select new bo.com.tesla.entidades.dto.DeudasClienteDto(hd.archivoId.archivoId, hd.codigoCliente,hd.servicio, "
			+ " hd.tipoServicio,hd.periodo, hd.estado,hd.nombreCliente, "
			+ "	tc.fechaCreacion,tc.totalDeuda,r.nombre,tc.comision) "			
			+ " from HistoricoDeudaEntity hd "
			+ " left join TransaccionCobroEntity tc on ( tc.archivoId.archivoId=hd.archivoId.archivoId "
			+ "											 and tc.codigoCliente=hd.codigoCliente "
			+ "											 and tc.servicio=hd.servicio "
			+ "											 and tc.tipoServicio=hd.tipoServicio "
			+ "											 and tc.periodo=hd.periodo ) "
			+ " left join RecaudadorEntity r on r.recaudadorId=tc.recaudador.recaudadorId "
			+ " inner join ArchivoEntity a on a.archivoId=hd.archivoId.archivoId "
			+ " inner join EntidadEntity e on e.entidadId=a.entidadId.entidadId "
			+ " Where "			
			+ " e.entidadId= :entidadId "
			+ " and (COALESCE(CAST(r.recaudadorId as string ),'') in :recaudadorId or r.recaudadorId = null )"
			+ " and hd.estado in :estado"
			+ " and (  date(tc.fechaCreacion) BETWEEN   date(:fechaInicio) and date(:fechaFin) "
			+ "         or tc.fechaCreacion is null) "
			+ " GROUP BY hd.archivoId,hd.codigoCliente,hd.tipoServicio,hd.servicio,hd.periodo,hd.nombreCliente,hd.estado, "
			+ " tc.totalDeuda,tc.fechaCreacion,tc.comision,r.nombre "
			+ " ORDER BY hd.estado,r.nombre,tc.fechaCreacion ASC "
			)
	public List<DeudasClienteDto>  findDeudasByParameterForReportEntidades(
			@Param("fechaInicio") Date fechaInicio, 
			@Param("fechaFin") Date fechaFin,
			@Param("entidadId") Long entidadId,
			@Param("recaudadorId") List<String> recaudadorId,
			@Param("estado") List<String> estado			
			);
	
	@Query(" Select new  bo.com.tesla.entidades.dto.DeudasClienteDto( "
			+ "	c.servicio,c.tipoServicio,c.periodo,c.fechaCreacion,CONCAT(p.nombres,' ',p.paterno,' ',p.materno) as cajero , "
			+ " c.nombreClientePago,c.totalDeuda )"
			+ " from TransaccionCobroEntity c "
			+ " left join SegUsuarioEntity u on u.usuarioId=c.usuarioCreacion "
			+ " left join PersonaEntity p on p.personaId=u.personaId.personaId "
			+ " left join EmpleadoEntity e on e.personaId.personaId=p.personaId "
			+ " left join SucursalEntity s on s.sucursalId=e.sucursalId.sucursalId "
			+ " left join RecaudadorEntity r on r.recaudadorId=s.recaudador.recaudadorId "
			+ " where c.entidadId.entidadId= :entidadId "
			+ " and CAST(r.recaudadorId as string ) like concat( :recaudadorId,'%') "
			+ " and c.estado like concat(:estado,'%') "
			+ " and CAST(c.fechaCreacion AS date) >= CAST(:fechaIni AS date) "
			+ " and CAST(c.fechaCreacion AS date) <= CAST(:fechaFin AS date)  "
			+ " ORDER BY c.fechaCreacion asc ")
	public List<DeudasClienteDto>  findDeudasPagadasByParameter(
			@Param("entidadId") Long entidadId, 
			@Param("recaudadorId") String recaudadorId,
			@Param("estado") String estado,
			@Param("fechaIni") Date fechaIni,
			@Param("fechaFin") Date fechaFin
			);
	
	
	
	@Query("Select new bo.com.tesla.administracion.dto.DeudasClienteAdmDto(hd.archivoId.archivoId, hd.codigoCliente,hd.servicio, "
			+ " hd.tipoServicio,hd.periodo, hd.estado,hd.nombreCliente, "
			+ "	tc.fechaCreacion,tc.totalDeuda,r.nombre,tc.comision,p.nombres ||' '||p.paterno||' '||p.materno,e.nombreComercial) "			
			+ " from HistoricoDeudaEntity hd "
			+ " left join TransaccionCobroEntity tc on ( tc.archivoId.archivoId=hd.archivoId.archivoId "
			+ "											 and tc.codigoCliente=hd.codigoCliente "
			+ "											 and tc.servicio=hd.servicio "
			+ "											 and tc.tipoServicio=hd.tipoServicio "
			+ "											 and tc.periodo=hd.periodo ) "
			+ " left join RecaudadorEntity r on r.recaudadorId=tc.recaudador.recaudadorId "
			+ " left join ArchivoEntity a on a.archivoId=hd.archivoId.archivoId "
			+ " left join EntidadEntity e on e.entidadId=a.entidadId.entidadId "
			+ " left join SegUsuarioEntity u on u.usuarioId=tc.usuarioCreacion "
			+ " left join PersonaEntity p on p.personaId=u.personaId.personaId "
			+ " Where "			
			+ " COALESCE(CAST(e.entidadId as string ),'') like :entidadId "
			+ " and COALESCE(CAST(r.recaudadorId as string ),'') like :recaudadorId "			
			+ " and hd.estado in :estado"
			+ " and (  date(tc.fechaCreacion) BETWEEN   date(:fechaInicio) and date(:fechaFin) "
			+ "         or tc.fechaCreacion is null) "
			+ " GROUP BY hd.archivoId,hd.codigoCliente,hd.tipoServicio,hd.servicio,hd.periodo,hd.nombreCliente,hd.estado, "
			+ " tc.totalDeuda,tc.fechaCreacion,tc.comision,r.nombre,p.nombres ||' '||p.paterno||' '||p.materno,e.nombreComercial "
			+ " ORDER BY tc.fechaCreacion ASC"
			)
	public Page<DeudasClienteAdmDto>  findDeudasByParameterForAdmin(
			@Param("fechaInicio") Date fechaInicio, 
			@Param("fechaFin") Date fechaFin,
			@Param("entidadId") String entidadId,
			@Param("recaudadorId") String recaudadorId,
			@Param("estado") List<String> estado,
			Pageable pageable
			);
	
	@Query("Select new bo.com.tesla.administracion.dto.DeudasClienteAdmDto(hd.archivoId.archivoId, hd.codigoCliente,hd.servicio, "
			+ " hd.tipoServicio,hd.periodo, hd.estado,hd.nombreCliente, "
			+ "	tc.fechaCreacion,tc.totalDeuda,r.nombre,tc.comision, "
			+ " p.nombres ||' '||p.paterno||' '||p.materno,e.nombreComercial) "			
			+ " from HistoricoDeudaEntity hd "
			+ " left join TransaccionCobroEntity tc on ( tc.archivoId.archivoId=hd.archivoId.archivoId "
			+ "											 and tc.codigoCliente=hd.codigoCliente "
			+ "											 and tc.servicio=hd.servicio "
			+ "											 and tc.tipoServicio=hd.tipoServicio "
			+ "											 and tc.periodo=hd.periodo ) "
			+ " left join RecaudadorEntity r on r.recaudadorId=tc.recaudador.recaudadorId "
			+ " left join ArchivoEntity a on a.archivoId=hd.archivoId.archivoId "
			+ " left join EntidadEntity e on e.entidadId=a.entidadId.entidadId "
			+ " left join SegUsuarioEntity u on u.usuarioId=tc.usuarioCreacion "
			+ " left join PersonaEntity p on p.personaId=u.personaId.personaId "
			+ " Where "			
			+ " COALESCE(CAST(e.entidadId as string ),'') like :entidadId "
			+ " and COALESCE(CAST(r.recaudadorId as string ),'') like :recaudadorId "		
			+ " and hd.estado in :estado "
			+ " and (  date(tc.fechaCreacion) BETWEEN   date(:fechaInicio) and date(:fechaFin) "
			+ "         or tc.fechaCreacion is null) "
			+ " GROUP BY hd.archivoId,hd.codigoCliente,hd.tipoServicio,hd.servicio,hd.periodo,hd.nombreCliente,hd.estado, "
			+ " tc.totalDeuda,tc.fechaCreacion,tc.comision,r.nombre,p.nombres ||' '||p.paterno||' '||p.materno,e.nombreComercial "
			+ " ORDER BY hd.estado,e.nombreComercial,r.nombre, tc.fechaCreacion ASC"
			)
	public List<DeudasClienteAdmDto>  findDeudasByParameterForReportAdmin(
			@Param("fechaInicio") Date fechaInicio, 
			@Param("fechaFin") Date fechaFin,
			@Param("entidadId") String entidadId,
			@Param("recaudadorId") String recaudadorId,
			@Param("estado") List<String> estado			
			);

	
	
	@Query("Select tc "
			+ " from TransaccionCobroEntity tc "
			+ " where date(tc.fechaCreacion) = date(:fechaSeleccionada) "
			+ " and tc.usuarioCreacion= :usuarioCreacion "
			+ " and tc.estado='COBRADO' "
			+ " and tc.entidadId.entidadId= :entidadId "			
			)
	public List<TransaccionCobroEntity>  findDeudasCobradasByUsuarioCreacionForGrid(			
			@Param("usuarioCreacion") Long usuarioCreacion,
			@Param("fechaSeleccionada") Date fechaSeleccionada,
			@Param("entidadId") Long entidadId
			);
	


	@Modifying
	@Query(value = "UPDATE TransaccionCobroEntity t " +
			"SET t.transaccion = :transaccion, t.usuarioModificacion = :usuarioModificacionId, t.fechaModificacion = CURRENT_TIMESTAMP " +
			"WHERE t.facturaId in :facturaIdLst " +
			"AND t.modalidadFacturacion.dominioId = :modalidadFacturacionId " +
			"AND (SELECT a.estado " +
				"FROM ArchivoEntity a " +
				"WHERE a.archivoId = t.archivoId.archivoId) = 'ACTIVO'")//Permite controlar que no se actualice con un cargado nuevo
	Integer updateLstTransaccionByFacturas(@Param("facturaIdLst") List<Long> facturaIdLst,
												@Param("modalidadFacturacionId") Long modalidadFacturacionId,
												@Param("transaccion") String transaccion,
												@Param("usuarioModificacionId") Long usuarioModificacionId);

	@Query(value = "SELECT count(t) " +
			"FROM TransaccionCobroEntity t " +
			"WHERE t.facturaId in :facturaIdLst " +
			"AND t.modalidadFacturacion.dominioId = :modalidadFacturacionId " +
			"AND (SELECT a.estado " +
				"FROM ArchivoEntity a " +
				"WHERE a.archivoId = t.archivoId.archivoId) != 'ACTIVO'")
	Integer countArchivosNoActivosPorListaFacturas(@Param("facturaIdLst") List<Long> facturaIdLst,
										   @Param("modalidadFacturacionId") Long modalidadFacturacionId);

	@Modifying
	@Query("UPDATE TransaccionCobroEntity t " +
			"SET t.facturaId = :facturaId, t.transaccion = 'COBRAR' " +
			"WHERE t.transaccionCobroId in :transaccionCobroIdLst ")
	Integer updateFacturaTransaccion(@Param("transaccionCobroIdLst") List<Long> transaccionCobroIdLst,
						  @Param("facturaId") Long facturaId);

	List<TransaccionCobroEntity> findByFacturaIdAndEstado(Long facturaId, String estado);

	@Query("SELECT distinct t.facturaId " +
			"FROM TransaccionCobroEntity t " +
			"WHERE t.modalidadFacturacion.dominioId = :modalidadFacturacionId " +
			"AND t.entidadId.entidadId = :entidadId " +
			"AND (:recaudadorId is null OR (t.recaudador.recaudadorId = :recaudadorId)) " +
			"ORDER BY t.facturaId")
	List<Long> findFacturasByModalidadAndEntidadAndRecaudador(@Param("modalidadFacturacionId") Long modalidadFacturacionId,
															  @Param("entidadId") Long entidadId,
															  @Param("recaudadorId") Long recaudadorId);

	
	@Query(	"Select new bo.com.tesla.recaudaciones.dto.TransaccionesCobroApiDto( "
			+ " tc.tipoServicio, "
			+ " tc.servicio, "
			+ " tc.periodo, "
			+ " tc.codigoCliente,"
			+ "	tc.totalDeuda, "
			+ " tc.nombreClienteArchivo, "
			+ " tc.nroDocumentoClienteArchivo,"
			+ "	r.nombre, "
			+ " s.nombre, "
			+ " tc.fechaCreacion ) "			
					+ " from  TransaccionCobroEntity tc "					
					+ " left join RecaudadorEntity r on r.recaudadorId=tc.recaudador.recaudadorId "	
					+ " left join SegUsuarioEntity u on u.usuarioId=tc.usuarioCreacion "
					+ " left join PersonaEntity p on p.personaId=u.personaId.personaId "
					+ " left join EmpleadoEntity e on e.personaId.personaId=p.personaId "
					+ " left join SucursalEntity s on s.sucursalId=e.sucursalId.sucursalId "					
					+ " Where "
					+ " tc.archivoId = :archivoId "
					+ " and tc.codigoCliente= :codigoCliente "
					+ " and tc.servicio= :servicio "
					+ " and tc.tipoServicio = :tipoServicio "
					+ " and tc.periodo= :periodo "
					+ " and tc.estado ='COBRADO' "
			
			)
	public TransaccionesCobroApiDto  getTransaccionCobroForEntidad(			
			@Param("archivoId") Long archivoId,
			@Param("codigoCliente") String codigoCliente,
			@Param("servicio") String servicio,
			@Param("tipoServicio") String tipoServicio,
			@Param("periodo") String periodo
			
			);

}
