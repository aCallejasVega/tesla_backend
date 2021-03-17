package bo.com.tesla.recaudaciones.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bo.com.tesla.administracion.entity.TransaccionCobroEntity;
import bo.com.tesla.recaudaciones.dto.DeudasClienteRecaudacionDto;


@Repository
public interface ITransaccionCobroDao extends JpaRepository<TransaccionCobroEntity, Long> {
	
	@Query("Select new bo.com.tesla.recaudaciones.dto.DeudasClienteRecaudacionDto(hd.archivoId.archivoId, hd.codigoCliente,hd.servicio, "
			+ " hd.tipoServicio,hd.periodo, hd.estado,hd.nombreCliente, "
			+ "	tc.fechaCreacion,tc.totalDeuda,r.nombre,tc.comision,p.nombres ||' '||p.paterno||' '||p.materno) "			
			+ " from HistoricoDeudaEntity hd "
			+ " left join TransaccionCobroEntity tc on ( tc.archivoId.archivoId=hd.archivoId.archivoId "
			+ "											 and tc.codigoCliente=hd.codigoCliente "
			+ "											 and tc.servicio=hd.servicio "
			+ "											 and tc.tipoServicio=hd.tipoServicio "
			+ "											 and tc.periodo=hd.periodo ) "
			+ " left join RecaudadorEntity r on r.recaudadorId=tc.recaudadorId.recaudadorId "
			+ " inner join ArchivoEntity a on a.archivoId=hd.archivoId.archivoId "
			+ " inner join EntidadEntity e on e.entidadId=a.entidadId.entidadId "
			+ " inner join SegUsuarioEntity u on u.usuarioId=tc.usuarioCreacion "
			+ " inner join PersonaEntity p on p.personaId=u.personaId.personaId "
			+ " Where "			
			+ "  COALESCE(CAST(e.entidadId as string ),'') like :entidadId "
			+ " and r.recaudadorId = :recaudadorId "
			+ " and hd.estado like :estado"
			+ " and (  date(tc.fechaCreacion) BETWEEN   date(:fechaInicio) and date(:fechaFin) "
			+ "         or tc.fechaCreacion is null) "
			+ " GROUP BY hd.archivoId,hd.codigoCliente,hd.servicio,hd.tipoServicio,hd.periodo,hd.nombreCliente,hd.estado, "
			+ " tc.totalDeuda,tc.fechaCreacion,tc.comision,r.nombre,p.nombres ||' '||p.paterno||' '||p.materno "
			+ " ORDER BY tc.fechaCreacion ASC"
			)
	public Page<DeudasClienteRecaudacionDto>  findDeudasByParameter(
			@Param("fechaInicio") Date fechaInicio, 
			@Param("fechaFin") Date fechaFin,
			@Param("entidadId") String entidadId,
			@Param("recaudadorId") Long recaudadorId,
			@Param("estado") String estado,
			Pageable pageable
			);
	
	@Query("Select new bo.com.tesla.recaudaciones.dto.DeudasClienteRecaudacionDto(hd.archivoId.archivoId, hd.codigoCliente,hd.servicio, "
			+ " hd.tipoServicio,hd.periodo, hd.estado,hd.nombreCliente, "
			+ "	tc.fechaCreacion,tc.totalDeuda,r.nombre,tc.comision,p.nombres ||' '||p.paterno||' '||p.materno) "			
			+ " from HistoricoDeudaEntity hd "
			+ " left join TransaccionCobroEntity tc on ( tc.archivoId.archivoId=hd.archivoId.archivoId "
			+ "											 and tc.codigoCliente=hd.codigoCliente "
			+ "											 and tc.servicio=hd.servicio "
			+ "											 and tc.tipoServicio=hd.tipoServicio "
			+ "											 and tc.periodo=hd.periodo ) "
			+ " left join RecaudadorEntity r on r.recaudadorId=tc.recaudadorId.recaudadorId "
			+ " inner join ArchivoEntity a on a.archivoId=hd.archivoId.archivoId "
			+ " inner join EntidadEntity e on e.entidadId=a.entidadId.entidadId "
			+ " inner join SegUsuarioEntity u on u.usuarioId=tc.usuarioCreacion "
			+ " inner join PersonaEntity p on p.personaId=u.personaId.personaId "
			+ " Where "			
			+ " COALESCE(CAST(e.entidadId as string ),'') like :entidadId"
			+ " and r.recaudadorId = :recaudadorId "
			+ " and hd.estado like :estado"
			+ " and (  date(tc.fechaCreacion) BETWEEN   date(:fechaInicio) and date(:fechaFin) "
			+ "         or tc.fechaCreacion is null) "
			+ " GROUP BY hd.archivoId,hd.codigoCliente,hd.servicio,hd.tipoServicio,hd.periodo,hd.nombreCliente,hd.estado, "
			+ " tc.totalDeuda,tc.fechaCreacion,tc.comision,r.nombre,p.nombres ||' '||p.paterno||' '||p.materno "
			+ " ORDER BY hd.estado,tc.fechaCreacion ASC"
			)
	public List<DeudasClienteRecaudacionDto>  findDeudasByParameterForReport(
			@Param("fechaInicio") Date fechaInicio, 
			@Param("fechaFin") Date fechaFin,
			@Param("entidadId") String entidadId,
			@Param("recaudadorId") Long recaudadorId,
			@Param("estado") String estado			
			);
	
}
