package bo.com.tesla.entidades.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bo.com.tesla.administracion.entity.TransaccionCobroEntity;

@Repository
public interface ITransaccionCobrosDao  extends JpaRepository<TransaccionCobroEntity, Long>{
	
	@Query(value=" select TO_CHAR(date(c.fecha_creacion),'dd/mm/yyyy'),count(*) "
			+ " from  tesla.transacciones_cobros c "
			+ " left join tesla.seg_usuarios u on u.usuario_id=c.usuario_creacion "
			+ " left join tesla.personas p on p.persona_id=u.persona_id "
			+ " left join tesla.empleados e on e.persona_id=p.persona_id "
			+ " left join tesla.sucursales s on s.sucursal_id=e.sucursal_id "
			+ " left join tesla.recaudadores r on r.recaudador_id=s.recaudador_id "
			+ " where  "
			+ " c.entidad_id= :entidadId "
			+ " and cast(s.recaudador_id as VARCHAR) like :recaudadorId "
			+ " and c.estado LIKE concat(:estado,'%') "
			+ " and date(c.fecha_creacion)>=date(:fechaInicio) "
			+ " and date(c.fecha_creacion)<=date(:fechaFin) "
			+ " GROUP BY TO_CHAR(date(c.fecha_creacion),'dd/mm/yyyy') "
			+ " ORDER BY TO_CHAR(date(c.fecha_creacion),'dd/mm/yyyy') ASC", nativeQuery = true)
	public  List<Object[]> getDeudasforDate(
			@Param("entidadId") Long entidadId,
			@Param("recaudadorId") String recaudadorId,
			@Param("estado") String estado,
			@Param("fechaInicio") Date fechaInicio,
			@Param("fechaFin") Date fechaFin
			);
	
	@Query(value=" select c.servicio, count(*) "
			+ " from  tesla.transacciones_cobros c "
			+ " left join tesla.seg_usuarios u on u.usuario_id=c.usuario_creacion "
			+ " left join tesla.personas p on p.persona_id=u.persona_id "
			+ " left join tesla.empleados e on e.persona_id=p.persona_id "
			+ " left join tesla.sucursales s on s.sucursal_id=e.sucursal_id "
			+ " left join tesla.recaudadores r on r.recaudador_id=s.recaudador_id "
			+ " where  "
			+ " c.entidad_id= :entidadId "
			+ " and cast(s.recaudador_id as VARCHAR) like :recaudadorId "
			+ " and c.estado LIKE concat(:estado,'%') "
			+ " and date(c.fecha_creacion)>=date(:fechaInicio) "
			+ " and date(c.fecha_creacion)<=date(:fechaFin) "
			+ " GROUP BY c.servicio "
			+ " ", nativeQuery = true)
	public  List<Object[]> getDeudasforServicio(
			@Param("entidadId") Long entidadId,
			@Param("recaudadorId") String recaudadorId,
			@Param("estado") String estado,
			@Param("fechaInicio") Date fechaInicio,
			@Param("fechaFin") Date fechaFin
			);
	
	@Query(value= " select c.tipo_servicio,count(c.tipo_servicio) as cantidad "
			+ " from  tesla.transacciones_cobros c "
			+ " left join tesla.seg_usuarios u on u.usuario_id=c.usuario_creacion "
			+ " left join tesla.personas p on p.persona_id=u.persona_id "
			+ " left join tesla.empleados e on e.persona_id=p.persona_id "
			+ " left join tesla.sucursales s on s.sucursal_id=e.sucursal_id "
			+ " left join tesla.recaudadores r on r.recaudador_id=s.recaudador_id "
			+ " where  "
			+ " c.entidad_id=1 "
			+ " and cast(s.recaudador_id as VARCHAR) like '%' "
			+ " and c.estado LIKE concat('COBRADO','%')  "
			+ " and date(c.fecha_creacion)>=date('2021-01-01 18:11:05.028') "
			+ " and date(c.fecha_creacion)<=date('2021-08-25 18:11:05.028') "
			+ " GROUP BY c.tipo_servicio "
			, nativeQuery = true)
	public  List<Object[]> getDeudasforTipoServicio(
			@Param("entidadId") Long entidadId,
			@Param("recaudadorId") String recaudadorId,
			@Param("estado") String estado,
			@Param("fechaInicio") Date fechaInicio,
			@Param("fechaFin") Date fechaFin
			);
}
